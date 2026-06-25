package com.example.waypoint.entries

import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.entries.AudienceDisplay
import com.typewritermc.engine.paper.entry.entries.AudienceEntry
import com.typewritermc.engine.paper.entry.entries.PassThroughDisplay

/**
 * Purely static (label/x/y/z/world). Whether a player is "tracking" this waypoint is never cached —
 * it's derived live from audience membership each time %waypoint_compass%/%waypoint_distance% is
 * resolved (see WaypointPlaceholderExpansion), so there's no persisted state that can desync from a
 * missed onPlayerAdd/onPlayerRemove call when this entry is shared across sibling filters.
 */
@Entry(
    "waypoint_manifest",
    "Defines a waypoint shown to players in this entry's audience (e.g. nested under a quest-active filter)",
    "#4CAF50",
    "material-symbols:add-location",
)
class WaypointManifestEntry(
    override val id: String = "",
    override val name: String = "",
    val label: String = "",
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,
    val world: String = "",
) : AudienceEntry {
    override suspend fun display(): AudienceDisplay = PassThroughDisplay()
}
