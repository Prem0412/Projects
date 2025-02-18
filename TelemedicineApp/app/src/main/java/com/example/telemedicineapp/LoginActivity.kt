package com.example.telemedicineapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.telemedicineapp.database.AppDatabase
import com.example.telemedicineapp.database.User

class LoginActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var registerLink: TextView
    private lateinit var forgotPasswordLink: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        userEmail = findViewById(R.id.editText_Email_LoginScreen)
        userPassword = findViewById(R.id.editText_Password_LoginScreen)
        btnLogin = findViewById(R.id.button_LOGIN_LoginScreen)
        registerLink = findViewById(R.id.textView_textUnableTOLogin_LoginScreen)
        forgotPasswordLink = findViewById(R.id.textView_textForgotPassword_LoginScreen)

        // Initialize Room database
        db = AppDatabase.getDatabase(this)

        btnLogin.setOnClickListener {
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            // Check if fields are empty
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            try {
                // Fetch user from database
                val user: User? = db.userDao().loginUser(email, password)

                if (user != null) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@LoginActivity, OnboardDoctorActivity::class.java))
                    finish() // Close login activity
                } else {
                    Toast.makeText(this, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                e.printStackTrace() // Debugging
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
