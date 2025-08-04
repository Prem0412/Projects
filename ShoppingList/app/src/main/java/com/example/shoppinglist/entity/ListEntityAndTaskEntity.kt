package com.example.shoppinglist.entity

import androidx.room.Embedded
import androidx.room.Relation


data class ListEntityAndTaskEntity (
    @Embedded
    val list: ListEntity,
    @Relation(
        parentColumn = "listTitle",
        entityColumn = "listName"
    )
    val task : List<TaskEntity>
)