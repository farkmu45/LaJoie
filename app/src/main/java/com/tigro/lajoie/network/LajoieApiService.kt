package com.tigro.lajoie.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tigro.lajoie.models.*
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private const val URL = "https://lajoie-api.herokuapp.com/"

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(URL).build()

interface LajoieApiService {

    // Wall related requests
    @POST("walls.php")
    suspend fun sendQuestion(
        @Header("Authorization") token: String,
        @Body wallBody: WallBody
    ): SuccessResponse

    @GET("walls.php")
    suspend fun getWalls(@Header("Authorization") token: String): List<Wall>

    @GET("history.php")
    suspend fun getHistory(@Header("Authorization") token: String): List<History>

    // Comment related requests
    @GET("walls.php")
    suspend fun getResponses(
        @Header("Authorization") token: String,
        @Query("id") id: Int
    ): List<Comment>

    @POST("walls.php")
    suspend fun addResponse(
        @Body commentBody: CommentBody,
        @Header("Authorization") token: String,
        @Query("id") id: Int
    )

    // Knowledge related requests
    @GET("knowledges.php")
    suspend fun getKnowledges(): List<Knowledge>

    @GET("knowledges.php")
    suspend fun getKnowledgeDetail(@Query("id") id: Int): List<KnowledgeDetail>

    // Auth related requests
    @POST("login.php")
    suspend fun login(@Body loginBody: LoginBody): User

    @POST("register.php")
    suspend fun register(@Body registerBody: RegisterBody): SuccessResponse

    @GET("profile.php")
    suspend fun getUser(@Header("Authorization") token: String): User

    // Submission related request
    @POST("submission.php")
    suspend fun sendSubmission(
        @Body submissionBody: SubmissionBody,
        @Header("Authorization") token: String

    )

    @Headers(
        "Accept: application/vnd.uploadcare-v0.5+json",
        "Authorization: Uploadcare.Simple 574887dd15eae2ede6e6:62ecefc595036cc43f33"
    )
    @DELETE()
    suspend fun deleteImage(
        @Url ucareURL: String
    )
}

object LajoieApi {
    val retrofitService: LajoieApiService by lazy {
        retrofit.create(LajoieApiService::class.java)
    }
}