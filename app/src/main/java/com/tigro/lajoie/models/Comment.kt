package com.tigro.lajoie.models

import com.squareup.moshi.Json

data class Comment(
    val username: String,
    val comment: String,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "user_type_id") val type: Int
)
