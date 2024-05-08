package com.example.osl

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class RegisterActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        mAuth = FirebaseAuth.getInstance()

        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextUSN = findViewById<EditText>(R.id.editTextUSN)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)
        val buttonNavigateToLogin = findViewById<Button>(R.id.buttonNavigateToLogin)
        val buttonForgotPassword = findViewById<Button>(R.id.buttonForgotPassword)

        buttonRegister.setOnClickListener {
            val name = editTextName.text.toString()
            val usn = editTextUSN.text.toString()
            val email = editTextEmail.text.toString()
            val password = editTextPassword.text.toString()

            // Register user with email and password
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Registration successful
                        val user = mAuth.currentUser
                        if (user != null) {
                            // Send email verification
                            user.sendEmailVerification().addOnCompleteListener { verificationTask ->
                                if (verificationTask.isSuccessful) {
                                    // Verification email sent successfully
                                    Toast.makeText(
                                        this,
                                        "Verification email sent. Please verify your email before logging in.",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Add user to the database asynchronously
                                    GlobalScope.launch(Dispatchers.IO) {
                                        UserRepository(AppDatabase.getInstance(this@RegisterActivity).userDao())
                                            .registerUser(name, usn, password)
                                    }

                                    // You may choose to navigate to another screen or display a message here
                                } else {
                                    // Failed to send verification email
                                    Toast.makeText(
                                        this,
                                        "Failed to send verification email",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }
                        }
                    } else {
                        // Registration failed
                        Toast.makeText(
                            this,
                            "Registration failed: ${task.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        buttonNavigateToLogin.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        buttonForgotPassword.setOnClickListener {
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }
}






