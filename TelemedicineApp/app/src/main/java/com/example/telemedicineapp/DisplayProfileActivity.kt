package com.example.telemedicineapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.telemedicineapp.database.DoctorDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DisplayProfileActivity : AppCompatActivity() {

    private lateinit var tvFullName: TextView
    private lateinit var tvPhoneNumber: TextView
    private lateinit var tvLicenseNumber: TextView
    private lateinit var tvClinicAddress: TextView
    private lateinit var tvSpecialization: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnUpdateProfile: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_profile)

        tvFullName = findViewById(R.id.textView_FullName)
        tvPhoneNumber = findViewById(R.id.textView_PhoneNumber)
        tvLicenseNumber = findViewById(R.id.textView_License)
        tvClinicAddress = findViewById(R.id.textView_ClinicAddress)
        tvSpecialization = findViewById(R.id.textView_Specialization)
        tvEmail = findViewById(R.id.textView_Email)
        btnUpdateProfile = findViewById(R.id.button_UpdateProfile)

        val doctorDB = DoctorDatabase.getDRDatabase(this)

        // Fetch and display doctor data
        lifecycleScope.launch {
            val doctorList = withContext(Dispatchers.IO) {
                doctorDB.doctorDataDao().getDoctorData()
            }

            if (doctorList.isNotEmpty()) {
                val doctor = doctorList[0] // Assuming only one doctor exists
                tvFullName.text = "Full Name: ${doctor.fullNameDR}"
                tvPhoneNumber.text = "Phone: ${doctor.phoneNumber}"
                tvLicenseNumber.text = "License: ${doctor.licenseNumber}"
                tvClinicAddress.text = "Clinic Address: ${doctor.clinicAddress}"
                tvSpecialization.text = "Specialization: ${doctor.specilization}"
                tvEmail.text = "Email: ${doctor.email}"
            } else {
                Toast.makeText(this@DisplayProfileActivity, "No doctor data found!", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to ProfileActivity on button click
        btnUpdateProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }
    }
}
