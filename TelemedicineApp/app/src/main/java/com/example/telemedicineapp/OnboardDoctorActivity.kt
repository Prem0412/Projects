package com.example.telemedicineapp

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.telemedicineapp.adapter.AppointmentAdapter
import com.example.telemedicineapp.data.Appointment

class OnboardDoctorActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var appointmentAdapter: AppointmentAdapter
   lateinit var personImage: ImageView
    lateinit var breadImage:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboard_doctor)

        recyclerView = findViewById(R.id.recyclerView_AppintmentList_DashboardScreen)
      personImage = findViewById(R.id.imageView_DrImage_OnboardDoctorActivity)
       breadImage = findViewById(R.id.imageView_sandvich_OnboardDoctorActivity)

        // Hardcoded appointment data
        val appointmentList = listOf(
            Appointment("Person 1", "2025-02-18", "10:00 AM - 10:30 AM"),
            Appointment("Person 2", "2025-02-18", "11:00 AM - 11:30 AM"),
            Appointment("Person 3", "2025-02-18", "12:00 PM - 12:30 PM"),
            Appointment("Person 4", "2025-02-18", "01:00 PM - 01:30 PM"),
            Appointment("Person 5", "2025-02-18", "02:00 PM - 02:30 PM"),
            Appointment("Person 6", "2025-02-18", "03:00 PM - 03:30 PM"),
            Appointment("Person 7", "2025-02-18", "04:00 PM - 04:30 PM"),
            Appointment("Person 8", "2025-02-18", "05:00 PM - 05:30 PM"),
            Appointment("Person 9",  "2025-02-18", "06:00 PM - 06:30 PM"),
            Appointment("Person 10", "2025-02-18", "07:00 PM - 07:30 PM")
        )

        // Set up RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        appointmentAdapter = AppointmentAdapter(appointmentList)
        recyclerView.adapter = appointmentAdapter

        personImage.setOnClickListener(){

            startActivity(Intent(this,OnboardingFormActivity::class.java))
        }
    }
}