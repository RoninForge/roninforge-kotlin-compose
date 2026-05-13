# Changelog

## [1.0.0] - 2026-05-13

### Added

- 5 rule files: `compose-core` (always-on, Kotlin 2.x + Compose + Material 3 fundamentals), `compose-anti-patterns` (always-on, 20 legacy regressions), `compose-architecture` (`.kt`), `compose-performance` (`.kt`), `compose-testing` (agent-requested)
- `/compose-new-screen` skill: scaffold stateless Screen + stateful Route + sealed UI state + ViewModel + route + previews + test
- `/compose-migrate-views-to-compose` skill: per-screen migration from XML/View system to Compose
- `/compose-validate` skill: grep audit for the 20 tracked anti-patterns
- `/compose-modernize-build` skill: bring an older Android project to Kotlin 2.x + Version Catalogs + KSP + Material 3
- `compose-reviewer` subagent: severity-grouped review
- Test fixtures: anti-pattern (View system + LiveData + GlobalScope + Material 2) and correct (Compose + StateFlow + Material 3 + Hilt + Version Catalog)
- Plugin validation script and CI workflow
