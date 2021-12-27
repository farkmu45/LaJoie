package com.tigro.lajoie.models

import com.squareup.moshi.Json

data class User(
    val token: String?,
    val username: String,
    val name: String,
    val status: String,
    val picture: String,
    @Json(name = "user_type_id") val type: Int
)
