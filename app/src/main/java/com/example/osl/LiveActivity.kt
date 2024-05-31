package com.example.osl

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class LiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live)

        val textViewUserName = findViewById<TextView>(R.id.textViewUserName)
        val textViewUserUsn = findViewById<TextView>(R.id.textViewUserUsn)
        val textViewLoginTime = findViewById<TextView>(R.id.textViewLoginTime)

        // Get the user data from extras
        val userName = intent.getStringExtra("userName")
        val userUsn = intent.getStringExtra("userUsn")
        val loginTime = intent.getLongExtra("loginTime", 0L)

        // Set the user data to the TextViews
        textViewUserName.text = "Name: $userName"
        textViewUserUsn.text = "USN: $userUsn"

        // Format and display the login time
        val formattedLoginTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(loginTime))
        textViewLoginTime.text = "Login Time: $formattedLoginTime"
    }
}
