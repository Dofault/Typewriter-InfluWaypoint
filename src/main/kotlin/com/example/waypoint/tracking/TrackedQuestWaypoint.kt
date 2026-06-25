package com.example.waypoint.tracking

import com.example.waypoint.entries.WaypointManifestEntry
import com.typewritermc.quest.entries.questShowingObjectives
import com.typewritermc.quest.trackedQuest
import org.bukkit.entity.Player

/**
 * Finds the waypoint_manifest, if any, that's a child of one of the player's currently-tracked
 * quest's active objectives.
 *
 * This is deliberately scoped to the *tracked* quest (Typewriter's "followed quest" concept), not
 * just "any objective whose criteria currently match" — otherwise progressing an unrelated, untracked
 * quest would switch the displayed waypoint away from the quest the player is actually following.
 */
object TrackedQuestWaypoint {
    fun find(player: Player): WaypointManifestEntry? {
        val trackedQuest = player.trackedQuest() ?: return null
        return player.questShowingObjectives(trackedQuest)
            .flatMap { it.children.asSequence() }
            .mapNotNull { it.get() as? WaypointManifestEntry }
            .firstOrNull()
    }
}
