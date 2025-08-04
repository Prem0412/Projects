package com.example.shoppinglist.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("ListTable")
data class ListEntity(
    @PrimaryKey
    val listTitle:String
)