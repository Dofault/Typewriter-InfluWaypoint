package com.example.waypoint.tracking

import com.typewritermc.core.entries.Ref
import com.typewritermc.engine.paper.entry.entries.AudienceFilter
import com.typewritermc.engine.paper.entry.entries.AudienceFilterEntry
import org.bukkit.entity.Player

/** Lets its children be shown only to players who are currently in the audience of any waypoint_manifest. */
class WaypointTrackingFilter(ref: Ref<out AudienceFilterEntry>) : AudienceFilter(ref) {
    override fun filter(player: Player): Boolean = WaypointManifestRegistry.hasAnyActiveFor(player)
}
