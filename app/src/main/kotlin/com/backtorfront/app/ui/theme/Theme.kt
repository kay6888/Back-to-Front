package com.backtorfront.app.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary            = BabyBlue,
    onPrimary          = OnPrimary,
    primaryContainer   = SurfaceVariantPurple,
    onPrimaryContainer = BabyBlueLight,

    secondary          = BabyBlueDim,
    onSecondary        = OnPrimary,
    secondaryContainer = SurfaceDarkPurple,
    onSecondaryContainer = BabyBlue,

    tertiary           = NeonCyan,
    onTertiary         = OnPrimary,
    tertiaryContainer  = SurfaceVariantPurple,
    onTertiaryContainer = NeonCyan,

    background         = BackgroundDeepPurple,
    onBackground       = OnBackground,

    surface            = SurfaceDarkPurple,
    onSurface          = OnSurface,
    surfaceVariant     = SurfaceVariantPurple,
    onSurfaceVariant   = TextSecondary,

    error              = ErrorRed,
    onError            = OnError,
    errorContainer     = Color(0xFF3B0000),
    onErrorContainer   = ErrorRed,

    outline            = BabyBlueDim,
    outlineVariant     = TextMuted,
    scrim              = BackgroundDeepPurple,
)

@Composable
fun BackToFrontTheme(content: @Composable () -> Unit) {
    val colorScheme = DarkColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = BackToFrontTypography,
        content     = content
    )
}
