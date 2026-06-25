package com.example.waypoint.tracking

import com.example.waypoint.data.WaypointData
import com.example.waypoint.data.WaypointStorage
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/** In-memory view of each online player's single tracked waypoint, backed by [WaypointStorage]. */
object WaypointTracker {

    private val active = ConcurrentHashMap<UUID, WaypointData>()

    /**
     * Call on player join to hydrate the in-memory cache from disk. A no-op if something is
     * already tracked in memory: this also runs on every Typewriter reload (not just join), and
     * a stale disk read must never clobber state a manifest entry just set/cleared in memory.
     */
    fun load(playerId: UUID) {
        if (active.containsKey(playerId)) return
        WaypointStorage.load(playerId)?.let { active[playerId] = it }
    }

    fun unload(playerId: UUID) {
        active.remove(playerId)
    }

    fun track(playerId: UUID, data: WaypointData) {
        active[playerId] = data
        WaypointStorage.save(playerId, data)
    }

    fun stopTracking(playerId: UUID) {
        active.remove(playerId)
        WaypointStorage.clear(playerId)
    }

    fun current(playerId: UUID): WaypointData? = active[playerId]

    fun isTracking(playerId: UUID): Boolean = active.containsKey(playerId)
}
