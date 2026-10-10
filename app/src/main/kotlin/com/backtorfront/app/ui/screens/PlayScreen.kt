package com.backtorfront.app.ui.screens

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Power
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.backtorfront.app.play.ControlBinding
import com.backtorfront.app.play.HostProfile
import com.backtorfront.app.play.HostStore
import com.backtorfront.app.play.KeyMapping
import com.backtorfront.app.play.StreamApp
import com.backtorfront.app.play.StreamLauncher
import com.backtorfront.app.play.WakeOnLan
import com.backtorfront.app.ui.theme.BabyBlue
import com.backtorfront.app.ui.theme.BabyBlueDim
import com.backtorfront.app.ui.theme.BackgroundDeepPurple
import com.backtorfront.app.ui.theme.NeonCyan
import com.backtorfront.app.ui.theme.OnPrimary
import com.backtorfront.app.ui.theme.SurfaceDarkPurple
import com.backtorfront.app.ui.theme.TextSecondary
import kotlin.concurrent.thread

private fun isOnline(ctx: Context): Boolean {
    val cm = ctx.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val caps = cm.getNetworkCapabilities(cm.activeNetwork) ?: return false
    return caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}

@Suppress("DEPRECATION")
private fun buzz(ctx: Context) {
    val v = ctx.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
    v.vibrate(VibrationEffect.createOneShot(15, VibrationEffect.DEFAULT_AMPLITUDE))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlayScreen(onBack: () -> Unit) {
    val ctx = LocalContext.current
    val store = remember { HostStore(ctx) }
    var hosts by remember { mutableStateOf(store.load()) }
    var name by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var port by remember { mutableStateOf("9") }
    var mac by remember { mutableStateOf("") }
    var app by remember { mutableStateOf(StreamApp.MOONLIGHT) }
    var showOverlay by remember { mutableStateOf(false) }
    var bindings by remember { mutableStateOf(KeyMapping.defaults) }

    if (showOverlay) {
        TouchOverlay(bindings, onRemap = { id, k -> bindings = KeyMapping.remap(bindings, id, k) },
            onClose = { showOverlay = false })
        return
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CLASSIC WOW // REMOTE PLAY", color = BabyBlue, fontSize = 15.sp) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, "Back", tint = BabyBlue)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = SurfaceDarkPurple)
            )
        },
        containerColor = BackgroundDeepPurple
    ) { pad ->
        Column(
            Modifier.fillMaxSize().padding(pad).verticalScroll(rememberScrollState()).padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                "Stream your own, legally licensed Classic client from your PC or cloud VM. " +
                    "No game files or private servers are included.",
                color = TextSecondary, fontSize = 12.sp
            )
            Text("// HOSTS", color = BabyBlueDim, letterSpacing = 2.sp)
            if (hosts.isEmpty()) Text("No hosts yet. Add one below.", color = TextSecondary)
            hosts.forEach { h ->
                Column(
                    Modifier.fillMaxWidth()
                        .border(1.dp, BabyBlueDim.copy(alpha = 0.3f), RoundedCornerShape(12.dp))
                        .background(SurfaceDarkPurple, RoundedCornerShape(12.dp))
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("${h.name}  (${h.address})", color = BabyBlue)
                    Text("via ${h.app.label}", color = TextSecondary, fontSize = 11.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedButton(onClick = {
                            if (WakeOnLan.parseMac(h.mac) == null) {
                                Toast.makeText(ctx, "Invalid MAC address", Toast.LENGTH_SHORT).show()
                            } else {
                                thread {
                                    val ok = WakeOnLan.send(h.mac, h.address.ifBlank { "255.255.255.255" }, h.port)
                                    android.os.Handler(ctx.mainLooper).post {
                                        Toast.makeText(ctx, if (ok) "Wake packet sent" else "Wake failed",
                                            Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }
                        }) { Icon(Icons.Filled.Power, null, Modifier.size(16.dp)); Text(" WAKE") }
                        Button(
                            onClick = {
                                if (!isOnline(ctx)) {
                                    Toast.makeText(ctx, "No network connection", Toast.LENGTH_SHORT).show()
                                } else StreamLauncher.launch(ctx, h.app)
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = BabyBlue, contentColor = OnPrimary)
                        ) { Icon(Icons.Filled.PlayArrow, null, Modifier.size(16.dp)); Text(" LAUNCH") }
                        OutlinedButton(onClick = {
                            hosts = hosts - h; store.save(hosts)
                        }) { Text("DEL") }
                    }
                }
            }

            Text("// ADD HOST", color = BabyBlueDim, letterSpacing = 2.sp)
            OutlinedTextField(name, { name = it }, label = { Text("Name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(address, { address = it }, label = { Text("Address / broadcast IP") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            OutlinedTextField(
                port, { port = it.filter(Char::isDigit) }, label = { Text("WoL port") },
                keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            OutlinedTextField(mac, { mac = it }, label = { Text("MAC (AA:BB:CC:DD:EE:FF)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                StreamApp.values().forEach { a ->
                    val sel = a == app
                    Text(
                        a.label, fontSize = 11.sp,
                        color = if (sel) OnPrimary else BabyBlue,
                        modifier = Modifier
                            .background(if (sel) BabyBlue else SurfaceDarkPurple, RoundedCornerShape(50))
                            .clickable { app = a }
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    )
                }
            }
            Button(
                onClick = {
                    val p = port.toIntOrNull()
                    if (name.isBlank() || p == null || p !in 1..65535 ||
                        (mac.isNotBlank() && WakeOnLan.parseMac(mac) == null)
                    ) {
                        Toast.makeText(ctx, "Check name, port and MAC", Toast.LENGTH_SHORT).show()
                    } else {
                        hosts = hosts + HostProfile(name.trim(), address.trim(), p, mac.trim(), app)
                        store.save(hosts)
                        name = ""; address = ""; mac = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) { Text("SAVE HOST") }

            OutlinedButton(onClick = { showOverlay = true }, modifier = Modifier.fillMaxWidth()) {
                Text("TOUCH CONTROLS / KEY MAPPING")
            }
        }
    }
}

/**
 * Touch layout editor/preview: joystick, camera-drag area, tap-to-target area and hotbar.
 * Tapping a hotbar button remaps its key. Forwarding input to the stream is done by the
 * streaming app; this layout defines the mapping to configure there.
 */
@Composable
private fun TouchOverlay(
    bindings: List<ControlBinding>,
    onRemap: (String, String) -> Unit,
    onClose: () -> Unit
) {
    val ctx = LocalContext.current
    var status by remember { mutableStateOf("Drag joystick / camera, tap to target") }
    var editing by remember { mutableStateOf<ControlBinding?>(null) }
    var draft by remember { mutableStateOf("") }
    val joyRadius = 60f

    Box(
        Modifier.fillMaxSize().background(BackgroundDeepPurple)
            .pointerInput(Unit) {
                detectTapGestures(onTap = { buzz(ctx); status = "Target @ ${it.x.toInt()},${it.y.toInt()} (left click)" })
            }
            .pointerInput(Unit) {
                detectDragGestures { _, d -> status = "Camera drag ${d.x.toInt()},${d.y.toInt()} (right-drag)" }
            }
    ) {
        Text(status, color = NeonCyan, fontSize = 11.sp, modifier = Modifier.align(Alignment.TopCenter).padding(12.dp))
        OutlinedButton(onClick = onClose, modifier = Modifier.align(Alignment.TopEnd).padding(8.dp)) { Text("CLOSE") }

        var knob by remember { mutableStateOf(Offset.Zero) }
        Box(
            Modifier.align(Alignment.BottomStart).padding(32.dp).size(140.dp)
                .border(2.dp, BabyBlueDim, CircleShape)
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDragEnd = { knob = Offset.Zero; status = "Move: none" },
                        onDrag = { change, d ->
                            change.consume()
                            val n = knob + d
                            val len = n.getDistance()
                            knob = if (len > joyRadius) n * (joyRadius / len) else n
                            status = "Move keys: " + KeyMapping.joystickKeys(knob.x / joyRadius, knob.y / joyRadius)
                        }
                    )
                },
            contentAlignment = Alignment.Center
        ) {
            Box(
                Modifier.offset { androidx.compose.ui.unit.IntOffset(knob.x.toInt(), knob.y.toInt()) }
                    .size(40.dp)
                    .background(BabyBlue.copy(alpha = 0.7f), CircleShape)
            )
        }

        Row(
            Modifier.align(Alignment.BottomEnd).padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            bindings.filter { it.id.startsWith("bar") || it.id == "jump" }.forEach { b ->
                Box(
                    Modifier.size(30.dp)
                        .border(1.dp, NeonCyan, RoundedCornerShape(6.dp))
                        .clickable { buzz(ctx); editing = b; draft = b.key; status = "${b.label} → ${b.key}" },
                    contentAlignment = Alignment.Center
                ) { Text(b.key, color = BabyBlue, fontSize = 9.sp) }
            }
        }

        editing?.let { b ->
            Column(
                Modifier.align(Alignment.Center).background(SurfaceDarkPurple, RoundedCornerShape(12.dp)).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Remap ${b.label} (A-Z, 0-9, F1-F12, SPACE, TAB, ESC, ENTER)", color = BabyBlue, fontSize = 11.sp)
                OutlinedTextField(draft, { draft = it }, singleLine = true)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        if (KeyMapping.isValidKey(draft)) { onRemap(b.id, draft); editing = null }
                        else Toast.makeText(ctx, "Invalid key", Toast.LENGTH_SHORT).show()
                    }) { Text("OK") }
                    OutlinedButton(onClick = { editing = null }) { Text("CANCEL") }
                }
            }
        }
    }
}
