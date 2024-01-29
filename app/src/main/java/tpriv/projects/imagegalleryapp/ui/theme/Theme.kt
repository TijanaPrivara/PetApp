package tpriv.projects.imagegalleryapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = lightGreen,
    primaryVariant = lightGreen,
    secondary = lightGreen
)

private val LightColorPalette = lightColors(
    primary = darkGreen,
    primaryVariant = darkGreen,
    secondary = darkGreen
)

@Composable
fun ImageGalleyAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        content = content
    )
}