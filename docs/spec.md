# Hex Flow Connect — Spec (Plan Mode)

ConceptRank: 19 · Hex-grid path-connect puzzle (single-stroke line connecting matching colored nodes across honeycomb board; bridges, blockers, color-mixing rules; deterministic daily seeds; par-move mastery; color-blind safe palettes).

## Q&A Discovery (derived from iOS reference + Android gap notes; assumptions marked [A])

- Q: Core fantasy / 10-second hook? A: Draw one continuous line across a hex grid connecting same-colored nodes — feels like “connect the constellation” with geometric satisfying closure.
- Q: Why come back? A: Daily seeded board (fair global par + leaderboard); master a board with fewer moves; undo token preserves fairness.
- Q: Session target? A: 1–3 min per board; instant restart; low battery / offline.
- Q: Skill vs luck? A: Mostly skill; seed is deterministic (no randomness in outcome); difficulty ramped by board size / blocker density.
- Q: Fail-state frustration control? A: No streak-loss punishment; one undo token; clear “no valid path” end-state keeps failure readable.
- Q: Difficulty ramp? A: 3 levels (small 4×4 → medium 6×6 with bridges → large 8×8 with color-mix + blockers).
- Q: Distinctive mechanic vs Android clones? A: Hex topology (not square grid) + single-stroke constraint; daily seed + par-move mastery.
- Q: Art / animation scope (MVP)? A: Minimal flat geometric hex tiles, 2-color safe palette; particle flash on complete; no complex 3D / audio.
- Q: Audio / feedback? A: Short “connect” chime + subtle haptic; muted by default.
- Q: Monetization-safe design? A: No dark patterns; fully offline; optional future ad slot after completed board, not interrupted.
- Q: Technical constraints? A: Android minSdk 24 / compileSdk 34, Kotlin + JUnit4; single-module; <16ms frame, no external dependencies beyond core-ktx.

## Assumptions [A]
- [A] Daily seed derived from UTC date (same method as Lantern Link / Switchboard Spark).
- [A] Hex board represented as even-q axial coordinate grid; adjacency 6 directions.
- [A] Color-blind-safe palette: 5 clearly distinct colors + shape markers (no red/green only pairs).
- [A] Undo = replay of last stroke; stored in GameState (not persisted across restarts).

## Differentiation (USP + 3)
- USP: “Single-stroke hex-connect with deterministic daily seeds and par-move mastery.”
- Differentiators: (1) Hex grid (not square) forces different spatial reasoning; (2) Daily seed = fair global comparison; (3) Undo token + star-rating by move count = fairness-first mastery.
- Retention hooks: (1) Daily challenge seed loop; (2) Star/par progression per board; (3) Global leaderboard potential via deterministic scores.
- Quality bars: (1) <16ms per frame / smooth swipe; (2) Readable hex nodes / color + shape; (3) Instant restart / fairness undo.

## MVP Scope Guardrail
In: hex grid drawing (touch-to-draw path), color-match validation, 3 board sizes, daily seed, one undo, JUnit4 tests (HexBoard, PathValidator, GameEngine, DailySeedGenerator), build/test pass.
Post-MVP: leaderboard, more board packs, color-mix advanced rules, sound design.