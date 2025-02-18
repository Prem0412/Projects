package com.example.telemedicineapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.telemedicineapp.dao.DoctorDataDao
import com.example.telemedicineapp.database.DoctorData
import com.example.telemedicineapp.database.DoctorDatabase

class OnboardingFormActivity : AppCompatActivity() {

    lateinit var etFullNameOfDoctor: EditText
    lateinit var etPhoneNumberOfDoctor: EditText
    lateinit var etLicenseOfDoctor: EditText
    lateinit var etClinicAddressOfDoctor: EditText
    lateinit var etSpecilizationOfDoctor: EditText
    lateinit var etEmailOfDoctor: EditText
    lateinit var btnOnboard: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_form)

        etFullNameOfDoctor = findViewById(R.id.editText_FULLNAME_OnboardingFormScreen)
        etPhoneNumberOfDoctor = findViewById(R.id.editText_PhoneNumber_OnboardingFormScreen)
        etLicenseOfDoctor = findViewById(R.id.editText_License_OnboardingFormScreen)
        etClinicAddressOfDoctor = findViewById(R.id.editText_ClinicAddress_OnboardingFormScreen)
        etSpecilizationOfDoctor = findViewById(R.id.editText_Specialization_OnboardingFormScreen)
        etEmailOfDoctor = findViewById(R.id.editText_Email_OnboardingFormScreen)
        btnOnboard = findViewById(R.id.button_ONBOARD_OnboardingFormScreen)

        val doctorDB = DoctorDatabase.getDRDatabase(this)

        btnOnboard.setOnClickListener {

            val doctor = DoctorData(
                phoneNumber = etPhoneNumberOfDoctor.text.toString(),
                fullNameDR = etFullNameOfDoctor.text.toString(),
                email = etEmailOfDoctor.text.toString(),
                specilization = etSpecilizationOfDoctor.text.toString(),
                licenseNumber = etLicenseOfDoctor.text.toString(),
                clinicAddress = etClinicAddressOfDoctor.text.toString()
            )

            doctorDB.doctorDataDao().insertDoctor(doctor)

            Toast.makeText(this, "Doctor Registered!", Toast.LENGTH_SHORT).show()

            // Redirect to ProfileActivity
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
            finish()  // Close th
        }
    }}