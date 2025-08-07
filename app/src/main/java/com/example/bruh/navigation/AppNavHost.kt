package com.example.bruh.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bruh.ui.theme.screens.dashboard.dashboardScreen
import com.example.bruh.ui.theme.screens.login.loginScreen
import com.example.bruh.ui.theme.screens.patients.AddPatientScreen
import com.example.bruh.ui.theme.screens.patients.PatientListScreen
import com.example.bruh.ui.theme.screens.patients.UpdatePatientScreen
import com.example.bruh.ui.theme.screens.register.registerScreen

@Composable
fun AppNavHost(navController: NavHostController= rememberNavController(),startDestination:String= ROUTE_DASHBOARD){
    NavHost(navController=navController,startDestination=startDestination){
        composable(ROUTE_REGISTER) { registerScreen(navController) }
        composable(ROUTE_LOGIN) { loginScreen(navController) }
        composable(ROUTE_DASHBOARD) { dashboardScreen(navController) }
        composable(ROUTE_ADDPATIENT) { AddPatientScreen(navController) }
        composable(ROUTE_VIEWPATIENTS) { PatientListScreen(navController) }
        composable(
            route = ROUTE_UPDATE_PATIENT,
            arguments = listOf(navArgument("patientId") { type = NavType.StringType })
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getString("patientId")!!
            UpdatePatientScreen(navController, patientId)
        }

    }
}
