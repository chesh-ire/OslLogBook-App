package com.example.osl

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.Calendar
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

        // Set the user data to the TextViews
        textViewUserName.text = "Name: $userName"
        textViewUserUsn.text = "USN: $userUsn"

        // Get and display the current time
        val currentTime = getCurrentTime()
        textViewLoginTime.text = "Login Time: $currentTime"
    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}

