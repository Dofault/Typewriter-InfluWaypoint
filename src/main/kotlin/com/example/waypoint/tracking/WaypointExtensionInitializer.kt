package com.example.waypoint.tracking

import com.typewritermc.core.extension.Initializable
import com.typewritermc.core.extension.annotations.Singleton
import com.typewritermc.engine.paper.plugin
import org.bukkit.Bukkit

@Singleton
object WaypointExtensionInitializer : Initializable {
    private var placeholderExpansion: WaypointPlaceholderExpansion? = null

    // initialize()/shutdown() aren't guaranteed to run on the main thread, but PlaceholderAPI's
    // ExpansionRegisterEvent (fired by register()/unregister()) must be triggered synchronously.
    override suspend fun initialize() {
        WaypointManifestRegistry.refresh()

        Bukkit.getScheduler().runTask(plugin, Runnable {
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                placeholderExpansion = WaypointPlaceholderExpansion().also { it.register() }
            } else {
                plugin.logger.warning("[Waypoint] PlaceholderAPI not found/enabled — %waypoint_*% placeholders won't be available.")
            }
        })
    }

    override suspend fun shutdown() {
        Bukkit.getScheduler().runTask(plugin, Runnable {
            placeholderExpansion?.unregister()
            placeholderExpansion = null
        })
    }
}
