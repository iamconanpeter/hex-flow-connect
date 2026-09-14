# Hex Flow Connect — Tasks (Plan-Mode Gate)

- [x] Q&A discovery in `docs/spec.md`
- [x] Technical plan in `docs/technical-plan.md`
- [x] Test strategy in `docs/technical-plan.md`
- [ ] Implement / verify `HexBoard.kt` (board model, adjacency) – present
- [ ] Implement / verify `HexCell.kt` (axial coordinates) – present
- [ ] Implement / verify `DailySeedGenerator.kt` (UTC date seed → PRNG) – present
- [ ] Implement / verify `GameEngine.kt` (state, undo, star rating) – present
- [ ] Implement / verify `PathValidator.java` (single-stroke validation) – present
- [ ] Implement / verify `HexBoardView.java` (hex drawing, touch, path rendering) – present (stub)
- [ ] Add/expand JUnit4 tests: `DailySeedGeneratorTest.kt`, `HexBoardTest.kt`, `GameEngineTest.kt`, `PathValidatorTest.kt`
- [ ] Run `./gradlew test assembleDebug`
- [ ] Commit + push to `https://github.com/iamconanpeter/hex-flow-connect`
- [ ] Update `research/game_factory_status.json`
- [ ] Update `research/ios_android_game_pipeline.md`

Dependencies: `HexCell` → `HexBoard` → `GameEngine`/`PathValidator`; `DailySeedGenerator` → `HexBoard`; tests after production classes; validation after tests; push after green build.