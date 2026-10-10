package com.backtorfront.app.play

/** A touch control and the keyboard/mouse input it should produce on the host. */
data class ControlBinding(val id: String, val label: String, val key: String)

object KeyMapping {
    /** Default WoW layout: movement joystick keys + action bar 1-0. */
    val defaults: List<ControlBinding> = listOf(
        ControlBinding("up", "↑", "W"),
        ControlBinding("down", "↓", "S"),
        ControlBinding("left", "←", "A"),
        ControlBinding("right", "→", "D"),
        ControlBinding("jump", "JMP", "SPACE")
    ) + (1..9).map { ControlBinding("bar$it", "$it", "$it") } +
        ControlBinding("bar10", "0", "0")

    private val valid = Regex("^([A-Z0-9]|F([1-9]|1[0-2])|SPACE|TAB|ESC|ENTER)$")

    fun isValidKey(key: String) = valid.matches(key.trim().uppercase())

    /** Returns a new list with binding [id] remapped; unchanged if the key is invalid. */
    fun remap(bindings: List<ControlBinding>, id: String, key: String): List<ControlBinding> {
        val k = key.trim().uppercase()
        if (!isValidKey(k)) return bindings
        return bindings.map { if (it.id == id) it.copy(key = k) else it }
    }

    /**
     * Maps a joystick offset (dx, dy; screen coords, y down) to the movement keys held.
     * Uses a dead zone as a fraction of 1.0 normalised magnitude.
     */
    fun joystickKeys(dx: Float, dy: Float, deadZone: Float = 0.3f): Set<String> {
        val keys = mutableSetOf<String>()
        if (dy < -deadZone) keys += "W"
        if (dy > deadZone) keys += "S"
        if (dx < -deadZone) keys += "A"
        if (dx > deadZone) keys += "D"
        return keys
    }
}
