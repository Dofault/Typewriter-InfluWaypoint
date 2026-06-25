# Waypoint

A Typewriter extension for Paper 1.21.x that exposes a player's currently active waypoint as live PlaceholderAPI placeholders — no entities, no packets, no per-tick rendering overhead, no persisted state.

Simple way to guide a player to a destination without a compass in the inventory.

<img width="328" height="286" alt="Capture" src="https://github.com/user-attachments/assets/2471c942-9f20-49b1-a231-6525d51d37eb" />

## Entries

- **`waypoint_manifest`** — a static waypoint (label + coordinates). Nest it under a quest/fact filter (or anywhere else in your audience tree) and it's "active" for exactly as long as the player is in that audience — there's nothing to clean up, because nothing is ever cached.
- **`waypoint_tracking_filter`** — an audience filter that only lets its children show to players who are currently in the audience of any `waypoint_manifest`.

## Placeholders

- `%waypoint_compass%` — resolves to the matching ItemsAdder compass glyph (`%img_compass_00%`–`%img_compass_31%`), computed from the bearing to the active waypoint **relative to where the player is currently looking** — just like a real compass needle.
- `%waypoint_distance%` — distance to the waypoint in blocks (e.g. `23m`).

Both placeholders resolve to an empty string when no `waypoint_manifest` is active for the player, or when the player isn't in the waypoint's world. Resolved live on every request: which waypoint is "active" is computed by checking the player's current audience membership, not from any cache, so there's no stale/stuck state to worry about. The (small) list of `waypoint_manifest` entries is cached on load to keep that lookup cheap.

You can safely reference the **same** `waypoint_manifest` from multiple sibling filters (e.g. two quest objectives that should point at the same place) — there's no per-player state attached to it, so there's nothing to desync.

## Requirements

- [Typewriter](https://typewritermc.com/) `0.9.0-beta-174`+
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
- An ItemsAdder pack providing 32 compass glyphs named `img_compass_00` through `img_compass_31` (frame 0 = due south, increasing clockwise). A ready-to-use pack is attached to each [release](../../releases).

## Building

```
./gradlew build
```

The compiled jar will be in `build/libs/`. Drop it into `plugins/Typewriter/extensions/`.
