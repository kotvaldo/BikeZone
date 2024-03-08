package com.example.bikezone.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val DarkColorScheme = darkColorScheme(
    primary = Color.White,  // Nastavte primárnu farbu na čiernu
    secondary = DarkPrimary,  // Nastavte sekundárnu farbu na tmavo šedú
    tertiary = DarkPrimary,  // Nastavte terciárnu farbu na šedú
    background = DarkPrimary, // Nastavte farbu pozadia na čiernu
    surface = DarkPrimary,  // Nastavte farbu povrchu na čiernu
    onPrimary = Color.Black,  // Nastavte farbu textu na primárnej farbe na bielu
    onSecondary = Color.White,  // Nastavte farbu textu na sekundárnej farbe na bielu
    onTertiary = Color.White,  // Nastavte farbu textu na terciárnej farbe na bielu
    onBackground = Color.White,  // Nastavte farbu textu na pozadí na bielu
    onSurface = Color.White,  // Nastavte farbu textu na povrchu na bielu
    // Môžete nastaviť aj ďalšie farby podľa potreby
)

private val LightColorScheme = lightColorScheme(
    primary = DarkPrimary,
    secondary = LightPrimary,
    tertiary = LightPrimary,
    background = LightPrimary,
    surface = LightPrimary,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,

    )

@Composable
fun BikeZoneTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {

    val colorScheme = when {
      dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
        val context = LocalContext.current
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
      }
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
      SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.surface.toArgb()
          window.navigationBarColor = colorScheme.surface.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
      }
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}