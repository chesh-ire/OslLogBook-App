package com.example.osl

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        val editTextNewPassword = findViewById<EditText>(R.id.editTextNewPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)
        val buttonResetPassword = findViewById<Button>(R.id.buttonResetPassword)

        buttonResetPassword.setOnClickListener {
            val newPassword = editTextNewPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            if (newPassword == confirmPassword) {
                finish()
            } else {
                // Passwords don't match, show an error message
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
