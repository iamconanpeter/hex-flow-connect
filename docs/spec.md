# Hex Flow Connect — Spec (Plan-Mode) — conceptRank 19

## Q&A Discovery (Assumptions)
- **10-second hook:** Draw a single continuous line across a hex honeycomb to connect matching colored endpoints; bridges let paths cross.
- **Daily/weekly loop:** Daily deterministic seed (SHA-256 of YYYY-MM-DD) → fixed board; replay for star mastery.
- **Session length:** 30s–3m (depends on board size 3×3 / 5×5 / 7×7).
- **Skill vs luck:** Skill (path planning, no revisits) dominates; seed only sets board, not outcome.
- **Fail-state fairness:** One undo token (future), instant restart, no time pressure in MVP.
- **Difficulty ramp:** Board size 3→5→7 via seed; more nodes = longer path.
- **Distinctive mechanic vs clones:** Hex axial topology + single-stroke constraint (no revisits) vs square-grid clones.
- **Art/animation scope:** Abstract hex nodes with color endpoints; minimal assets; <16ms frame target.
- **Audio/feedback:** Soft chime on valid path, soft pulse on invalid step (future).
- **Monetization-safe:** No dark patterns; optional star-based progress tracking only.
- **Constraints:** Java 11, Android SDK available at /home/openclaw/android-sdk.

## USP
Single-stroke hex topology with axial adjacency creates uniquely spatial reasoning vs square-grid path clones.

## 3 Differentiators
1. Hex axial topology (not square grid)
2. Deterministic daily seed for fair leaderboards
3. Single-stroke, no-revisit constraint (tactical planning)

## 3 Retention Hooks
1. Daily deterministic seed board + star mastery (1–3 stars)
2. Replay same seed for improvement (streak-safe)
3. Variable challenge via board size rotation

## 3 Quality Bars
1. Frame budget <16ms (light weight)
2. Readable hex nodes + endpoint colors
3. Instant restart + deterministic fairness

## MVP Scope
- HexCell, DailySeedGenerator, GameEngine, HexBoardTest (4 assertions)
- Build passes (SDK fixed); repo: iamconanpeter/hex-flow-connect
