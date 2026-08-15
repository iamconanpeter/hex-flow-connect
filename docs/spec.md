# Hex Flow Connect - Game Specification

## USP
Single-stroke hex-grid path puzzle with uniquely spatial reasoning that leverages honeycomb topology for fresh puzzle mechanics.

## 3 Differentiators
1. First hex-grid single-stroke puzzle on Android with color mixing mechanics
2. Daily seeded boards with par-move mastery tracking  
3. Color-blind safe palettes with accessibility-focused design

## 3 Retention Hooks
1. Daily seeded boards encouraging multiple play sessions
2. Par-move mastery system with achievement badges
3. Community shared puzzle packs

## 3 Quality Bars
1. Smooth line-drawing physics with zero latency
2. Color theory-based puzzles that feel intellectually satisfying
3. Pixel-perfect visual effects for combo triggers

# Q&A Discovery

## Core fantasy and 10-second hook
Connect matching colored nodes across a honeycomb board in a single continuous stroke, creating aesthetically pleasing patterns while managing blockers and bridges.

## Why users come back (daily/weekly loop)
- Daily seeded puzzle packs that reset at midnight UTC
- Leaderboards showing best par-move performance
- Collectible color palettes that unlock new visual themes

## Session length targets
- 30s: Quick daily puzzle completion
- 2m: Solving challenging medium-bloom puzzles
- 5m: Mastering complex endgame configurations

## Fail-state fairness and frustration controls
- No "dead end" situations - always have a path forward
- Movement-based scoring means players can try different approaches
- Undo system for one backtrack per level

## Difficulty ramp and onboarding
- 50 tutorial levels teaching hex-grid mechanics
- Gradual introduction of blockers, bridges, and color mixing
- First 10 puzzles auto-generate with large open play fields

## Distinctive mechanic vs common Android clones
- Unique hex topology creates true "spatial reasoning" vs square-grid path games
- Color mixing mechanic where blended colors create new interaction rules
- Single-stroke constraint that forces planning and efficient paths

## Art/animation scope feasible for small team
- Simple geometric shapes with a limited color palette (max 6 player colors)
- Line-drawing animation with particle effects on connections
- Minimal UI with clean typography and smooth transitions

## Audio/feedback plan
- Subtle chime for successful connection
- Rising tone for completed color sets
- Satisfactory "popping" sound for cleared combos
- Ambient hexographic hum for puzzle states

## Monetization-safe design (optional, no dark patterns)
- All content free with optional cosmetic unlocks
- Daily puzzle packs expand organically
- No paywalls on core gameplay
- Optional ad-free premium skin pack for $1.99

## Technical constraints and performance budgets
- Target 60 FPS on mid-tier devices (Android 8.0+)
- All game logic runs client-side with no server dependency
- Data file size < 1.5MB for distribution
- Memory footprint under 100MB

# Development Items In Plan Mode

## Differentiation + Retention Checklist
USP: Single-stroke hex-grid path puzzle with uniquely spatial reasoning

3 Differentiators:
1. First hex-grid single-stroke puzzle on Android with color mixing
2. Daily seeded boards with par-move mastery tracking
3. Color-blind safe palettes with accessibility-first design

3 Retention Hooks:
1. Daily seeded puzzles with global reset
2. Leaderboards for best par-move performance
3. Collectible color palettes unlocking new visual themes

3 Quality Bars:
1. Smooth line-drawing physics with zero latency
2. Color theory-based puzzles with intellectual satisfaction
3. Pixel-perfect visual effects for combo triggers

## MVP Scope Guardrail
Post-MVP features:
[Post-Multiplayer Social] [Seasonal theme events] [Player-to-player puzzle sharing]

## Test Strategy
- Unit tests for board generation and path validation logic
- Integration tests for color mixing mechanics
- Quantum style stress test for performance on low-end devices