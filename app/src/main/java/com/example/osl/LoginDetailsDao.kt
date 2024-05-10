package com.example.osl

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface LoginDetailsDao {
    @Insert
    suspend fun insertLoginDetails(loginDetails: LoginDetails)
}
