package com.example.telemedicineapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.telemedicineapp.database.AppDatabase
import com.example.telemedicineapp.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    val user: User? = withContext(Dispatchers.IO) {
                        db.userDao().loginUser(email, password)
                    }

                    withContext(Dispatchers.Main) {
                        if (user != null) {
                            Toast.makeText(this@LoginActivity, "Login Successful", Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@LoginActivity, OnboardDoctorActivity::class.java))
                            finish() // Close login activity
                        } else {
                            Toast.makeText(this@LoginActivity, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    e.printStackTrace()
                }
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
        }
    }
}
