package com.example.telemedicineapp.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity(tableName = "doctor")
data class DoctorData(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val phoneNumber: String,
    val fullNameDR:String,
    val email:String,
    val specilization:String,
    val licenseNumber: String,
    val clinicAddress:String

)
