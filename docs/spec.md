# Hex Flow Connect — Plan Mode Spec (ConceptRank 19)

## Q&A Discovery (derived assumptions marked [A])

1. **Core fantasy / 10-second hook:** Draw a single continuous line through a hex honeycomb, connecting matching colored nodes. Every tap extends the path; one wrong move forces restart. [A] Hook: "Can you see the line through the hive?"
2. **Retention loops:** Daily seeded boards (new layout each day), par-move mastery (3-star per board), progressive palette unlocks. [A]
3. **Session length target:** 1–3 min (short commute play). [A]
4. **Skill vs luck balance:** 90% skill (path-planning, color-mixing rules), 10% luck (starting node randomization within seed). [A]
5. **Fail-state fairness:** One undo token per board; failure = immediate restart (fast loop). [A]
6. **Difficulty ramp / onboarding:** 3-tutorial hex board → simple 2-node path → 4-node color-mix. [A]
7. **Distinctive mechanic vs Android clones:** Hex topology + single-stroke constraint vs square-grid line-draw clones. [A]
8. **Art/animation scope:** Flat geometric hex grid, smooth line-draw stroke, subtle particle clear effects, color-blind safe palettes (high contrast + shapes). [A] Small-team feasible.
9. **Audio/feedback plan:** Tap click (subtle), line extends (soft tone), color-match (ascending chime), fail (low tone). [A]
10. **Monetization-safe design:** No dark patterns; optional reward for undo token via short ad (opt-in), no energy limits. [A]
11. **Technical constraints / performance budget:** Android SDK 21+, 2D Canvas, <10MB install, no network required for core mode. [A]

## Differentiation Checklist
- USP: Hex topology + single-stroke path creates uniquely spatial reasoning vs square-grid clones.
- 3 differentiators: (1) Hex topology, (2) Single-stroke constraint, (3) Color-mixing rules
- 3 retention hooks: Daily seeded boards, par-movement mastery (3-star), progressive palette unlock
- 3 quality bars: Readable hex grid, smooth line-draw feedback, satisfying color-clear effects

## MVP Scope Guardrail
- MVP: 10 tutorial/campaign boards + 1 daily-seed mode + basic scoring + 1 undo token. Post-MVP: level editor, online leaderboard, more palettes.
- No scope creep before green `gradlew test assembleDebug`.
