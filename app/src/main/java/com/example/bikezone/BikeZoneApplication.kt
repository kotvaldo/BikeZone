package com.example.bikezone

import android.app.Application
import com.example.bikezone.data.AppContainer
import com.example.bikezone.data.AppDataContainer

class BikeZoneApplication : Application() {

    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
