package com.smartwand.ui.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Devices
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.smartwand.R
import com.smartwand.ui.screens.dashboard.DashboardScreen
import com.smartwand.ui.screens.devices.DevicesScreen
import com.smartwand.ui.screens.emergency.EmergencyScreen
import com.smartwand.ui.screens.genie.GenieScreen
import com.smartwand.ui.screens.settings.SettingsScreen

/**
 * Bottom navigation items configuration.
 */
data class BottomNavItem(
    val screen: Screen,
    val labelResId: Int,
    val icon: ImageVector,
    val contentDescriptionResId: Int
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard, R.string.nav_dashboard, Icons.Filled.Home, R.string.nav_dashboard_desc),
    BottomNavItem(Screen.Emergency, R.string.nav_emergency, Icons.Filled.Warning, R.string.nav_emergency_desc),
    BottomNavItem(Screen.Devices, R.string.nav_devices, Icons.Filled.Devices, R.string.nav_devices_desc),
    BottomNavItem(Screen.Genie, R.string.nav_genie, Icons.Filled.AutoAwesome, R.string.nav_genie_desc),
    BottomNavItem(Screen.Settings, R.string.nav_settings, Icons.Filled.Settings, R.string.nav_settings_desc)
)

/**
 * Main navigation host with bottom navigation.
 * 
 * Implements single activity architecture with Compose Navigation.
 */
@Composable
fun SmartWandNavHost() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    Scaffold(
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { 
                            Icon(
                                imageVector = item.icon, 
                                contentDescription = stringResource(item.contentDescriptionResId)
                            ) 
                        },
                        label = { Text(stringResource(item.labelResId)) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                        onClick = {
                            navController.navigate(item.screen.route) {
                                // Pop up to the start destination to avoid building up a large stack
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen()
            }
            composable(Screen.Emergency.route) {
                EmergencyScreen()
            }
            composable(Screen.Devices.route) {
                DevicesScreen()
            }
            composable(Screen.Genie.route) {
                GenieScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen()
            }
        }
    }
}
