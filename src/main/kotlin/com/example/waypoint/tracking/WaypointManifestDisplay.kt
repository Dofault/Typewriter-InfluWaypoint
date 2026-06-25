package com.example.waypoint.tracking

import com.example.waypoint.data.WaypointData
import com.typewritermc.engine.paper.entry.entries.AudienceDisplay
import org.bukkit.entity.Player

/** Tracks [data] for as long as the player stays in this entry's audience scope. */
class WaypointManifestDisplay(private val data: WaypointData) : AudienceDisplay() {
    override fun onPlayerAdd(player: Player) {
        WaypointTracker.track(player.uniqueId, data)
    }

    override fun onPlayerRemove(player: Player) {
        WaypointTracker.stopTracking(player.uniqueId)
    }
}
