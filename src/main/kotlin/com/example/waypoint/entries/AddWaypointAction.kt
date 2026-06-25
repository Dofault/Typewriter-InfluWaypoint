package com.example.waypoint.entries

import com.example.waypoint.data.WaypointData
import com.example.waypoint.tracking.WaypointTracker
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger

@Entry(
    "add_waypoint",
    "Sets the player's tracked waypoint (replaces any waypoint already being tracked)",
    "#4CAF50",
    "material-symbols:add-location",
)
class AddWaypointAction(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
    val label: String = "",
    val x: Double = 0.0,
    val y: Double = 0.0,
    val z: Double = 0.0,
    val world: String = "",
) : ActionEntry {
    override fun ActionTrigger.execute() {
        WaypointTracker.track(player.uniqueId, WaypointData(label, x, y, z, world))
    }
}
