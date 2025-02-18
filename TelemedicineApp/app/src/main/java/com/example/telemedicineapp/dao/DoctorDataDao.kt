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
    fun getDoctorData(): List<DoctorData>

    @Insert
    fun insertDoctor(doctorData: DoctorData)

    @Update
    fun updateDoctor(doctor: DoctorData)

    @Delete
    fun deleteDoctor(doctorData: DoctorData)



}