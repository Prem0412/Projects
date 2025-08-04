package com.example.shoppinglist.model

data class rvModel(
    val title:String,
    val type:ItemType
    )


enum class ItemType{
    LIST, TASK
}