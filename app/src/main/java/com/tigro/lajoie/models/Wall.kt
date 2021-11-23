package com.tigro.lajoie.models

import com.squareup.moshi.Json

data class Wall(
    val id: Int,
    @Json(name = "created_at") val createdAt: String,
    val title: String,
    val detail: String,
    val username: String
)