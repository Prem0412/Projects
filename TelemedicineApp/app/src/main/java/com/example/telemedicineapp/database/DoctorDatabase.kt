package com.example.telemedicineapp.database

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.telemedicineapp.dao.DoctorDataDao


//

@Database(entities = [DoctorData::class], version = 1, exportSchema = false)
abstract class DoctorDatabase : RoomDatabase() {
    abstract fun doctorDataDao(): DoctorDataDao

    companion object {
        @Volatile
        private var INSTANCE: DoctorDatabase? = null
        private const val DATABASE_NAME = "doctor_database"

        fun getDRDatabase(context: Context): DoctorDatabase {
            return INSTANCE ?: synchronized(this) {
                try {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        DoctorDatabase::class.java,
                        DATABASE_NAME
                    )
                        .fallbackToDestructiveMigration() // Handle version updates safely
                        .build()

                    INSTANCE = instance
                    instance
                } catch (e: Exception) {
                    Log.e("DB_ERROR", "DoctorDatabase initialization failed: ${e.message}", e)
                    throw RuntimeException("DoctorDatabase initialization failed", e) // Rethrow for debugging
                }
            }
        }
    }
}