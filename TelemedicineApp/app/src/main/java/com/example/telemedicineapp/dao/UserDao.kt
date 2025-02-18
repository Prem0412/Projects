package com.example.telemedicineapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.telemedicineapp.database.User

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE email = :email AND password = :password ")
    suspend  fun loginUser(email: String, password: String): User?

    @Query("SELECT COUNT(*) FROM users")
    suspend fun isUserRegistered(): Boolean
}
