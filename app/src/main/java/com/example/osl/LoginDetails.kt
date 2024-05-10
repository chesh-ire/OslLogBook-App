package com.example.osl

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "login_details")
data class LoginDetails(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val usn: String,
    val name: String,
    val loginTime: Long
)
