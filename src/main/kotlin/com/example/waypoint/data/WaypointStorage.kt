package com.example.waypoint.data

import com.typewritermc.engine.paper.plugin
import java.io.File
import java.util.UUID

/**
 * Persists each player's single tracked waypoint as one JSON object per file under
 * <dataFolder>/waypoints/<uuid>.json. No external JSON library is used since the schema
 * is a fixed set of primitive fields. Absence of a file (or an empty one) means "not tracking".
 */
object WaypointStorage {

    private val folder: File by lazy {
        File(plugin.dataFolder, "waypoints").apply { mkdirs() }
    }

    fun load(playerId: UUID): WaypointData? {
        val file = File(folder, "$playerId.json")
        if (!file.exists()) return null
        return decode(file.readText())
    }

    fun save(playerId: UUID, data: WaypointData) {
        File(folder, "$playerId.json").writeText(encode(data))
    }

    fun clear(playerId: UUID) {
        File(folder, "$playerId.json").delete()
    }

    private fun encode(w: WaypointData): String =
        """{"label":"${escape(w.label)}","x":${w.x},"y":${w.y},"z":${w.z},"world":"${escape(w.world)}"}"""

    private fun decode(json: String): WaypointData? {
        val body = json.trim().removePrefix("{").removeSuffix("}")
        if (body.isBlank()) return null
        val fieldRegex = Regex("\"(\\w+)\":(\"(?:[^\"\\\\]|\\\\.)*\"|[-0-9.]+)")
        val fields = fieldRegex.findAll(body).associate { match ->
            val key = match.groupValues[1]
            val rawValue = match.groupValues[2]
            val value = if (rawValue.startsWith("\"")) unescape(rawValue.removeSurrounding("\"")) else rawValue
            key to value
        }
        return WaypointData(
            label = fields["label"].orEmpty(),
            x = fields["x"]?.toDoubleOrNull() ?: return null,
            y = fields["y"]?.toDoubleOrNull() ?: return null,
            z = fields["z"]?.toDoubleOrNull() ?: return null,
            world = fields["world"].orEmpty(),
        )
    }

    private fun escape(value: String): String =
        value.replace("\\", "\\\\").replace("\"", "\\\"")

    private fun unescape(value: String): String =
        value.replace("\\\"", "\"").replace("\\\\", "\\")
}
