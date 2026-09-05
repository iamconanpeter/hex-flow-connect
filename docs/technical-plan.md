# Hex Flow Connect — Technical Plan — conceptRank 19
Package: com.gamefactory.hexflowconnect
Architecture:
- HexCell (axial coords q,r + adjacency + neighbors)
- DailySeedGenerator (SHA-256 seed from date, board size derived)
- GameEngine (validatePath, scorePath, isValidStart)
- HexBoardTest (4 JUnit assertions)
Build: ./gradlew test assembleDebug (requires root build.gradle.kts with plugin versions 8.2.0 / 1.9.10)
Performance target: <16ms frame; tiny asset footprint; no network dependency.
Test strategy: HexBoardTest covers adjacency, seed determinism, path validation, backtrack failure.
