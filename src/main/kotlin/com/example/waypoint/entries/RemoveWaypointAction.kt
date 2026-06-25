package com.example.waypoint.entries

import com.example.waypoint.tracking.WaypointTracker
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.Criteria
import com.typewritermc.engine.paper.entry.Modifier
import com.typewritermc.engine.paper.entry.TriggerableEntry
import com.typewritermc.engine.paper.entry.entries.ActionEntry
import com.typewritermc.engine.paper.entry.entries.ActionTrigger

@Entry(
    "remove_waypoint",
    "Stops tracking the player's current waypoint, if any",
    "#E53935",
    "material-symbols:wrong-location",
)
class RemoveWaypointAction(
    override val id: String = "",
    override val name: String = "",
    override val criteria: List<Criteria> = emptyList(),
    override val modifiers: List<Modifier> = emptyList(),
    override val triggers: List<Ref<TriggerableEntry>> = emptyList(),
) : ActionEntry {
    override fun ActionTrigger.execute() {
        WaypointTracker.stopTracking(player.uniqueId)
    }
}
