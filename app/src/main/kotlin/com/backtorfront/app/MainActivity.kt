package com.backtorfront.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.backtorfront.app.ui.screens.HomeScreen
import com.backtorfront.app.ui.screens.PlayScreen
import com.backtorfront.app.ui.theme.BackToFrontTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BackToFrontTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    var onPlay by remember { mutableStateOf(false) }
                    BackHandler(enabled = onPlay) { onPlay = false }
                    if (onPlay) PlayScreen(onBack = { onPlay = false })
                    else HomeScreen(onOpenPlay = { onPlay = true })
                }
            }
        }
    }
}
