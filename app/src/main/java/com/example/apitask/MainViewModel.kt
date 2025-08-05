package com.example.apitask

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {
    private val repository: Repository = Repository()
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> = _products

    fun loadItems() {
        viewModelScope.launch{
            try {
                val result = repository.getProducts()
                _products.value = result
            } catch (e: Exception) {
                TODO("i should handle errors")
            }
        }
    }
}