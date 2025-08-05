package com.example.apitask
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("products")
    suspend fun getProducts(): List<Product>
}