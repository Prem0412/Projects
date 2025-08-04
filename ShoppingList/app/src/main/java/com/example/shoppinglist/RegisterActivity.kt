package com.example.shoppinglist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglist.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

   lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var userName = binding.etNameRS.text.toString().trim()
        var userEmail = binding.etEmailRS.text.toString().trim()
        var userNumber = binding.etNumberRS.text.toString().trim()
        var userPassword = binding.etPasswordRS.text.toString().trim()
        var btnRegister = binding.btnRegisterRS

        var sharedPref = SharedPrefSessionManager(this)
        btnRegister.setOnClickListener {
            var userName = binding.etNameRS.text.toString().trim()
            var userEmail = binding.etEmailRS.text.toString().trim()
            var userNumber = binding.etNumberRS.text.toString().trim()
            var userPassword = binding.etPasswordRS.text.toString().trim()
            if(userName.isNotEmpty() && userEmail.isNotEmpty()&& userName.isNotEmpty()&& userPassword.isNotEmpty()) {
                sharedPref.saveUser(userName, userEmail, userNumber, userPassword)
                Toast.makeText(this,"Registered Successfully",Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,LoginActivity::class.java))
            }else {
                Toast.makeText(this,"Fill all required fields",Toast.LENGTH_SHORT).show()
            }


        }
       
    }
}