package com.example.snkr_app.views

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.snkr_app.navigation.Route
import kotlinx.coroutines.launch
import androidx.navigation.NavType
import androidx.navigation.navArgument

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuShellView(
    appNavController: NavHostController // 👈 usa el mismo NavController de tu AppNavHost
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val innerNavController = rememberNavController()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = "Menú",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(16.dp)
                )
                NavigationDrawerItem(
                    label = { Text("Home") },
                    selected = currentInnerRoute(innerNavController) == Route.Home.route,
                    onClick = {
                        innerNavController.navigate(Route.Home.route) {
                            popUpTo(Route.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Ver productos") },
                    selected = currentInnerRoute(innerNavController) == Route.Productos.route,
                    onClick = {
                        innerNavController.navigate(Route.Productos.route) {
                            popUpTo(Route.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Newsletter") },
                    selected = currentInnerRoute(innerNavController) == Route.Newsletter.route,
                    onClick = {
                        innerNavController.navigate(Route.Newsletter.route) {
                            popUpTo(Route.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Sube tu zapatilla") },
                    selected = currentInnerRoute(innerNavController) == Route.AddZapatilla.route,
                    onClick = {
                        innerNavController.navigate(Route.AddZapatilla.route) {
                            popUpTo(Route.Home.route) { inclusive = false }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                NavigationDrawerItem(
                    label = { Text("Admin Panel")},
                    selected = currentInnerRoute(innerNavController) == Route.AdminPanel.route,
                    onClick = {
                        innerNavController.navigate(Route.AdminPanel.route) {
                            popUpTo(Route.Home.route) { inclusive = false}
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )

                // 👇 Nuevo botón de cerrar sesión
                NavigationDrawerItem(
                    label = { Text("Cerrar sesión") },
                    selected = false,
                    onClick = {
                        appNavController.navigate(Route.Welcome.route) {
                            // Limpia el backstack para que no se pueda volver atrás
                            popUpTo(appNavController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("SNKR HOOD") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
                            }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Menú")
                        }
                    }
                )
            }
        ) { innerPadding ->
            // NavHost interno para las opciones del menú
            NavHost(
                navController = innerNavController,
                startDestination = Route.Home.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(Route.Home.route) { HomeView(navController = innerNavController) }
                composable(Route.Productos.route) { ProductosView(navController = innerNavController) }
                composable(Route.Newsletter.route) { NewsletterView(navController = innerNavController) }
                composable(Route.AddZapatilla.route) {
                    AddZapatillaView(onZapatillaSaved = {
                        innerNavController.navigate(Route.Productos.route) {
                            popUpTo(Route.Home.route)
                        }
                    })
                }
                composable(
                    route = Route.ProductosDetail.route,
                    arguments = listOf(
                        navArgument("id") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val id = backStackEntry.arguments?.getString("id") ?: "N/A"

                    ProductosDetailView(
                        id = id,
                        onBack = { innerNavController.popBackStack() }
                    )
                }
                composable(Route.AdminPanel.route) { AdminPanelView(navController = innerNavController) }
            }
        }
    }
}

@Composable
private fun currentInnerRoute(navController: NavHostController): String? {
    val entry by navController.currentBackStackEntryAsState()
    return entry?.destination?.route
}
