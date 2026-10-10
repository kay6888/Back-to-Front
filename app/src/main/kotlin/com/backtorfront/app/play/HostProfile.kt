package com.backtorfront.app.play

import android.content.Context
import org.json.JSONArray
import org.json.JSONObject

enum class StreamApp(val label: String, val packageName: String) {
    MOONLIGHT("Moonlight", "com.limelight"),
    STEAM_LINK("Steam Link", "com.valvesoftware.steamlink"),
    PARSEC("Parsec", "com.parsecgaming.parsec"),
    GEFORCE_NOW("GeForce NOW", "com.nvidia.geforcenow")
}

data class HostProfile(
    val name: String,
    val address: String,
    val port: Int = 9,
    val mac: String = "",
    val app: StreamApp = StreamApp.MOONLIGHT
) {
    fun toJson(): JSONObject = JSONObject()
        .put("name", name).put("address", address).put("port", port)
        .put("mac", mac).put("app", app.name)

    companion object {
        fun fromJson(o: JSONObject) = HostProfile(
            name = o.optString("name"),
            address = o.optString("address"),
            port = o.optInt("port", 9),
            mac = o.optString("mac"),
            app = runCatching { StreamApp.valueOf(o.optString("app")) }
                .getOrDefault(StreamApp.MOONLIGHT)
        )
    }
}

/** Persists host profiles in SharedPreferences as a JSON array. */
class HostStore(context: Context) {
    private val prefs = context.getSharedPreferences("hosts", Context.MODE_PRIVATE)

    fun load(): List<HostProfile> {
        val raw = prefs.getString("list", null) ?: return emptyList()
        return runCatching {
            val arr = JSONArray(raw)
            (0 until arr.length()).map { HostProfile.fromJson(arr.getJSONObject(it)) }
        }.getOrDefault(emptyList())
    }

    fun save(hosts: List<HostProfile>) {
        val arr = JSONArray()
        hosts.forEach { arr.put(it.toJson()) }
        prefs.edit().putString("list", arr.toString()).apply()
    }
}
