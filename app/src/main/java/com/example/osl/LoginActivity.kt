package com.example.osl

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsn: EditText
    private lateinit var buttonLogin: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("MainActivity", "onCreate: MainActivity created")

        editTextUsn = findViewById(R.id.editTextUsn)
        buttonLogin = findViewById(R.id.buttonLogin)

        buttonLogin.setOnClickListener {
            Log.d("MainActivity", "Login button clicked")
            val usn = editTextUsn.text.toString().trim()

            if (usn.isNotEmpty()) {
                Log.d("MainActivity", "USN entered: $usn")

                val currentTime = getCurrentTime()
                val formattedTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(currentTime))
                Log.d("MainActivity", "Current time: $formattedTime")

                val loginDetails = LoginDetails(
                    usn = usn,
                    name = "User", // Replace with actual user name
                    loginTime = currentTime
                )

                Log.d("MainActivity", "LoginDetails created: $loginDetails")

                // Insert login details into Room database
                GlobalScope.launch {
                    try {
                        MyApplication.db.loginDetailsDao().insertLoginDetails(loginDetails)
                        Log.d("MainActivity", "LoginDetails inserted into database")
                    } catch (e: Exception) {
                        Log.e("MainActivity", "Error inserting LoginDetails: ${e.message}")
                    }
                }

                // Proceed to home page activity
                startActivity(Intent(this, LiveActivity::class.java))
                finish() // Close current activity
            } else {
                Toast.makeText(this, "Please enter your USN", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCurrentTime(): Long {
        return System.currentTimeMillis()
    }
}







