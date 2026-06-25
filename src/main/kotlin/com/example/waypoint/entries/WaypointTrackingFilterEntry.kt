package com.example.waypoint.entries

import com.example.waypoint.tracking.WaypointTrackingFilter
import com.typewritermc.core.entries.Ref
import com.typewritermc.core.extension.annotations.Entry
import com.typewritermc.engine.paper.entry.entries.AudienceEntry
import com.typewritermc.engine.paper.entry.entries.AudienceFilter
import com.typewritermc.engine.paper.entry.entries.AudienceFilterEntry

@Entry(
    "waypoint_tracking_filter",
    "Shows its children only to players who currently have an active tracked waypoint",
    "#2196F3",
    "material-symbols:explore",
)
class WaypointTrackingFilterEntry(
    override val id: String = "",
    override val name: String = "",
    override val children: List<Ref<AudienceEntry>> = emptyList(),
) : AudienceFilterEntry {
    override suspend fun display(): AudienceFilter =
        WaypointTrackingFilter(Ref(id, WaypointTrackingFilterEntry::class, this))
}
