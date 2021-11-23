package com.tigro.lajoie.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tigro.lajoie.models.AuthToken
import com.tigro.lajoie.models.Knowledge
import com.tigro.lajoie.models.SuccessResponse
import com.tigro.lajoie.models.Wall
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST


private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private const val URL = "https://3f33-125-166-117-52.ngrok.io/uni/LaJoie/"

private val retrofit =
    Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(URL).build()

interface LajoieApiService {
    @GET("walls.php")
    suspend fun getWalls(@Header("Authorization") token: String): List<Wall>

    @GET("knowledges.php")
    suspend fun getKnowledges(): List<Knowledge>

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