package com.example.osl



import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

class LoginActivity : AppCompatActivity() {

    private lateinit var editTextUsn: EditText
    private lateinit var editTextPassword: EditText
    private lateinit var buttonLogin: Button

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        Log.d("LoginActivity", "onCreate: LoginActivity created")

        editTextUsn = findViewById(R.id.editTextUsn)
        editTextPassword = findViewById(R.id.editTextPassword)
        buttonLogin = findViewById(R.id.buttonLogin)

        // Initialize UserRepository
        userRepository = UserRepository(AppDatabase.getInstance(this).userDao())

        buttonLogin.setOnClickListener {
            Log.d("LoginActivity", "Login button clicked")
            val usn = editTextUsn.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if (usn.isNotEmpty() && password.isNotEmpty()) {
                Log.d("LoginActivity", "USN entered: $usn")

                lifecycleScope.launch {
                    val user = userRepository.login(usn, password)
                    if (user != null) {
                        val currentTime = System.currentTimeMillis()
                        val formattedTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(currentTime))
                        Log.d("LoginActivity", "Current time: $formattedTime")

                        val loginDetails = LoginDetails(
                            usn = user.usn,
                            name = user.name,
                            loginTime = currentTime
                        )

                        Log.d("LoginActivity", "LoginDetails created: $loginDetails")

                        // Insert login details into Room database
                        try {
                            AppDatabase.getInstance(this@LoginActivity).loginDetailsDao().insertLoginDetails(loginDetails)
                            Log.d("LoginActivity", "LoginDetails inserted into database")
                        } catch (e: Exception) {
                            Log.e("LoginActivity", "Error inserting LoginDetails: ${e.message}")
                        }

                        // Proceed to home page activity
                        startActivity(Intent(this@LoginActivity, LiveActivity::class.java).apply {
                            putExtra("userName", user.name)
                            putExtra("userUsn", user.usn)
                            putExtra("loginTime", currentTime)
                        })
                        finish() // Close current activity
                    } else {
                        Toast.makeText(this@LoginActivity, "Invalid USN or Password", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter your USN and Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
