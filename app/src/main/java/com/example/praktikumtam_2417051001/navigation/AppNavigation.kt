package com.example.praktikumtam_2417051001.navigation

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktikumtam_2417051001.model.ActivityModel
import com.example.praktikumtam_2417051001.uii.auth.LoginScreen
import com.example.praktikumtam_2417051001.uii.auth.RegisterScreen
import com.example.praktikumtam_2417051001.uii.home.HomeScreen
import com.example.praktikumtam_2417051001.uii.home.DetailScreen
import com.example.praktikumtam_2417051001.uii.profile.ProfileScreen
import com.example.praktikumtam_2417051001.uii.mission.MissionScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    var selectedActivity by remember { mutableStateOf<ActivityModel?>(null) }

    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = {
                    navController.navigate("home") { popUpTo("login") { inclusive = true } }
                }
            )
        }
        composable("register") {
            RegisterScreen(
                onNavigateBack = { navController.popBackStack() },
                onRegisterSuccess = {
                    navController.navigate("login") { popUpTo("register") { inclusive = true } }
                }
            )
        }
        composable("home") {
            HomeScreen(
                onNavigateToDetail = { data ->
                    selectedActivity = data
                    navController.navigate("detail")
                },
                onNavigateToProfile = { navController.navigate("profile") },
                onNavigateToMissions = { navController.navigate("mission") }
            )
        }
        composable("detail") {
            selectedActivity?.let { data ->
                DetailScreen(activityData = data, onNavigateBack = { navController.popBackStack() })
            }
        }
        composable("profile") {
            ProfileScreen(onNavigateBack = { navController.popBackStack() }, onLogout = {
                navController.navigate("login") { popUpTo(0) } // Reset semua stack dan balik ke login
            })
        }
        composable("mission") {
            MissionScreen(onNavigateBack = { navController.popBackStack() })
        }
    }
}
