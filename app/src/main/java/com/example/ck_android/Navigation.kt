package com.example.ck_android

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ck_android.ui.screens.Writing.WritingScreen
import com.example.ck_android.ui.screens.Writing.WritingViewModel
import com.example.ck_android.ui.screens.check_code.CheckCodeScreen
import com.example.ck_android.ui.screens.check_code.CheckCodeViewModel
import com.example.ck_android.ui.screens.content.ContentScreen
import com.example.ck_android.ui.screens.content.ContentViewModel
import com.example.ck_android.ui.screens.grammar.GrammarItemScreen
import com.example.ck_android.ui.screens.grammar.GrammarItemViewModel
import com.example.ck_android.ui.screens.grammar.GrammarScreen
import com.example.ck_android.ui.screens.grammar.GrammarViewModel
import com.example.ck_android.ui.screens.home.HomeScreen
import com.example.ck_android.ui.screens.home.HomeViewModel
import com.example.ck_android.ui.screens.login.LoginScreen
import com.example.ck_android.ui.screens.login.LoginViewModel
import com.example.ck_android.ui.screens.register.RegisterScreen
import com.example.ck_android.ui.screens.register.RegisterViewModel
import com.example.ck_android.ui.screens.speakAI.SpeakAIScreen
import com.example.ck_android.ui.screens.speakAI.SpeakAIViewModel
import com.example.ck_android.ui.screens.start.StartScreen
import com.example.ck_android.ui.screens.start.StartViewModel
import com.example.ck_android.ui.screens.test.TestFlashScreen
import com.example.ck_android.ui.screens.test.TestFlashViewModel
import com.example.ck_android.ui.screens.test.TestScreen
import com.example.ck_android.ui.screens.test.TestViewModel
import com.example.ck_android.ui.screens.toeic.ToeicExamScreen
import com.example.ck_android.ui.screens.toeic.ToeicExamViewModel
import com.example.ck_android.ui.screens.toeic.ToeicResultScreen
import com.example.ck_android.ui.screens.toeic.ToeicResultViewModel
import com.example.ck_android.ui.screens.toeic.ToeicScreen
import com.example.ck_android.ui.screens.toeic.ToeicViewModel
import com.example.ck_android.ui.screens.vocabulary.VocabularyByTitleScreen
import com.example.ck_android.ui.screens.vocabulary.VocabularyByTitleViewModel
import com.example.ck_android.ui.screens.vocabulary.VocabularyCategory
import com.example.ck_android.ui.screens.vocabulary.VocabularyCategoryViewModel
import com.example.ck_android.ui.screens.vocabulary.VocabularyScreen
import com.example.ck_android.ui.screens.vocabulary.VocabularyViewModel

sealed class Screen(val route: String) {
    object Login : Screen("login")
    object Home : Screen("home")
    object Start : Screen("start")
    object Register : Screen("register")
    object CheckCode : Screen("check_code")
    object Content : Screen("content")
    object Grammar : Screen("grammar")
    object GrammarItem : Screen("grammar_item/{slug}")
    object SpeakAi : Screen("speak_ai")
    object Vocabulary : Screen("vocabulary")
    object VocabularyByTitle : Screen("vocabulary_by_title/{slug}")
    object VocabularyCategory : Screen("vocabulary_category/{slug}/{category}")
    object Toeic : Screen("toeic")
    object ToeicExam : Screen("toeic/{id}")
    object ToeicResult : Screen("toeic_result/{id}")
    object Writing : Screen("writing")
    object Test : Screen("Test/{slug}/{category}")
    object TestFlashCard : Screen("TestFlashCard/{slug}/{category}")
}

