package com.example.waypoint.tracking

import com.typewritermc.core.entries.Ref
import com.typewritermc.engine.paper.entry.entries.AudienceFilter
import com.typewritermc.engine.paper.entry.entries.AudienceFilterEntry
import org.bukkit.entity.Player

/** Lets its children be shown only to players who currently have an active tracked waypoint. */
class WaypointTrackingFilter(ref: Ref<out AudienceFilterEntry>) : AudienceFilter(ref) {
    override fun filter(player: Player): Boolean = WaypointTracker.isTracking(player.uniqueId)
}
