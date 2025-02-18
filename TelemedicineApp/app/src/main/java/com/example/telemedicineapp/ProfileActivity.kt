package com.example.telemedicineapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.telemedicineapp.database.DoctorData
import com.example.telemedicineapp.database.DoctorDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProfileActivity : AppCompatActivity() {

    lateinit var etFullName: EditText
    lateinit var etPhoneNumber: EditText
    lateinit var etLicense: EditText
    lateinit var etClinicAddress: EditText
    lateinit var etSpecialization: EditText
    lateinit var etEmail: EditText
    lateinit var btnUpdate: Button

    private lateinit var doctorDB: DoctorDatabase
    private var doctorId: Int? = null  // To store the doctor's ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        etFullName = findViewById(R.id.editText_FullName_Profile)
        etPhoneNumber = findViewById(R.id.editText_PhoneNumber_Profile)
        etLicense = findViewById(R.id.editText_License_Profile)
        etClinicAddress = findViewById(R.id.editText_ClinicAddress_Profile)
        etSpecialization = findViewById(R.id.editText_Specialization_Profile)
        etEmail = findViewById(R.id.editText_Email_Profile)
        btnUpdate = findViewById(R.id.button_Update_Profile)

        doctorDB = DoctorDatabase.getDRDatabase(this)

        // Fetch the first doctor asynchronously
        loadDoctorData()

        btnUpdate.setOnClickListener {
            updateProfile()
        }
    }

    private fun loadDoctorData() {
        lifecycleScope.launch {
            val doctor = getDoctorData()

            if (doctor != null) {
                doctorId = doctor.id  // Store the ID for updates
                etFullName.setText(doctor.fullNameDR)
                etPhoneNumber.setText(doctor.phoneNumber)
                etLicense.setText(doctor.licenseNumber)
                etClinicAddress.setText(doctor.clinicAddress)
                etSpecialization.setText(doctor.specilization)
                etEmail.setText(doctor.email)
            }
        }
    }

    private suspend fun getDoctorData(): DoctorData? {
        return withContext(Dispatchers.IO) {
            doctorDB.doctorDataDao().getDoctorData().firstOrNull()
        }
    }

    private fun updateProfile() {
        if (doctorId == null) {
            Toast.makeText(this, "No profile found to update!", Toast.LENGTH_SHORT).show()
            return
        }

        val updatedDoctor = DoctorData(
            id = doctorId!!,
            fullNameDR = etFullName.text.toString(),
            phoneNumber = etPhoneNumber.text.toString(),
            licenseNumber = etLicense.text.toString(),
            clinicAddress = etClinicAddress.text.toString(),
            specilization = etSpecialization.text.toString(),
            email = etEmail.text.toString()
        )

        // Update doctor data asynchronously
        lifecycleScope.launch {
            updateDoctorData(updatedDoctor)
            Toast.makeText(this@ProfileActivity, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
        }
    }

    private suspend fun updateDoctorData(doctor: DoctorData) {
        withContext(Dispatchers.IO) {
            doctorDB.doctorDataDao().updateDoctor(doctor)
        }
    }
}
