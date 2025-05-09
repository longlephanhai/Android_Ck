package com.example.ck_android.ui.screens.grammar

import android.text.Html
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ck_android.MainViewModel
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.ui.viewinterop.AndroidView
@Composable
fun GrammarItemScreen(
    navController: NavController,
    grammarItemViewModel: GrammarItemViewModel,
    mainViewModel: MainViewModel,
    slug: String
) {
    val state = grammarItemViewModel.uiState.collectAsState()
    val blueColor = Color(0xFF1877F2)
    LaunchedEffect(Unit) {
        grammarItemViewModel.getGrammarBySlug(grammarItemViewModel.getToken() ?: "", slug)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = state.value.data.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        AndroidView(
            factory = { context ->
                TextView(context).apply {
                    textSize = 16f
                    setTextColor(android.graphics.Color.parseColor("#333333"))
                    setLineSpacing(1.4f, 1.4f)
                    movementMethod = LinkMovementMethod.getInstance()
                }
            },
            update = { textView ->
                val htmlContent = state.value.data.content
                textView.text = Html.fromHtml(htmlContent, Html.FROM_HTML_MODE_LEGACY)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        )

        Spacer(modifier = Modifier.weight(0.5f))

        Button(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = blueColor,
                contentColor = Color.White
            )
        ) {
            Text(
                text = "Go Back",
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)
            )
        }
    }
}
