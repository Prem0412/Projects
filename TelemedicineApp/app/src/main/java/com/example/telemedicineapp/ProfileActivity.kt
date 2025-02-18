package com.example.telemedicineapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.telemedicineapp.database.DoctorData
import com.example.telemedicineapp.database.DoctorDatabase

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

        // Fetch the first doctor (assuming one doctor per app for simplicity)
        val doctor = doctorDB.doctorDataDao().getDoctorData().firstOrNull()

        if (doctor != null) {
            doctorId = doctor.id  // Store the ID for updates
            etFullName.setText(doctor.fullNameDR)
            etPhoneNumber.setText(doctor.phoneNumber)
            etLicense.setText(doctor.licenseNumber)
            etClinicAddress.setText(doctor.clinicAddress)
            etSpecialization.setText(doctor.specilization)
            etEmail.setText(doctor.email)
        }

        btnUpdate.setOnClickListener {
            updateProfile()
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

        doctorDB.doctorDataDao().updateDoctor(updatedDoctor)
        Toast.makeText(this, "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
    }
}