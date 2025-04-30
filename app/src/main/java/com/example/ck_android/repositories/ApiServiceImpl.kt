package com.example.ck_android.repositories

import com.example.ck_android.model.ChatRequest
import com.example.ck_android.model.ChatResponse
import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.model.CheckCodeResponse
import com.example.ck_android.model.GeminiRequest
import com.example.ck_android.model.GeminiResponse
import com.example.ck_android.model.GrammarItemResponse
import com.example.ck_android.model.GrammarResponse
import com.example.ck_android.model.LoginRequest
import com.example.ck_android.model.LoginRespon
import com.example.ck_android.model.QuestionResponse
import com.example.ck_android.model.RegisterResponse
import com.example.ck_android.model.ScoreRequest
import com.example.ck_android.model.ScoreResponse
import com.example.ck_android.model.TitleGeminiResponse
import com.example.ck_android.model.ToeicExamResponse
import com.example.ck_android.model.ToeicResponse
import com.example.ck_android.model.VocabularyByTitleResponse
import com.example.ck_android.model.VocabularyCategoryResponse
import com.example.ck_android.model.VocabularyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class ApiServiceImpl @Inject constructor(
    private val apiClient: ApiClient
) : ApiService {
    override suspend fun login(loginRequest: LoginRequest): LoginRespon {
        return apiClient.apiService.login(loginRequest)
    }

    override suspend fun register(
        avatar: MultipartBody.Part?,
        name: RequestBody,
        email: RequestBody,
        password: RequestBody,
        phone: RequestBody
    ): RegisterResponse {
        return apiClient.apiService.register(avatar, name, email, password, phone)
    }

    override suspend fun checkCode(checkCodeRequest: CheckCodeRequest): CheckCodeResponse {
        return apiClient.apiService.checkCode(checkCodeRequest)
    }

    override suspend fun getGrammars(accessToken: String): GrammarResponse {
        return apiClient.apiService.getGrammars(accessToken)
    }

    override suspend fun getGrammarItem(
        access_token: String,
        slug: String
    ): GrammarItemResponse {
        return apiClient.apiService.getGrammarItem(access_token, slug)
    }

    override suspend fun chatGpt(
        access_token: String,
        chatRequest: ChatRequest
    ): ChatResponse {
        return apiClient.apiService.chatGpt(access_token, chatRequest)
    }

    override suspend fun getVocabularyTitles(access_token: String): VocabularyResponse {
        return apiClient.apiService.getVocabularyTitles(access_token)
    }

    override suspend fun getVocabularyByTitle(
        access_token: String,
        slug: String
    ): VocabularyByTitleResponse {
        return apiClient.apiService.getVocabularyByTitle(access_token, slug)
    }

    override suspend fun getVocabularyCategory(
        access_token: String,
        slug: String,
        category: String
    ): VocabularyCategoryResponse {
        return apiClient.apiService.getVocabularyCategory(access_token, slug, category)
    }

    override suspend fun getToiec(access_token: String): ToeicResponse {
        return apiClient.apiService.getToiec(access_token)
    }

    override suspend fun getToeicExam(
        access_token: String,
        id: String
    ): ToeicExamResponse {
        return apiClient.apiService.getToeicExam(access_token, id)
    }

    override suspend fun getQuestionToeic(
        access_token: String,
        id: String
    ): QuestionResponse {
        return apiClient.apiService.getQuestionToeic(access_token, id)
    }

    override suspend fun postScore(
        access_token: String,
        scoreRequest: ScoreRequest
    ): ScoreResponse {
        return apiClient.apiService.postScore(access_token, scoreRequest)
    }

    override suspend fun postWritten(
        access_token: String,
        prompt: GeminiRequest
    ): GeminiResponse {
        return apiClient.apiService.postWritten(access_token, prompt)
    }

    override suspend fun getTitleWritten(access_token: String): TitleGeminiResponse {
        return apiClient.apiService.getTitleWritten(access_token)
    }


}