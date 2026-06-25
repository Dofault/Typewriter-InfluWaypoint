package com.example.waypoint.tracking

import com.example.waypoint.data.WaypointData
import me.clip.placeholderapi.PlaceholderAPI
import me.clip.placeholderapi.expansion.PlaceholderExpansion
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import kotlin.math.atan2
import kotlin.math.roundToInt
import kotlin.math.roundToLong

private const val FRAME_COUNT = 32
private const val DEGREES_PER_FRAME = 360.0 / FRAME_COUNT

/**
 * Exposes `%waypoint_compass%` (the resolved ItemsAdder compass glyph, e.g. for frame 7) and
 * `%waypoint_distance%` (e.g. "23m") for whichever waypoint_manifest the player is currently in the
 * audience of. Both resolve to an empty string when no manifest is active, or when the player isn't
 * in the waypoint's world.
 *
 * Looked up live on every request (not cached) — there's no persisted "tracked waypoint" state to
 * desync, so a missed/out-of-order audience event can never leave a stale waypoint stuck on.
 *
 * %waypoint_compass% resolves the underlying %img_compass_XX% placeholder itself (rather than
 * returning it as-is) because PlaceholderAPI only does a single substitution pass: a nested
 * placeholder returned by an expansion is never re-resolved by whatever consumed the original text
 * (menus, scoreboards, etc.) unless that consumer explicitly loops — most don't.
 */
class WaypointPlaceholderExpansion : PlaceholderExpansion() {

    override fun getIdentifier() = "waypoint"
    override fun getAuthor() = "Influencraft"
    override fun getVersion() = "1.0.0"
    override fun persist() = true

    override fun onPlaceholderRequest(player: Player?, params: String): String {
        if (player == null) return ""
        val waypoint = activeWaypoint(player) ?: return ""

        val world = Bukkit.getWorld(waypoint.world) ?: return ""
        if (player.world != world) return ""

        return when (params.lowercase()) {
            "compass" -> {
                val frame = bearingFrame(waypoint.x - player.location.x, waypoint.z - player.location.z, player.location.yaw)
                PlaceholderAPI.setPlaceholders(player, "%%img_compass_%02d%%".format(frame))
            }
            "distance" -> "${player.location.distance(Location(world, waypoint.x, waypoint.y, waypoint.z)).roundToLong()}m"
            else -> ""
        }
    }

    private fun activeWaypoint(player: Player): WaypointData? {
        val manifest = WaypointManifestRegistry.activeFor(player) ?: return null
        return WaypointData(manifest.label, manifest.x, manifest.y, manifest.z, manifest.world)
    }

    private fun bearingFrame(dx: Double, dz: Double, playerYaw: Float): Int {
        // Bearing to the waypoint in Minecraft's yaw convention: 0 = south, increasing clockwise.
        val bearingDeg = Math.toDegrees(atan2(-dx, dz)).let { if (it < 0) it + 360 else it }
        // Subtract where the player is currently looking, like a real compass needle: facing the
        // waypoint head-on always lands on frame 0, turning away rotates the needle accordingly.
        val playerYawDeg = playerYaw.toDouble().let { if (it < 0) it + 360 else it }
        val relativeDeg = ((bearingDeg - playerYawDeg + 180) % 360).let { if (it < 0) it + 360 else it }
        return (relativeDeg / DEGREES_PER_FRAME).roundToInt() % FRAME_COUNT
    }
}
