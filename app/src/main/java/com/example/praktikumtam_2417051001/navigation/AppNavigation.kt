package com.example.praktikumtam_2417051001.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.praktikumtam_2417051001.MainViewModel
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
    val mainViewModel: MainViewModel = viewModel()
    var selectedActivity by remember { mutableStateOf<ActivityModel?>(null) }

    NavHost(
        navController = navController,
        startDestination = "login",
        enterTransition = { slideInHorizontally(animationSpec = tween(350)) { it } },
        exitTransition = { slideOutHorizontally(animationSpec = tween(350)) { -it } },
        popEnterTransition = { slideInHorizontally(animationSpec = tween(350)) { -it } },
        popExitTransition = { slideOutHorizontally(animationSpec = tween(350)) { it } }
    ) {
        composable("login") {
            LoginScreen(
                onNavigateToRegister = { navController.navigate("register") },
                onLoginSuccess = { email ->
                    mainViewModel.userEmail.value = email
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
                DetailScreen(activityData = data, mainViewModel = mainViewModel, onNavigateBack = { navController.popBackStack() })
            }
        }
        composable("profile") {
            ProfileScreen(
                email = mainViewModel.userEmail.value,
                onNavigateBack = { navController.popBackStack() },
                onLogout = {
                    mainViewModel.resetSession()
                    navController.navigate("login") { popUpTo(0) }
                }
            )
        }
        composable("mission") {
            MissionScreen(mainViewModel = mainViewModel, onNavigateBack = { navController.popBackStack() })
        }
    }
}