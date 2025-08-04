package com.example.shoppinglist

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash)

      Handler(Looper.getMainLooper()).postDelayed({
          var pref=SharedPrefSessionManager(this)

          var intent1 = Intent(this,RegisterActivity::class.java)
          var intent2 = Intent(this,LoginActivity::class.java)
            if (pref.isUserLoggedIn())
            { startActivity(intent1)}else{startActivity(intent2)}

      }, 3000)
    }
}