package com.example.telemedicineapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.telemedicineapp.database.AppDatabase
import com.example.telemedicineapp.database.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    private lateinit var userPhoneNumber: EditText
    private lateinit var userEmail: EditText
    private lateinit var userPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var db: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        userPhoneNumber = findViewById(R.id.editText_MobileNumber_RegisterScreen)
        userEmail = findViewById(R.id.editText_Email_RegisterScreen)
        userPassword = findViewById(R.id.editText_Password_RegisterScreen)
        btnRegister = findViewById(R.id.button_REGISTER_RegisterScreen)

        db = AppDatabase.getDatabase(this)

        // Check if user is already registered
        lifecycleScope.launch {
            val userExists = withContext(Dispatchers.IO) {
                db.userDao().isUserRegistered()
            }

            if (userExists) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegisterActivity, "User already registered. Redirecting to login.", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                    finish() // Close registration activity
                }
            }
        }

        btnRegister.setOnClickListener {
            val phone = userPhoneNumber.text.toString().trim()
            val email = userEmail.text.toString().trim()
            val password = userPassword.text.toString().trim()

            if (phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                try {
                    withContext(Dispatchers.IO) {
                        db.userDao().insertUser(User(email = email, password = password, phoneNum = phone))
                    }

                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Registration Successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
                        finish()
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@RegisterActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                    e.printStackTrace()
                }
            }
        }
    }
}
