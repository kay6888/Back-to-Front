package com.backtorfront.app.play

import java.net.DatagramPacket
import java.net.DatagramSocket
import java.net.InetAddress

object WakeOnLan {
    /** Parses "AA:BB:CC:DD:EE:FF" or "AA-BB-..." into 6 bytes, or null if invalid. */
    fun parseMac(mac: String): ByteArray? {
        val parts = mac.trim().split(':', '-')
        if (parts.size != 6) return null
        if (parts.any { it.length != 2 }) return null
        val bytes = parts.map { it.toIntOrNull(16) ?: return null }
        return ByteArray(6) { bytes[it].toByte() }
    }

    /** Magic packet: 6 x 0xFF followed by the MAC repeated 16 times (102 bytes). */
    fun buildMagicPacket(mac: String): ByteArray? {
        val m = parseMac(mac) ?: return null
        val out = ByteArray(6 + 16 * 6)
        for (i in 0 until 6) out[i] = 0xFF.toByte()
        for (r in 0 until 16) m.copyInto(out, 6 + r * 6)
        return out
    }

    /** Blocking; call off the main thread. Returns true if sent. */
    fun send(mac: String, address: String, port: Int = 9): Boolean {
        val packet = buildMagicPacket(mac) ?: return false
        return try {
            DatagramSocket().use { s ->
                s.broadcast = true
                s.send(DatagramPacket(packet, packet.size, InetAddress.getByName(address), port))
            }
            true
        } catch (e: Exception) {
            false
        }
    }
}
