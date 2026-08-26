# Technical Plan

## Architecture
- Kotlin, Android SDK min 24, Custom View (Canvas)
- Classes: HexBoard, HexCell, GameEngine, PathValidator, DailySeedGenerator
- Axial coords (q, r), cube constraint q+r+s=0
- Input: Touch draw; validate adjacency + no backtrack + color match
- Tests: Unit for path, adjacency, seed determinism, par scoring
- Build: `./gradlew test assembleDebug`
- Performance: 60fps, 7x7 max, vector tiles
