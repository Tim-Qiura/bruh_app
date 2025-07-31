package com.example.bruh.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bruh.ui.theme.screens.dashboard.dashboardScreen
import com.example.bruh.ui.theme.screens.login.loginScreen
import com.example.bruh.ui.theme.screens.patients.AddPatientScreen
import com.example.bruh.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(),startDestination:String= ROUTE_DASHBOARD){
    NavHost(navController=navController,startDestination=startDestination){
        composable(ROUTE_REGISTER) { registerScreen(navController) }
        composable(ROUTE_LOGIN) { loginScreen(navController) }
        composable(ROUTE_DASHBOARD) { dashboardScreen(navController) }
        composable(ROUTE_ADDPATIENT) { AddPatientScreen(navController) }
    }
}
