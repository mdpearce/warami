package com.neaniesoft.warami

import android.app.Application

class WaramiApplication : Application() {
    companion object {
        @Volatile
        private var instance: WaramiApplication? = null
        fun getInstance(): WaramiApplication =
            instance ?: throw IllegalStateException("Application is not initialized.")
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}
