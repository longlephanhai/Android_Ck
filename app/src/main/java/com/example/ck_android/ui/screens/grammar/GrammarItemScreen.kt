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
        // Title
        Text(
            text = state.value.data.title,
            style = MaterialTheme.typography.headlineLarge.copy(
                fontSize = 28.sp,
                color = MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // Display content with better spacing and style
        val htmlContent = Html.fromHtml(state.value.data.content, Html.FROM_HTML_MODE_LEGACY)
        BasicText(
            text = htmlContent.toString(),
            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp),
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Spacer to give some space before the button
        Spacer(modifier = Modifier.weight(1f))

        // Back Button with more styling
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
