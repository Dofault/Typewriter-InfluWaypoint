package com.example.waypoint.entries

import com.example.waypoint.data.WaypointData
import com.example.waypoint.tracking.WaypointManifestDisplay
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.entries.AudienceDisplay
import com.typewritermc.engine.paper.entry.entries.AudienceEntry

@Entry(
    "waypoint_manifest",
    "Tracks a waypoint for as long as the player is in this entry's audience (e.g. nested under a quest-active filter)",
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
    override suspend fun display(): AudienceDisplay = WaypointManifestDisplay(WaypointData(label, x, y, z, world))
}
