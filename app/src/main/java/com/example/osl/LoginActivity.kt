package com.example.osl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val editTextUSN = findViewById<EditText>(R.id.editTextUsn)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)

        // Initialize UserRepository
        userRepository = UserRepository(AppDatabase.getInstance(this).userDao())

        buttonLogin.setOnClickListener {
            val usn = editTextUSN.text.toString()
            val password = editTextPassword.text.toString()

            // Check if the user exists in the database
            lifecycleScope.launch {
                val user = userRepository.login(usn, password)
                if (user != null) {
                    // User is registered, navigate to the live page
                    val intent = Intent(this@LoginActivity, LiveActivity::class.java).apply {
                        putExtra("userName", user.name)
                        putExtra("userUsn", user.usn)
                    }
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(this@LoginActivity, "User not registered", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
