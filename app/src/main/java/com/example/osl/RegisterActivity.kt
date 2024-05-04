package com.example.osl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextUSN = findViewById<EditText>(R.id.editTextUSN)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonNavigateToLogin = findViewById<Button>(R.id.buttonNavigateToLogin)
        val buttonForgotPassword = findViewById<Button>(R.id.buttonForgotPassword)

        // Initialize UserRepository
        userRepository = UserRepository(AppDatabase.getInstance(this).userDao())

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val usn = editTextUSN.text.toString()
            val password = editTextPassword.text.toString()

            // Register user
            lifecycleScope.launch {
                val isRegistered = registerUser(name, usn, password)
                if (isRegistered) {
                    // Registration successful, navigate to the live page
                    val intent = Intent(this@RegisterActivity, LiveActivity::class.java).apply {
                        putExtra("userName", name)
                        putExtra("userUsn", usn)
                    }
                    startActivity(intent)
                    finish()
                } else {
                }
            }
        }

        buttonNavigateToLogin.setOnClickListener {
            navigateToLogin()
        }
        buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    private suspend fun registerUser(name: String, usn: String, password: String): Boolean {

        return try {
            userRepository.registerUser(name, usn, password)
            true // Registration successful
        } catch (e: Exception) {
            false // Registration failed
        }
    }

    private fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)


    }


}









