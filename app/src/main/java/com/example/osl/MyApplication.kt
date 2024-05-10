package com.example.osl

import android.app.Application
import androidx.room.Room

class MyApplication : Application() {
    companion object {
        lateinit var instance: MyApplication
            private set

        lateinit var db: AppDatabase
            private set
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        db = Room.databaseBuilder(applicationContext, AppDatabase::class.java, "app-database").build()
    }
}
