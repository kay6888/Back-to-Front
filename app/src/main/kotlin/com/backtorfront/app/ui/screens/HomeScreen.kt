package com.backtorfront.app.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Terminal
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backtorfront.app.ui.theme.BackgroundDeepPurple
import com.backtorfront.app.ui.theme.BabyBlue
import com.backtorfront.app.ui.theme.BabyBlueDim
import com.backtorfront.app.ui.theme.BabyBlueLight
import com.backtorfront.app.ui.theme.NeonCyan
import com.backtorfront.app.ui.theme.OnPrimary
import com.backtorfront.app.ui.theme.SurfaceDarkPurple
import com.backtorfront.app.ui.theme.SurfaceVariantPurple
import com.backtorfront.app.ui.theme.TextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Terminal,
                            contentDescription = null,
                            tint = BabyBlue,
                            modifier = Modifier
                                .size(24.dp)
                                .padding(end = 4.dp)
                        )
                        Text(
                            text = "BACK-TO-FRONT",
                            color = BabyBlue,
                            letterSpacing = 3.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = SurfaceDarkPurple,
                    titleContentColor = BabyBlue
                )
            )
        },
        containerColor = BackgroundDeepPurple
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // ── Hero header ────────────────────────────────────────────────
            HeroHeader()

            Spacer(Modifier.height(8.dp))

            // ── Primary fancy button ───────────────────────────────────────
            GlowButton(
                text = "SCAN SYSTEM",
                icon = Icons.Filled.Search,
                onClick = {}
            )

            // ── Secondary outlined neon button ────────────────────────────
            NeonOutlinedButton(
                text = "OPEN TERMINAL",
                icon = Icons.Filled.Terminal,
                onClick = {}
            )

            // ── Gradient button ────────────────────────────────────────────
            GradientButton(
                text = "DECRYPT FILE",
                icon = Icons.Filled.Lock,
                onClick = {}
            )

            Spacer(Modifier.height(8.dp))

            // ── Feature cards ──────────────────────────────────────────────
            Text(
                text = "// MODULES",
                color = BabyBlueDim,
                letterSpacing = 2.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 4.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                FeatureCard(
                    icon = Icons.Filled.Code,
                    title = "Decompiler",
                    subtitle = "Reverse Kotlin / Java bytecode",
                    modifier = Modifier.weight(1f)
                )
                FeatureCard(
                    icon = Icons.Filled.Lock,
                    title = "Decryptor",
                    subtitle = "Break common obfuscation layers",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(Modifier.height(4.dp))

            // ── Status line ────────────────────────────────────────────────
            StatusLine()
        }
    }
}

// ── Hero header ───────────────────────────────────────────────────────────────

@Composable
private fun HeroHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(SurfaceVariantPurple, SurfaceDarkPurple)
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.horizontalGradient(listOf(BabyBlueDim, NeonCyan, BabyBlueDim)),
                shape = RoundedCornerShape(16.dp)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "> BACK-TO-FRONT_",
                color = BabyBlue,
                fontSize = 26.sp,
                letterSpacing = 2.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Android Reverse Engineering Suite",
                color = TextSecondary,
                fontSize = 13.sp,
                letterSpacing = 1.sp,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "[ minSdk 26 · Jetpack Compose · Material 3 ]",
                color = BabyBlueDim.copy(alpha = 0.6f),
                fontSize = 11.sp,
                letterSpacing = 0.5.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

// ── Glow (filled) button ──────────────────────────────────────────────────────

@Composable
fun GlowButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "glowButtonScale"
    )
    val glowAlpha by animateFloatAsState(
        targetValue = if (isPressed) 0.6f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "glowButtonAlpha"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
    ) {
        // Glow shadow behind the button
        Box(
            modifier = Modifier
                .matchParentSize()
                .shadow(
                    elevation = (24 * glowAlpha).dp,
                    shape = RoundedCornerShape(50),
                    ambientColor = BabyBlue.copy(alpha = 0.5f),
                    spotColor = BabyBlue.copy(alpha = 0.5f)
                )
        )
        Button(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = BabyBlue,
                contentColor = OnPrimary
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp
            )
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                letterSpacing = 2.sp,
                fontSize = 14.sp
            )
        }
    }
}

// ── Neon outlined button ──────────────────────────────────────────────────────

@Composable
fun NeonOutlinedButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "neonButtonScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
    ) {
        OutlinedButton(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp)
                .border(
                    width = 1.5.dp,
                    brush = Brush.horizontalGradient(listOf(NeonCyan, BabyBlue, NeonCyan)),
                    shape = RoundedCornerShape(50)
                ),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = SurfaceDarkPurple.copy(alpha = 0.8f),
                contentColor = BabyBlue
            ),
            border = null
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                letterSpacing = 2.sp,
                fontSize = 14.sp,
                color = BabyBlue
            )
        }
    }
}

// ── Gradient button ───────────────────────────────────────────────────────────

@Composable
fun GradientButton(
    text: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(dampingRatio = 0.6f, stiffness = 400f),
        label = "gradientButtonScale"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .scale(scale)
            .clip(RoundedCornerShape(50))
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(BabyBlueDim, NeonCyan.copy(alpha = 0.8f), BabyBlueDim)
                )
            )
    ) {
        Button(
            onClick = onClick,
            interactionSource = interactionSource,
            modifier = Modifier
                .fillMaxWidth()
                .height(54.dp),
            shape = RoundedCornerShape(50),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = OnPrimary
            ),
            elevation = null
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.size(8.dp))
            Text(
                text = text,
                letterSpacing = 2.sp,
                fontSize = 14.sp
            )
        }
    }
}

// ── Feature card ──────────────────────────────────────────────────────────────

@Composable
private fun FeatureCard(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = BabyBlueDim.copy(alpha = 0.3f),
                shape = RoundedCornerShape(12.dp)
            ),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = SurfaceDarkPurple)
    ) {
        Column(
            modifier = Modifier.padding(14.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BabyBlue,
                modifier = Modifier.size(28.dp)
            )
            Text(text = title, color = BabyBlue, fontSize = 13.sp, letterSpacing = 1.sp)
            Text(text = subtitle, color = TextSecondary, fontSize = 11.sp, lineHeight = 16.sp)
        }
    }
}

// ── Status line ───────────────────────────────────────────────────────────────

@Composable
private fun StatusLine() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(SurfaceDarkPurple)
            .padding(12.dp)
    ) {
        Text(
            text = "> sys.status: ONLINE  |  build: 1.0.0  |  sdk: 26+",
            color = NeonCyan.copy(alpha = 0.7f),
            fontSize = 11.sp,
            letterSpacing = 0.5.sp
        )
    }
}
