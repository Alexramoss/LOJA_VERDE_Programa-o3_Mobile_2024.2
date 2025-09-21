package com.example.myapplication.model

data class ProductsModel(
    val id: String = "",
    val imageURL: List<String> = emptyList(),
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val newPrice: String = ""
) {

}