package com.example.shoppinglist

import android.content.Context
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE

class SharedPrefSessionManager(context: Context) {
    var sharedPreferences = context.getSharedPreferences("ShoppingApp_Data", MODE_PRIVATE)


    fun saveUser(
         userName:String,
         userEmail:String,
         userNumber:String,
         userPassword:String ){
        var editor = sharedPreferences.edit()
        editor.putString("User_Name",userName)
        editor.putString("User_Email",userEmail)
        editor.putString("User_Number",userNumber)
        editor.putString("User_Password",userPassword)
        editor.putBoolean("Is_Logged_In",true)
        editor.apply()
    }

    fun isUserLoggedIn():Boolean{

      return sharedPreferences.getBoolean("Is_Logged_In",false)

    }

   fun getUserEmail():String? {
       return sharedPreferences.getString("User_Email",null)
    }
    fun getUserPassword():String? {
      return  sharedPreferences.getString("User_Password",null)
    }

    fun logOut(){
        var editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }
}