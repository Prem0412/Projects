package com.example.telemedicineapp

import android.content.Intent
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

            // Insert doctor data asynchronously
            insertDoctorData(doctor, doctorDB)
        }
    }

    private fun insertDoctorData(doctor: DoctorData, doctorDB: DoctorDatabase) {
        lifecycleScope.launch {
            // Perform the insert operation in a background thread
            withContext(Dispatchers.IO) {
                doctorDB.doctorDataDao().insertDoctor(doctor)
            }

            // Show a Toast on the main thread after inserting data
            withContext(Dispatchers.Main) {
                Toast.makeText(this@OnboardingFormActivity, "Doctor Registered!", Toast.LENGTH_SHORT).show()

                // Redirect to ProfileActivity
                val intent = Intent(this@OnboardingFormActivity, ProfileActivity::class.java)
                startActivity(intent)
                finish()  // Close the current activity
            }
        }
    }
}
