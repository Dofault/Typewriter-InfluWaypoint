# Waypoint

A Typewriter extension for Paper 1.21.x that exposes a player's currently *tracked-quest* waypoint as live PlaceholderAPI placeholders — no entities, no packets, no per-tick rendering overhead, no persisted state.

Simple way to guide a player to a destination without a compass in the inventory.

<img width="328" height="286" alt="Capture" src="https://github.com/user-attachments/assets/2471c942-9f20-49b1-a231-6525d51d37eb" />

## Entries

- **`waypoint_manifest`** — a static waypoint (label + coordinates). Place it as a direct child of a quest objective (`location_objective`, etc.). It's only ever considered "active" while that objective belongs to the player's currently **tracked** quest and is currently showing — not just whenever its criteria happen to match. This matters: without that check, progressing a completely unrelated, untracked quest would silently steal the displayed waypoint.
- **`waypoint_tracking_filter`** — an audience filter that only lets its children show to players who currently have a waypoint from their tracked quest.

## Placeholders

- `%waypoint_compass%` — resolves to the matching ItemsAdder compass glyph (`%img_compass_00%`–`%img_compass_31%`), computed from the bearing to the active waypoint **relative to where the player is currently looking** — just like a real compass needle.
- `%waypoint_distance%` — distance to the waypoint in blocks (e.g. `23m`).

Both placeholders resolve to an empty string when there's no such waypoint, or when the player isn't in its world. Resolved live on every request — there's no persisted/cached "current waypoint" state, so there's nothing that can get stuck or desync.

## Requirements

- [Typewriter](https://typewritermc.com/) `0.9.0-beta-174`+ with the **Quest** extension (`waypoint_manifest` must be a child of a quest objective)
- [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/)
- An ItemsAdder pack providing 32 compass glyphs named `img_compass_00` through `img_compass_31` (frame 0 = due south, increasing clockwise). A ready-to-use pack — [`itemsadder/influwaypoint`](itemsadder/influwaypoint) — is included in this repo: copy that folder into your server's `plugins/ItemsAdder/contents/`.

## Building

```
./gradlew build
```

The compiled jar will be in `build/libs/`. Drop it into `plugins/Typewriter/extensions/`.
