package com.example.waypoint.tracking

import com.typewritermc.core.entries.Ref
import com.typewritermc.engine.paper.entry.entries.AudienceFilter
import com.typewritermc.engine.paper.entry.entries.AudienceFilterEntry
import com.typewritermc.engine.paper.entry.entries.TickableDisplay
import org.bukkit.entity.Player

private const val TICK_INTERVAL = 5

/**
 * Lets its children be shown only to players who are currently in the audience of any waypoint_manifest.
 *
 * `filter()` is only re-evaluated when something calls `refresh()` on this display — unlike fact-based
 * filters, there's no single event to subscribe to here (audience membership can change for many
 * different reasons upstream), so this re-checks itself periodically instead of relying on a push.
 */
class WaypointTrackingFilter(ref: Ref<out AudienceFilterEntry>) : AudienceFilter(ref), TickableDisplay {
    private var tickCounter = 0

    override fun filter(player: Player): Boolean = WaypointManifestRegistry.hasAnyActiveFor(player)

    override fun tick() {
        tickCounter++
        if (tickCounter < TICK_INTERVAL) return
        tickCounter = 0

        for (player in consideredPlayers) {
            player.refresh()
        }
    }
}
