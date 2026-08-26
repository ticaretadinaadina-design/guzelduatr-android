package com.guzelduatr.app.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.guzelduatr.app.ui.screens.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object AnaSayfa : Screen("anasayfa", "Ana Sayfa", Icons.Default.Home)
    object Dualar : Screen("dualar", "Dualar", Icons.Default.MenuBook)
    object Zikirler : Screen("zikirler", "Zikirler", Icons.Default.Favorite)
    object Namaz : Screen("namaz", "Namaz", Icons.Default.AccessTime)
    object DahaFazla : Screen("daha_fazla", "Daha Fazla", Icons.Default.MoreHoriz)
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.AnaSayfa,
        Screen.Dualar,
        Screen.Zikirler,
        Screen.Namaz,
        Screen.DahaFazla
    )

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { Icon(screen.icon, contentDescription = screen.title) },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
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
            startDestination = Screen.AnaSayfa.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.AnaSayfa.route) { AnaSayfaScreen() }
            composable(Screen.Dualar.route) { DualarScreen() }
            composable(Screen.Zikirler.route) { ZikirlerScreen() }
            composable(Screen.Namaz.route) { NamazVakitleriScreen() }
            composable(Screen.DahaFazla.route) { DahaFazlaScreen() }
        }
    }
}
