package dev.catsuperberg.e_commerce_exercise.client.presentation.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat


private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)


private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

data class ExtendedColors(
    val material: ColorScheme,
    val rawYellow: Color,
    val rawYellowHarmonized: Color,
    val rawBlue: Color,
    val rawBlueHarmonized: Color,
    val rawGreen: Color,
    val rawGreenHarmonized: Color,
    val rawRed: Color,
    val rawRedHarmonized: Color,
    val yellow: Color,
    val onYellow: Color,
    val yellowContainer: Color,
    val onYellowContainer: Color,
    val yellowHarmonized: Color,
    val onYellowHarmonized: Color,
    val yellowHarmonizedContainer: Color,
    val onYellowHarmonizedContainer: Color,
    val blue: Color,
    val onBlue: Color,
    val blueContainer: Color,
    val onBlueContainer: Color,
    val blueHarmonized: Color,
    val onBlueHarmonized: Color,
    val blueHarmonizedContainer: Color,
    val onBlueHarmonizedContainer: Color,
    val green: Color,
    val onGreen: Color,
    val greenContainer: Color,
    val onGreenContainer: Color,
    val greenHarmonized: Color,
    val onGreenHarmonized: Color,
    val greenHarmonizedContainer: Color,
    val onGreenHarmonizedContainer: Color,
    val red: Color,
    val onRed: Color,
    val redContainer: Color,
    val onRedContainer: Color,
    val redHarmonized: Color,
    val onRedHarmonized: Color,
    val redHarmonizedContainer: Color,
    val onRedHarmonizedContainer: Color,
)

private val ExtendedLightColorScheme = ExtendedColors(
    material = LightColorScheme,
    rawYellow = Yellow,
    rawYellowHarmonized = Yellowharmonized,
    rawBlue = Blue,
    rawBlueHarmonized = Blueharmonized,
    rawGreen = Green,
    rawGreenHarmonized = Greenharmonized,
    rawRed = Red,
    rawRedHarmonized = Redharmonized,
    yellow = light_Yellow,
    onYellow = light_onYellow,
    yellowContainer = light_YellowContainer,
    onYellowContainer = light_onYellowContainer,
    yellowHarmonized = light_Yellowharmonized,
    onYellowHarmonized = light_onYellowharmonized,
    yellowHarmonizedContainer = light_YellowharmonizedContainer,
    onYellowHarmonizedContainer = light_onYellowharmonizedContainer,
    blue = light_Blue,
    onBlue = light_onBlue,
    blueContainer = light_BlueContainer,
    onBlueContainer = light_onBlueContainer,
    blueHarmonized = light_Blueharmonized,
    onBlueHarmonized = light_onBlueharmonized,
    blueHarmonizedContainer = light_BlueharmonizedContainer,
    onBlueHarmonizedContainer = light_onBlueharmonizedContainer,
    green = light_Green,
    onGreen = light_onGreen,
    greenContainer = light_GreenContainer,
    onGreenContainer = light_onGreenContainer,
    greenHarmonized = light_Greenharmonized,
    onGreenHarmonized = light_onGreenharmonized,
    greenHarmonizedContainer = light_GreenharmonizedContainer,
    onGreenHarmonizedContainer = light_onGreenharmonizedContainer,
    red = light_Red,
    onRed = light_onRed,
    redContainer = light_RedContainer,
    onRedContainer = light_onRedContainer,
    redHarmonized = light_Redharmonized,
    onRedHarmonized = light_onRedharmonized,
    redHarmonizedContainer = light_RedharmonizedContainer,
    onRedHarmonizedContainer = light_onRedharmonizedContainer,
)

private val LocalColors = staticCompositionLocalOf { ExtendedLightColorScheme }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current

@Composable
fun HalloweenTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = ExtendedLightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = colorScheme.material.outline.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    CompositionLocalProvider(LocalColors provides colorScheme) {
        MaterialTheme(
            colorScheme = colorScheme.material,
            content = content
        )
    }
}
