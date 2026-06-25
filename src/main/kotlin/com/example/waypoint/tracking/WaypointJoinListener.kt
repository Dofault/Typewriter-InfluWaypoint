package com.example.waypoint.tracking

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class WaypointJoinListener : Listener {
    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        WaypointTracker.load(event.player.uniqueId)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        WaypointTracker.unload(event.player.uniqueId)
    }
}
