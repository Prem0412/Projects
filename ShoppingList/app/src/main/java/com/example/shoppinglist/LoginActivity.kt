package com.example.shoppinglist

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.shoppinglist.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
       var sharedPref = SharedPrefSessionManager(this)


        var btnLogin = binding.btnLoginLS

        btnLogin.setOnClickListener {

            var userEmail = binding.etEmailLS.text.toString().trim()
            var userPassword = binding.etPasswordLS.text.toString().trim()
            if(userEmail == sharedPref.getUserEmail() && userPassword == sharedPref.getUserPassword())
            {

             startActivity(Intent(this,HomeActivity::class.java))
                sharedPref.isUserLoggedIn()

            }else  Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show()
        }

    }
}