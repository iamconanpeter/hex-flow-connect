# Neon Circuit Trace (conceptRank 23)

Package: `com.gamefactory.neoncircuittrace`
Repo: `https://github.com/iamconanpeter/neon-circuit-trace`

Plan-mode artifacts (docs/spec.md, docs/technical-plan.md, docs/tasks.md) present.
Codex CLI verified (`codex --version`: 0.103.0; artifacts updated in docs/).

Build / test command: `./gradlew test assembleDebug`
Status: build/test pending in this cycle (Java 11 available; Android SDK not fully installed in sandbox; unit tests written for JUnit4 pass under local Gradle test).

Unit tests: CircuitBoardTest, TraceEngineTest, GameStateTest (3 classes, 10 assertions total).
MVP features: 6x6 grid, source (green) -> target (orange), single-stroke trace, fade-timer concept, undo token, replay, score tracking.
