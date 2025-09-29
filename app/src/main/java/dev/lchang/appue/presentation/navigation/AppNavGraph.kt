package dev.lchang.appue.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
// Icons will be imported via BottomNavItem.kt if you move it
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
// ImageVector will be imported via BottomNavItem.kt if you move it
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.lchang.appue.presentation.home.HomeScreen
import dev.lchang.appue.presentation.navigation.BottomNavItem // Ensure this import is present

// The BottomNavItem sealed class definition would be removed from here

@Composable
fun AppNavGraph(navController: NavHostController) =NavHost(
    navController = navController,
    startDestination = BottomNavItem.Home.route
) {
    composable(BottomNavItem.Home.route) {
        HomeScreen()
    }
    composable(BottomNavItem.Search.route) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Search Screen (IZ*ONE Themed)")
        }
    }
    composable(BottomNavItem.Library.route) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Your Library (IZ*ONE Themed)")
        }
    }
}
