# Waypoint

A Typewriter extension for Paper 1.21.x that lets your book set (or clear) **one tracked waypoint per player**, and exposes its direction/distance as live PlaceholderAPI placeholders — no entities, no packets, no per-tick rendering overhead.

Simple way to guide a player to a destination without a compass in the inventory.

## Entries

- **`add_waypoint`** / **`remove_waypoint`** — trigger from anywhere in a sequence for explicit, imperative control over the tracked waypoint.
- **`waypoint_manifest`** — an audience entry. Nest it under a quest/fact filter and the waypoint tracks automatically while the player is in that audience, and clears the moment they leave. No manual cleanup needed.
- **`waypoint_tracking_filter`** — an audience filter that only lets its children show to players who currently have an active tracked waypoint.

## Placeholders

- `%waypoint_compass%` — resolves to the matching ItemsAdder compass glyph (`%img_compass_00%`–`%img_compass_31%`), computed from the bearing to the waypoint **relative to where the player is currently looking** — just like a real compass needle.
- `%waypoint_distance%` — distance to the waypoint in blocks (e.g. `23m`).

Both placeholders resolve to an empty string when nothing is tracked, or when the player isn't in the waypoint's world.

## Requirements

- [Typewriter](https://typewritermc.com/) `0.9.0-beta-174`+
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
- An ItemsAdder pack providing 32 compass glyphs named `img_compass_00` through `img_compass_31` (frame 0 = due south, increasing clockwise). A ready-to-use pack is attached to each [release](../../releases).

## Building

```
./gradlew build
```

The compiled jar will be in `build/libs/`. Drop it into `plugins/Typewriter/extensions/`.