@Composable
fun Navigation() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = hiltViewModel()
    val mainState = mainViewModel.uiState.collectAsState()
    val context = LocalContext.current
    LaunchedEffect(mainState.value.error) {
        if (mainState.value.error != "") {
            Toast.makeText(context, mainState.value.error, Toast.LENGTH_LONG).show()
            mainViewModel.setError("")
        }
    }
    NavHost(navController = navController, startDestination = Screen.Start.route) {
        composable(Screen.Start.route) {
            StartScreen(
                navController,
                startViewModel = hiltViewModel<StartViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Home.route) {
            HomeScreen(
                navController,
                homeViewModel = hiltViewModel<HomeViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Login.route) {
            LoginScreen(
                navController,
                loginViewModel = hiltViewModel<LoginViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Register.route) {
            RegisterScreen(
                navController,
                registerViewModel = hiltViewModel<RegisterViewModel>(),
                mainViewModel
            )
        }
        composable(
            Screen.CheckCode.route + "?ID={ID}", arguments = listOf(
                navArgument("ID") {
                    type = NavType.StringType
                    defaultValue = ""
                }
            )) {
            CheckCodeScreen(
                navController,
                checkCodeViewModel = hiltViewModel<CheckCodeViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Content.route) {
            ContentScreen(
                navController,
                contentViewModel = hiltViewModel<ContentViewModel>(),
                mainViewModel
            )
        }
        composable(Screen.Vocabulary.route) {
            VocabularyScreen(
                navController,
                vocabularyViewModel = hiltViewModel<VocabularyViewModel>(),
                mainViewModel
            )
        }
        composable(
            Screen.VocabularyByTitle.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) { backStackEntry ->
            VocabularyByTitleScreen(
                navController = navController,
                vocabularyByTitleViewModel = hiltViewModel<VocabularyByTitleViewModel>(),
                mainViewModel = mainViewModel,
                slug = backStackEntry.arguments?.getString("slug") ?: ""
            )
        }

        composable(
            Screen.VocabularyCategory.route,
            arguments = listOf(
                navArgument("slug") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType })
        ) { backStackEntry ->
            VocabularyCategory(
                navController = navController,
                vocabularyCategoryViewModel = hiltViewModel<VocabularyCategoryViewModel>(),
                mainViewModel = mainViewModel,
                slug = backStackEntry.arguments?.getString("slug") ?: "",
                category = backStackEntry.arguments?.getString("category") ?: ""
            )
        }

        composable(Screen.Grammar.route) {
            GrammarScreen(
                navController,
                grammarViewModel = hiltViewModel<GrammarViewModel>(),
                mainViewModel
            )
        }
        composable(
            Screen.GrammarItem.route,
            arguments = listOf(navArgument("slug") { type = NavType.StringType })
        ) { backStackEntry ->
            GrammarItemScreen(
                navController = navController,
                grammarItemViewModel = hiltViewModel<GrammarItemViewModel>(),
                mainViewModel = mainViewModel,
                slug = backStackEntry.arguments?.getString("slug") ?: ""
            )
        }
        composable(Screen.SpeakAi.route) {
            SpeakAIScreen(
                navController = navController,
                speakAIViewModel = hiltViewModel<SpeakAIViewModel>(),
                mainViewModel = mainViewModel
            )
        }
        composable(Screen.Toeic.route) {
            ToeicScreen(
                navController = navController,
                toeicViewModel = hiltViewModel<ToeicViewModel>(),
                mainViewModel = mainViewModel
            )
        }
        composable(
            Screen.ToeicExam.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            ToeicExamScreen(
                navController = navController,
                toeicExamViewModel = hiltViewModel<ToeicExamViewModel>(),
                mainViewModel = mainViewModel,
                id = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
        composable(
            Screen.ToeicResult.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })
        ) { backStackEntry ->
            ToeicResultScreen(
                navController = navController,
                toeicResultViewModel = hiltViewModel<ToeicResultViewModel>(),
                mainViewModel = mainViewModel,
                id = backStackEntry.arguments?.getString("id") ?: ""
            )
        }
        composable(Screen.Writing.route) {
            WritingScreen(
                navController = navController,
                writingViewModel = hiltViewModel<WritingViewModel>(),
                mainViewModel = mainViewModel
            )
        }
        composable(
            Screen.Test.route,
            arguments = listOf(
                navArgument("slug") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            TestScreen(
                navController = navController,
                testViewModel = hiltViewModel<TestViewModel>(),
                mainViewModel = mainViewModel,
                slug = it.arguments?.getString("slug") ?: "",
                category = it.arguments?.getString("category") ?: ""
            )
        }
        composable(
            Screen.TestFlashCard.route,
            arguments = listOf(
                navArgument("slug") { type = NavType.StringType },
                navArgument("category") { type = NavType.StringType }
            )
        ) {
            TestFlashScreen(
                navController = navController,
                testFlashViewModel = hiltViewModel<TestFlashViewModel>(),
                mainViewModel = mainViewModel,
                slug = it.arguments?.getString("slug") ?: "",
                category = it.arguments?.getString("category") ?: "",
            )
        }
    }
}

