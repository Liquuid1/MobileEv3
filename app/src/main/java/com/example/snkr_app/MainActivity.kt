package com.example.snkr_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.snkr_app.navigation.Route
import com.example.snkr_app.views.MenuShellView
import com.example.snkr_app.views.RegisterView
import com.example.snkr_app.views.WelcomeView

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(color = MaterialTheme.colorScheme.background) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = Route.Welcome.route
                ) {
                    composable(Route.Welcome.route) {
                        WelcomeView(
                            onNavigateHome = { navController.navigate(Route.MenuShell.route) },
                            onNavigateRegister = { navController.navigate(Route.Register.route) } // 👈 aquí
                        )
                    }
                    composable(Route.Register.route) {
                        RegisterView(
                            onRegisterSuccess = { navController.navigate(Route.MenuShell.route) },
                            onBack = { navController.popBackStack() }
                        )
                    }
                    // MenuShell incluye su propio NavHost interno para Option1/2/3
                    composable(Route.MenuShell.route) {
                        MenuShellView(navController)
                    }
                }
            }
        }
    }
}