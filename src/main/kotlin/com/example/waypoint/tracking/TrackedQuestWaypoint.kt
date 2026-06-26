package com.example.waypoint.tracking

import com.example.waypoint.entries.WaypointManifestEntry
import com.typewritermc.core.entries.Query
import com.typewritermc.core.entries.ref
import com.typewritermc.engine.paper.entry.AudienceManager
import com.typewritermc.engine.paper.entry.inAudience
import com.typewritermc.quest.entries.questShowingObjectives
import com.typewritermc.quest.trackedQuest
import org.bukkit.entity.Player
import org.koin.java.KoinJavaComponent.get

/**
 * Finds the waypoint to show the player:
 *
 * 1. The waypoint_manifest that's a child of one of the player's currently-tracked quest's active
 *    objectives, if any. Deliberately scoped to the *tracked* quest (Typewriter's "followed quest"
 *    concept), not just "any objective whose criteria currently match" — otherwise progressing an
 *    unrelated, untracked quest would switch the displayed waypoint away from the quest the player
 *    is actually following.
 * 2. Otherwise, a standalone waypoint_manifest — one that isn't nested under anything (no parent at
 *    all) — so a generic/default waypoint can still be configured for players with nothing tracked.
 */
object TrackedQuestWaypoint {
    fun find(player: Player): WaypointManifestEntry? =
        fromTrackedQuest(player) ?: standaloneWaypoint(player)

    private fun fromTrackedQuest(player: Player): WaypointManifestEntry? {
        val trackedQuest = player.trackedQuest() ?: return null
        return player.questShowingObjectives(trackedQuest)
            .flatMap { it.children.asSequence() }
            .mapNotNull { it.get() as? WaypointManifestEntry }
            .firstOrNull()
    }

    private fun standaloneWaypoint(player: Player): WaypointManifestEntry? {
        val manager = get<AudienceManager>(AudienceManager::class.java)
        return Query.find<WaypointManifestEntry>()
            .firstOrNull { manager.getParents(it.ref()).isEmpty() && player.inAudience(it.ref()) }
    }
}
