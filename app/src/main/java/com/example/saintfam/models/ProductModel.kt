package com.example.saintfam.models

data class ProductModel(
    val id: String = "",
    val title: String ="",
    val description: String = "",
    val price: String = "",
    val actualPrice: String="",
    val category: String = "",
    val image : List<String> = emptyList(),
    val otherDetails : Map<String, String> = mapOf()
)
