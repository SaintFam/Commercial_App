package com.example.saintfam.models

data class UserModel(
    val userName: String = "",
    val email: String = "",
    val uid: String = "",
    val cartItems : Map<String, Long> = emptyMap()
)
