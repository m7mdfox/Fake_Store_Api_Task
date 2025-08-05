package com.example.apitask

class Repository : ApiService {
    override suspend fun getProducts(): List<Product> {
        return RetrofitInstance.api.getProducts()
    }

}