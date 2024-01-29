package tpriv.projects.imagegalleryapp.ui.navigation
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screens(
    val route: String,
    val title: String, val icon: ImageVector? = null
) {

    object Home : Screens(route = "home_screen", title = "Home", icon = Icons.Rounded.Home)
    object Details : Screens(route = "detail_screen", title = "Details")
}