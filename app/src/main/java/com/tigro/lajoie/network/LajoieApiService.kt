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
    @POST("walls.php")
    suspend fun sendQuestion(
        @Header("Authorization") token: String,
        @Body wallBody: WallBody
    ): SuccessResponse

    @GET("walls.php")
    suspend fun getWalls(@Header("Authorization") token: String): List<Wall>

    @GET("knowledges.php")
    suspend fun getKnowledges(): List<Knowledge>

    @GET("knowledges.php")
    suspend fun getKnowledgeDetail(@Query("id") id: Int): List<KnowledgeDetail>

    @POST("login.php")
    suspend fun login(@Body loginBody: LoginBody): AuthToken

    @POST("register.php")
    suspend fun register(@Body registerBody: RegisterBody): SuccessResponse
}

object LajoieApi {
    val retrofitService: LajoieApiService by lazy {
        retrofit.create(LajoieApiService::class.java)
    }
}