package com.example.ck_android.ui.screens.speakAI

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import java.io.File

@Composable
fun SpeakAIScreen(
    navController: NavController,
    speakAIViewModel: SpeakAIViewModel,
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val userText by speakAIViewModel.text.collectAsState()
    val aiText by speakAIViewModel.aiResponse.collectAsState()
    val audio by speakAIViewModel.audio.collectAsState()

    // Speech Recognition
    val speechRecognizer = remember { SpeechRecognizer.createSpeechRecognizer(context) }
    val speechIntent = remember {
        Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US")
            putExtra(RecognizerIntent.EXTRA_PROMPT, "Nói gì đó...")
            putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1)
            putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
        }
    }

    // Permission handling
    val audioPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            startListening(speechRecognizer, speechIntent, speakAIViewModel)
        } else {
            Log.e("Permission", "Microphone permission denied")
            // Show error to user
        }
    }

    // Audio playback effect
    LaunchedEffect(audio) {
        audio?.let { playAudioFromBytes(context, it) }
    }

    // Clean up on dispose
    DisposableEffect(Unit) {
        onDispose {
            speechRecognizer.destroy()
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "AI: $aiText", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Bạn: $userText", style = MaterialTheme.typography.bodyLarge)

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = speakAIViewModel::startConversation,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🟢 Bắt đầu trò chuyện")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.RECORD_AUDIO
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    startListening(speechRecognizer, speechIntent, speakAIViewModel)
                } else {
                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("🎙️ Nói")
        }

        Spacer(modifier = Modifier.height(10.dp))

        Button(
            onClick = speakAIViewModel::sendUserReply,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("📤 Gửi cho AI")
        }
    }
}

private fun startListening(
    speechRecognizer: SpeechRecognizer,
    intent: Intent,
    viewModel: SpeakAIViewModel
) {


    speechRecognizer.setRecognitionListener(object : RecognitionListener {
        override fun onReadyForSpeech(params: Bundle?) {
            Log.d("Speech", "Ready for speech")
        }

        override fun onBeginningOfSpeech() {
            Log.d("Speech", "Beginning of speech")
        }

        override fun onRmsChanged(rmsdB: Float) {
            // Handle volume changes if needed
        }

        override fun onBufferReceived(buffer: ByteArray?) {
            // Handle buffer if needed
        }

        override fun onEndOfSpeech() {
            Log.d("Speech", "End of speech")
        }

        override fun onError(error: Int) {
            val errorMessage = when (error) {
                SpeechRecognizer.ERROR_AUDIO -> "Audio recording error"
                SpeechRecognizer.ERROR_CLIENT -> "Client side error"
                SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> "Insufficient permissions"
                SpeechRecognizer.ERROR_NETWORK -> "Network error"
                SpeechRecognizer.ERROR_NETWORK_TIMEOUT -> "Network timeout"
                SpeechRecognizer.ERROR_NO_MATCH -> "No recognition match"
                SpeechRecognizer.ERROR_RECOGNIZER_BUSY -> "RecognitionService busy"
                SpeechRecognizer.ERROR_SERVER -> "Server error"
                SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> "No speech input"
                else -> "Unknown error"
            }
            Log.e("SpeechError", "Error $error: $errorMessage")
        }

        override fun onResults(results: Bundle?) {
            results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)?.let {
                if (it.isNotEmpty()) {
                    viewModel.updateText(it[0])
                }
            }
        }

        override fun onPartialResults(partialResults: Bundle?) {
            // Handle partial results if needed
        }

        override fun onEvent(eventType: Int, params: Bundle?) {
            // Handle events if needed
        }
    })

    try {
        speechRecognizer.startListening(intent)
    } catch (e: SecurityException) {
        Log.e("Speech", "Security exception: ${e.message}")
    } catch (e: Exception) {
        Log.e("Speech", "Error starting recognition: ${e.message}")
    }
}

private fun playAudioFromBytes(context: Context, audioBytes: ByteArray) {
    val tempFile = File.createTempFile("audio", "mp3", context.cacheDir).apply {
        deleteOnExit()
    }

    try {
        tempFile.writeBytes(audioBytes)
        MediaPlayer().apply {
            setDataSource(tempFile.absolutePath)
            prepare()
            setOnCompletionListener { release() }
            setOnErrorListener { _, what, extra ->
                Log.e("MediaPlayer", "Error playing audio: $what, $extra")
                release()
                false
            }
            start()
        }
    } catch (e: Exception) {
        Log.e("MediaPlayer", "Error playing audio: ${e.message}")
        tempFile.delete()
    }
}