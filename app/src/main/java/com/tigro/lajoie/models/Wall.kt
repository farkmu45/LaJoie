package com.tigro.lajoie.models

import com.squareup.moshi.Json

data class Wall(
    val id: Int,
    @Json(name = "created_at") val createdAt: String,
    @Json(name = "comment_count") val commentCount: String,
    val title: String,
    val detail: String,
    val username: String,
    val picture: String?
)