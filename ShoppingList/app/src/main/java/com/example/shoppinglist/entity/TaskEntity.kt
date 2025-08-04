package com.example.shoppinglist.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "TaskTable",
    foreignKeys = [
        ForeignKey(
            entity = ListEntity::class,
            parentColumns = ["listTitle"],
            childColumns = ["listName"],
            onDelete = ForeignKey.CASCADE // ✅ THIS FIXED LINE
        )
    ]
)
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val taskId: Int = 0,
    val listName: String,  // Must match ListEntity.listTitle
    val task: String
)
