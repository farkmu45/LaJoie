package com.tigro.lajoie.models

import com.squareup.moshi.Json

data class KnowledgeDetail(
    @Json(name = "knowledge_id") val id: Int,
    val name: String,
    val text: String
)
