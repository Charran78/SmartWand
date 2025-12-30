package com.smartwand.ui.navigation

/**
 * Navigation destinations for SmartWand app.
 * 
 * Using sealed class for type-safe navigation routes.
 */
sealed class Screen(val route: String) {
    
    /** Main dashboard - app entry point */
    data object Dashboard : Screen("dashboard")
    
    /** Emergency contacts and fall history */
    data object Emergency : Screen("emergency")
    
    /** IoT device control */
    data object Devices : Screen("devices")
    
    /** Crutch Genie AI assistant */
    data object Genie : Screen("genie")
    
    /** App settings */
    data object Settings : Screen("settings")
}
