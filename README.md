
# Waypoint

A Typewriter extension for Paper 1.21.x that lets your book set (or clear) **one tracked waypoint per player**, and exposes its direction/distance as live PlaceholderAPI placeholders — no entities, no packets, no per-tick rendering overhead.

Simple way to guide a player to a destination without a compass in the inventory.

<img width="328" height="286" alt="Capture" src="https://github.com/user-attachments/assets/2471c942-9f20-49b1-a231-6525d51d37eb" />

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

## Known issues

### Don't reference the same `waypoint_manifest` from multiple sibling filters

If the same `waypoint_manifest` entry is used as a child of **two different** `AudienceFilterEntry` (e.g. two quest objectives gated on the same fact, like `fact == 30` and `fact == 40`), the waypoint can stop tracking after a few fact transitions — for example: activate objective A, switch to an unrelated objective C, switch back to A, and the waypoint silently fails to reappear, even though switching A → B → A (B also sharing the child) works fine.

This isn't a bug in this extension. It's a race condition in Typewriter's own `AudienceManager`:

- Sibling filters that aren't nested under anything else become **roots**, so they all subscribe to fact changes independently and refresh concurrently on an unticked async dispatcher (`Dispatchers.UntickedAsync`).
- When a filter loses a player, `AudienceManager.removePlayerFromChildren()` only removes a shared child if **no other parent still claims it** for that player (`getParents(entryRef).none { this[it]?.contains(player) }`) — this cross-parent check has no ordering guarantee against a sibling filter's own concurrent refresh, so it can read stale state.

**Workaround**: create a separate `waypoint_manifest` per placement instead of sharing one across multiple objectives/filters. If you want to avoid repeating coordinates, keep them in a shared snippet/notes and just re-enter them per placement — duplicating the manifest entry itself is what avoids the race, since each one then has a single, unambiguous parent.

If you can reproduce this reliably, it's worth reporting on the [Typewriter Discord](https://typewritermc.com/) with the exact entry IDs and fact transition sequence.

## Building

```
./gradlew build
```

The compiled jar will be in `build/libs/`. Drop it into `plugins/Typewriter/extensions/`.
