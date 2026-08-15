# Hex Flow Connect - Technical Plan

## Tech Stack
- Language: Kotlin
- UI: Jetpack Compose (Material 3)
- Build System: Gradle 8.x (Android Studio Hedgehog+)
- Minimum API: 24 (Android 7.0)
- Target API: 34

## Architecture Overview

```
┌─────────────────────────────────────────────────────────────┐
│                    Presentation Layer                        │
│  ┌───────────┐  ┌───────────┐  ┌──────────────┐             │
│  │ MainMenu  │  │  Puzzle   │  │    Stats     │             │
│  │ Screen    │  │   Screen  │  │    Screen    │             │
│  └───────────┘  └───────────┘  └──────────────┘             │
├─────────────────────────────────────────────────────────────┤
│                    View Model Layer                          │
│  ┌───────────┐  ┌───────────┐  ┌──────────────┐             │
│  │MainVM     │  │PuzzleVM   │  │StatsVM       │             │
│  └───────────┘  └───────────┘  └──────────────┘             │
├─────────────────────────────────────────────────────────────┤
│                    Domain Layer                              │
│  ┌───────────┐  ┌───────────┐  ┌──────────────┐             │
│  │Board      │  │PathSolver │  │PuzzleSeed    │             │
│  │Generator  │  │           │  │              │             │
│  └───────────┘  └───────────┘  └──────────────┘             │
├─────────────────────────────────────────────────────────────┤
│                    Data Layer                                │
│  ┌───────────┐  ┌───────────┐  ┌──────────────┐             │
│  │LocalData  │  │BoardData  │  │Prefs         │             │
│  │Store      │  │Store      │  │Store         │             │
│  └───────────┘  └───────────┘  └──────────────┘             │
└─────────────────────────────────────────────────────────────┘
```

## Core Components

### 1. HexGrid Model
- **HexCoordinate**: Axial coordinate system (q, r) for hex positions
- **HexCell**: Cell with color, type (node, blocker, bridge, empty)
- **HexBoard**: 2D grid of cells with size constraints (7x7 to 15x15)

### 2. Path Engine
- **PathNode**: Typed node (input, output, merge, bridge entry, bridge exit)
- **StrokePath**: Ordered list of coordinates representing player path
- **PathValidator**: Validates path against all rules (connectivity, color matching, crossing)

### 3. Color System
- **ColorType**: Enum (Red, Blue, Yellow, Green, Purple, Orange)
- **ColorRules**: Mixing rules (Red + Blue = Purple, etc.)
- **ColorBlindMode**: Alternative palettes (diamonds, patterns, textures)

### 4. Board Generator
- **PuzzleGenerator**: Seeded algorithm for deterministic daily puzzles
- **DifficultyLevels**: Easy (5-6 connected pairs), Medium (7-9 pairs), Hard (10-12 pairs)
- **UniqueSolutionGuarantee**: Validates boards have exactly one optimal path

## Data Structures

```kotlin
data class HexCoordinate(val q: Int, val r: Int)

enum class CellType { 
    EMPTY, NODE_RED, NODE_BLUE, NODE_YELLOW, NODE_GREEN, 
    BLOCKER, BRIDGE, MERGE_POINT 
}

data class HexCell(
    val type: CellType,
    val isBridge: Boolean = false,
    val isMerge: Boolean = false
)

data class HexBoard(
    val size: Int,
    val cells: Array<Array<HexCell>>,
    val seed: Long
)
```

## File Structure
```
hex-flow-connect/
├── app/
│   ├── src/main/
│   │   ├── java/com/conan/hexflow/
│   │   │   ├── ui/               # Compose screens
│   │   │   │   ├── MainMenu.kt
│   │   │   │   ├── PuzzleScreen.kt
│   │   │   │   └── StatsScreen.kt
│   │   │   ├── viewmodel/        # State management
│   │   │   │   ├── PuzzleVM.kt
│   │   │   │   └── MainVM.kt
│   │   │   ├── domain/           # Core game logic
│   │   │   │   ├── HexBoard.kt
│   │   │   │   ├── PathEngine.kt
│   │   │   │   ├── ColorSystem.kt
│   │   │   │   └── PuzzleGenerator.kt
│   │   │   └── data/             # Persistence
│   │   │       └── LocalBoardStore.kt
│   └── src/test/
│       └── java/com/conan/hexflow/
│           ├── domain/
│           └── viewmodel/
├── build.gradle.kts
└── settings.gradle.kts
```

## Performance Targets
- Frame time: <16.67ms (60 FPS)
- Memory: <100MB peak
- APK size: <5MB (basic assets only)
- Startup time: <1.5s cold start

## Security Considerations
- No network permissions required
- All data stored in local SharedPreferences/internal storage
- Seeded generation means no malicious input vectors
- Resource files are read-only after compilation

## Testing Strategy
- JUnit4 unit tests for PathEngine and BoardGenerator
- Compose UI tests for screen interactions
- Performance benchmark tests for frame time validation
- Robolectric tests for Android framework dependencies

## Build Validation
```bash
./gradlew test assembleDebug
# Expected: 14/14 unit tests pass, APK generated under 5MB
```

## Post-MVP Extensions
- Multiplayer challenge mode
- Seasonal visual themes
- Cross-platform leaderboard sync (optional)