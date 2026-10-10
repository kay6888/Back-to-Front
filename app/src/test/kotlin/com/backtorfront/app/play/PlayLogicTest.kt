package com.backtorfront.app.play

import org.junit.Assert.*
import org.junit.Test

class PlayLogicTest {
    @Test fun magicPacketLayout() {
        val p = WakeOnLan.buildMagicPacket("AA:BB:CC:DD:EE:FF")!!
        assertEquals(102, p.size)
        for (i in 0 until 6) assertEquals(0xFF.toByte(), p[i])
        for (r in 0 until 16) assertEquals(0xAA.toByte(), p[6 + r * 6])
        assertEquals(0xFF.toByte(), p[101])
    }

    @Test fun invalidMac() {
        assertNull(WakeOnLan.buildMagicPacket("nope"))
        assertNull(WakeOnLan.buildMagicPacket("AA:BB:CC:DD:EE"))
        assertNull(WakeOnLan.buildMagicPacket("GG:BB:CC:DD:EE:FF"))
    }

    @Test fun dashMac() {
        assertNotNull(WakeOnLan.parseMac("aa-bb-cc-dd-ee-ff"))
    }

    @Test fun remap() {
        val r = KeyMapping.remap(KeyMapping.defaults, "jump", "f5")
        assertEquals("F5", r.first { it.id == "jump" }.key)
        assertEquals(KeyMapping.defaults, KeyMapping.remap(KeyMapping.defaults, "jump", "@@"))
    }

    @Test fun joystick() {
        assertEquals(setOf("W", "D"), KeyMapping.joystickKeys(0.8f, -0.8f))
        assertTrue(KeyMapping.joystickKeys(0.1f, 0.1f).isEmpty())
    }
}
