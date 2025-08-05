package com.example.apitask

data class Product(
    val category: Category,
    val description: String,
    val id: Int,
    val images: List<String>,
    val price: Int,
    val slug: String,
    val title: String
)