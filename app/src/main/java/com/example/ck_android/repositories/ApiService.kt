package com.example.ck_android.repositories

import com.example.ck_android.model.ChatRequest
import com.example.ck_android.model.ChatResponse
import com.example.ck_android.model.CheckCodeRequest
import com.example.ck_android.model.CheckCodeResponse
import com.example.ck_android.model.FavouriteCancelResponse
import com.example.ck_android.model.FavouriteListResponse
import com.example.ck_android.model.FavouriteRequest
import com.example.ck_android.model.FavouriteResponse
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
import com.example.ck_android.model.TestDefineRequest
import com.example.ck_android.model.TestDefineResponse
import com.example.ck_android.model.TestQuizzRequest
import com.example.ck_android.model.TestQuizzResponse
import com.example.ck_android.model.TitleGeminiResponse
import com.example.ck_android.model.ToeicExamResponse
import com.example.ck_android.model.ToeicResponse
import com.example.ck_android.model.UpdateUserProfileRequest
import com.example.ck_android.model.UpdateUserProfileResponse
import com.example.ck_android.model.UserProfileResponse
import com.example.ck_android.model.VocabularyByTitleResponse
import com.example.ck_android.model.VocabularyCategoryResponse
import com.example.ck_android.model.VocabularyResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    // api auth
    @POST("auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginRespon

    @Multipart
    @POST("auth/register")
    suspend fun register(
        @Part avatar: MultipartBody.Part?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("phone") phone: RequestBody
    ): RegisterResponse

    @POST("auth/check-code")
    suspend fun checkCode(@Body checkCodeRequest: CheckCodeRequest): CheckCodeResponse

    @GET("auth/profile")
    suspend fun getProfile(@Header("Authorization") access_token: String): UserProfileResponse

    // api grammar
    @GET("grammars")
    suspend fun getGrammars(@Header("Authorization") access_token: String): GrammarResponse

    @GET("grammars/{slug}")
    suspend fun getGrammarItem(
        @Header("Authorization") access_token: String,
        @Path("slug") slug: String
    ): GrammarItemResponse

    // api chatGpt
    @POST("chat")
    suspend fun chatGpt(
        @Header("Authorization") access_token: String,
        @Body chatRequest: ChatRequest
    ): ChatResponse

    // api vocabulary
    @GET("vocabulary/titles")
    suspend fun getVocabularyTitles(@Header("Authorization") access_token: String): VocabularyResponse

    @GET("vocabulary/{slug}")
    suspend fun getVocabularyByTitle(
        @Header("Authorization") access_token: String,
        @Path("slug") slug: String
    ): VocabularyByTitleResponse

    @GET("vocabulary/{slug}/{category}")
    suspend fun getVocabularyCategory(
        @Header("Authorization") access_token: String,
        @Path("slug") slug: String,
        @Path("category") category: String
    ): VocabularyCategoryResponse

    @POST("vocabulary/random")
    suspend fun getVocabularyRandom(
        @Header("Authorization") access_token: String,
        @Body testDefineRequest: TestDefineRequest
    ): TestDefineResponse

    @POST("vocabulary/randomized")
    suspend fun getVocabularyRandomized(
        @Header("Authorization") access_token: String,
        @Body testQuizzRequest: TestQuizzRequest
    ): TestQuizzResponse


    // api exam
    @GET("exam")
    suspend fun getToiec(@Header("Authorization") access_token: String): ToeicResponse

    @GET("exam/{id}")
    suspend fun getToeicExam(
        @Header("Authorization") access_token: String,
        @Path("id") id: String
    ): ToeicExamResponse

    // api question toeic
    @GET("question/{id}")
    suspend fun getQuestionToeic(
        @Header("Authorization") access_token: String,
        @Path("id") id: String
    ): QuestionResponse

    // api score
    @POST("score")
    suspend fun postScore(
        @Header("Authorization") access_token: String,
        @Body scoreRequest: ScoreRequest
    ): ScoreResponse

    // api gemini
    @POST("gemini/prompt")
    suspend fun postWritten(
        @Header("Authorization") access_token: String,
        @Body prompt: GeminiRequest
    ): GeminiResponse

    @GET("gemini/title")
    suspend fun getTitleWritten(
        @Header("Authorization") access_token: String
    ): TitleGeminiResponse

    // api favourite
    @POST("favourite")
    suspend fun postFavouriteVocb(
        @Header("Authorization") access_token: String,
        @Body favouriteRequest: FavouriteRequest
    ): FavouriteResponse

    @GET("favourite")
    suspend fun getFavouriteList(
        @Header("Authorization") access_token: String
    ): FavouriteListResponse

    @DELETE("favourite/{id}")
    suspend fun deleteFavouriteVocb(
        @Header("Authorization") access_token: String,
        @Path("id") id: String
    ): FavouriteCancelResponse

    // api user
    @PATCH("users/{id}")
    suspend fun updateProfileUser(
        @Header("Authorization") access_token: String,
        @Path("id") id: String,
        @Body updateUserProfileRequest: UpdateUserProfileRequest
    ): UpdateUserProfileResponse
}