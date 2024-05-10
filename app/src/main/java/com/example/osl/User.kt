package com.example.osl

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val usn: String,
    val password: String,



  // val loginTime: String
    //val loginTime: Long = 0,
//    @ColumnInfo(name = "login_time")
  val loginTime: Long = System.currentTimeMillis()



)

