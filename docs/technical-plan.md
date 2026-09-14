# Hex Flow Connect — Technical Plan

Project: hex-flow-connect · Package: com.gamefactory.hexflowconnect · Build: Gradle Kotlin DSL · compileSdk 34 · Java 11 (updated to 17 if AGP requires).

## Architecture
- Single app module (`app/`).
- Source: `src/main/java/com/gamefactory/hexflowconnect/` (HexBoard, HexCell, DailySeedGenerator, GameEngine, PathValidator, HexBoardView).
- Tests: `src/test/java/com/gamefactory/hexflowconnect/` (HexBoardTest, GameEngineTest, DailySeedGeneratorTest, PathValidatorTest).

## Core Classes
- HexCell (data class, axial q/r, neighbors(), adjacency check).
- HexBoard (even-q grid, nodes/blocks, adjacency, cellAt, addNode/addBlocker, isAdjacent).
- PathValidator (single-stroke validation: adjacency, no revisit, start→goal, bridge rules, undo, victory check, par BFS).
- GameEngine (state machine: start/drawing/completed/game-over; validatePath; scorePath by par; undo.
- DailySeedGenerator (deterministic seed from date YYYY-MM-DD via SHA-256 truncated to 32-bit; boardSizeForSeed).
- HexBoardView (custom View, hex tile drawing, touch tracking, path rendering).

## Build / Config
- Gradle Kotlin DSL (`build.gradle.kts`): compileSdk 34, minSdk 24, namespace com.gamefactory.hexflowconnect, Kotlin 1.9.10.
- Dependencies: androidx.core:core-ktx:1.12.0; test: junit:junit:4.13.2.
- Performance budget: 60 fps, single-threaded (no coroutine overhead), <2 MB APK.

## Test Strategy
- HexBoardTest: adjacency (2 cells), build 4×4 / 6×6 / 8×8 (3 assertions).
- GameEngineTest: valid path, backtrack invalid, scorePath (3 assertions).
- DailySeedGeneratorTest: same date → same seed; different date → likely different (2 assertions).
- PathValidatorTest: continuous path valid, non-adjacent invalid, bridge pass, undo restores (4 assertions).
- Gate: `./gradlew test assembleDebug` green.

## Security / Quality
- Fully offline; no network/analytics in MVP.
- Color-blind safe: 5 colors + shape markers; high-contrast mode.
- Replayability: deterministic daily seed → fair leaderboards.
- Performance: no GC-heavy allocations per frame; geometric drawing only.