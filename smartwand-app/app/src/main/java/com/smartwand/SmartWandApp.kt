package com.smartwand

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * SmartWand Application class.
 * 
 * This is the entry point for the Hilt dependency injection framework.
 * All app-wide initialization should happen here.
 */
@HiltAndroidApp
class SmartWandApp : Application() {
    
    override fun onCreate() {
        super.onCreate()
        // Future: Initialize crash reporting, analytics, etc.
    }
}
