package com.example.waypoint.tracking

import com.example.waypoint.entries.WaypointManifestEntry
import com.typewritermc.core.entries.Query
import com.typewritermc.core.entries.ref
import com.typewritermc.engine.paper.entry.inAudience
import org.bukkit.entity.Player

/**
 * Caches the (small, rarely-changing) list of waypoint_manifest entries so the hot path
 * (resolving %waypoint_*% placeholders, potentially many times per second) doesn't have to scan
 * every entry in the whole Typewriter book on every call. Refreshed on extension (re)load.
 *
 * Per-player audience membership is still checked live via [Player.inAudience] — only the set of
 * manifests is cached, never which player is in which, so this stays immune to stale state.
 */
object WaypointManifestRegistry {
    @Volatile
    private var manifests: List<WaypointManifestEntry> = emptyList()

    fun refresh() {
        manifests = Query.find<WaypointManifestEntry>().toList()
    }

    fun activeFor(player: Player): WaypointManifestEntry? =
        manifests.firstOrNull { player.inAudience(it.ref()) }

    fun hasAnyActiveFor(player: Player): Boolean =
        manifests.any { player.inAudience(it.ref()) }
}
