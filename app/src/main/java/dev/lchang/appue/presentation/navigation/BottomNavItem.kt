package dev.lchang.appue.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LibraryMusic
import androidx.compose.material.icons.outlined.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(
    val route: String,
    val title: String,
    val iconFilled: ImageVector,
    val iconOutlined: ImageVector
) {
    object Home : BottomNavItem("home_screen", "Home", Icons.Filled.Home, Icons.Outlined.Home)
    object Search : BottomNavItem("search_screen", "Search", Icons.Filled.Search, Icons.Outlined.Search)
    object Library : BottomNavItem("library_screen", "Your Library", Icons.Filled.LibraryMusic, Icons.Outlined.LibraryMusic)
}

