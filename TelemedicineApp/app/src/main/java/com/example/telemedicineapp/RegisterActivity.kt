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
import com.example.telemedicineapp.database.AppDatabase
import com.example.telemedicineapp.database.User

class RegisterActivity : AppCompatActivity() {

   lateinit var userPhoneNumber: EditText
    lateinit var userEmail: EditText
    lateinit  var userPassword:EditText
    lateinit  var btn_Register: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)



        userPhoneNumber = findViewById(R.id.editText_MobileNumber_RegisterScreen)
        userEmail = findViewById(R.id.editText_Email_RegisterScreen)
         userPassword = findViewById(R.id.editText_Password_RegisterScreen)
        btn_Register= findViewById(R.id.button_REGISTER_RegisterScreen)

        var db = AppDatabase.getDatabase(this)

        val userExists = db.userDao().isUserRegistered()

        if (userExists) {
            // If user already exists, redirect to Login screen
            Toast.makeText(this, "User already registered. Redirecting to login.", Toast.LENGTH_SHORT).show()
            startActivity(Intent( this, LoginActivity::class.java))

        }

        btn_Register.setOnClickListener(){

            var userPhoneNumber = userPhoneNumber.text.toString()
            var userEmail = userEmail.text.toString()
            var userPassword = userPassword.text.toString()

            val user = db.userDao().insertUser(User(email = userEmail, password = userPassword, phoneNum = userPhoneNumber))


        }


    }
}