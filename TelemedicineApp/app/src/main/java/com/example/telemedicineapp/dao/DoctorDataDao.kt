package com.example.telemedicineapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.telemedicineapp.database.DoctorData

@Dao
interface DoctorDataDao {

    @Query( "SELECT * FROM doctor" )
  suspend fun getDoctorData(): List<DoctorData>

    @Insert
    suspend fun insertDoctor(doctorData: DoctorData)

    @Update
    suspend fun updateDoctor(doctor: DoctorData)

    @Delete
    suspend  fun deleteDoctor(doctorData: DoctorData)



}