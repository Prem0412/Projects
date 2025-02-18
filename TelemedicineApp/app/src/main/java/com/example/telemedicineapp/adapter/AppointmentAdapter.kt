package com.example.telemedicineapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.telemedicineapp.R
import com.example.telemedicineapp.data.Appointment

class AppointmentAdapter(private val appointmentList: List<Appointment>) :
    RecyclerView.Adapter<AppointmentAdapter.AppointmentViewHolder>() {

    class AppointmentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val patientNameTextView: TextView = itemView.findViewById(R.id.textView_PatientName)
        val dateTextView: TextView = itemView.findViewById(R.id.textView_Date)
        val timeSlotTextView: TextView = itemView.findViewById(R.id.textView_TimeSlot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_appointment, parent, false)
        return AppointmentViewHolder(view)
    }

    override fun onBindViewHolder(holder: AppointmentViewHolder, position: Int) {
        val appointment = appointmentList[position]
        holder.patientNameTextView.text = appointment.patientName
        holder.dateTextView.text = appointment.date
        holder.timeSlotTextView.text = appointment.timeSlot
    }

    override fun getItemCount(): Int = appointmentList.size
}
