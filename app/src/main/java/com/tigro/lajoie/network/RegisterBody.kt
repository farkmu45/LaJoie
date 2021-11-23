package com.tigro.lajoie.network

data class RegisterBody(
    val email: String,
    val password: String,
    val name: String,
    val username: String
)
