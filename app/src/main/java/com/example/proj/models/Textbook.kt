package com.example.proj.models

data class Textbook(

    //val id: String  = java.util.UUID.randomUUID().toString(),
    val name: String,
    val module: String,
    val code: String,
    val isbn: String,
    val author: String,
    val price: String,
    val seller: User,
    var isSold: Boolean = false
)