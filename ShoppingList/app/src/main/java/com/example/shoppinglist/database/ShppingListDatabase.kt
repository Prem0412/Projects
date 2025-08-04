package com.example.shoppinglist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.dao.ShoppingListDao
import com.example.shoppinglist.entity.ListEntity
import com.example.shoppinglist.entity.TaskEntity

@Database(
    entities = [ListEntity::class, TaskEntity::class],
    version = 2,
    exportSchema = false
)
abstract class ShoppingDatabase : RoomDatabase() {

    abstract fun shoppingListDao(): ShoppingListDao

    companion object {
        @Volatile
        private var INSTANCE: ShoppingDatabase? = null

        fun getDatabase(context: Context): ShoppingDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java,
                    "shopping_database"
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}