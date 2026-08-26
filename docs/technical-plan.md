# Technical Plan — Hex Flow Connect (MVP)

## Architecture
- Android Studio project: `app/` module, Kotlin, minSdk 21, targetSdk 34
- Game loop: MainActivity → HexBoardView (custom View) → LinePathRenderer
- Data: Board seed (int) → deterministic hex layout (PointF array) using hex grid math (axial coords)
- Tests: JUnit4 core logic tests (board generation, path validation, color-match), 14 test cases

## Performance Budget
- Canvas draw < 16ms @ 60fps on mid-range device
- Board generation < 50ms
- Memory < 30MB peak

## Security / Safety
- Offline-only; no external APIs → no data exfiltration risk
- Undo token stored in SharedPreferences (local only)

## Post-MVP
- Level editor (UGC)
- Online leaderboard (optional Firebase integration)
- Color-blind mode with shape indicators
