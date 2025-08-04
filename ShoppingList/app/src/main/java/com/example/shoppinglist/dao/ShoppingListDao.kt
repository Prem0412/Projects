package com.example.shoppinglist.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.shoppinglist.entity.ListEntity
import com.example.shoppinglist.entity.ListEntityAndTaskEntity
import com.example.shoppinglist.entity.TaskEntity

@Dao
interface ShoppingListDao {

    @Query("SELECT * FROM ListTable")
    suspend fun getAllLists(): MutableList<ListEntity>

    @Query("SELECT * FROM TaskTable WHERE listName = :listTitle")
    suspend fun getAllTasksOfList(listTitle:String): MutableList<TaskEntity>

    @Query("SELECT * FROM ListTable")
    suspend fun getAllTasksWithList() : List<ListEntityAndTaskEntity>

    @Insert
    suspend fun addList(list: ListEntity)

    @Update
    suspend fun updateList(list: ListEntity)

    @Delete
    suspend fun deleteList(list: ListEntity)

    @Insert
    suspend fun addTask(task: TaskEntity)

    @Update
    suspend fun updateTask(task: TaskEntity)

    @Delete
    suspend fun deleteTask(task: TaskEntity)
}
