# roninforge-kotlin-compose

[![Validate Plugin](https://github.com/RoninForge/roninforge-kotlin-compose/actions/workflows/validate.yml/badge.svg)](https://github.com/RoninForge/roninforge-kotlin-compose/actions/workflows/validate.yml)
[![License: MIT](https://img.shields.io/badge/License-MIT-blue.svg)](https://opensource.org/licenses/MIT)
[![GitHub release](https://img.shields.io/github/v/release/RoninForge/roninforge-kotlin-compose)](https://github.com/RoninForge/roninforge-kotlin-compose/releases)

Cursor plugin for modern Android development with Kotlin 2.x and Jetpack Compose. Teaches the AI to write stateless Screen + stateful Route composables, StateFlow + `collectAsStateWithLifecycle`, Hilt + `hiltViewModel()`, type-safe Navigation Compose, KSP, Version Catalogs, and Material 3. Catches 20 legacy regressions LLMs still produce.

## The Problem

LLMs trained on Android-View-system + Kotlin 1.x patterns ship code that does not match modern Compose. They produce:

- `findViewById<...>(R.id.x)` and `setContentView(R.layout.x)` in projects meant to be Compose-based
- `GlobalScope.launch { }` (leaks across the process)
- `LiveData` + `observeAsState` instead of `StateFlow` + `collectAsStateWithLifecycle`
- Public `MutableStateFlow` exposed from the ViewModel
- Force-unwrap `!!` everywhere
- Lowercase composable names (`@Composable fun userCard()`)
- `LazyColumn { items(list) { ... } }` without a `key` argument
- `LaunchedEffect(true)` / `LaunchedEffect(Unit)` as a catch-all
- `androidx.compose.material.*` (Material 2) imports in a Material 3 project
- `Modifier.padding(...).clickable { }` (dead zone around the button)
- `kotlinCompilerExtensionVersion` in `composeOptions` (removed in Kotlin 2.0+)
- `kapt` annotation processor instead of KSP
- ViewModel drilled through composition instead of `hiltViewModel()` at the screen root
- `remember { mutableStateOf() }` outside a `@Composable` function
- `runBlocking { }` on the main thread
- Side effects in composition body without `LaunchedEffect` / `DisposableEffect` / `SideEffect`
- Java-style `getX()` / `setX()` accessors instead of Kotlin properties
- Heavy work in the composition body (large `map` / `filter`) without `remember`
- `Log.d` calls scattered through the codebase
- Hardcoded versions in module `build.gradle.kts` instead of `libs.versions.toml`

## Install

```bash
git clone https://github.com/RoninForge/roninforge-kotlin-compose.git ~/.cursor/plugins/local/roninforge-kotlin-compose
```

Or copy into your project:

```bash
git clone https://github.com/RoninForge/roninforge-kotlin-compose.git
cp -r roninforge-kotlin-compose/rules/* your-project/.cursor/rules/
cp -r roninforge-kotlin-compose/skills/* your-project/.cursor/skills/
cp -r roninforge-kotlin-compose/agents/* your-project/.cursor/agents/
```

## What's Included

### Rules (5 files)

| Rule | Scope | What it does |
|------|-------|-------------|
| `compose-core` | Always active | Kotlin 2.x + `kotlin-compose` plugin, Material 3, state hoisting, StateFlow + `collectAsStateWithLifecycle`, Hilt, type-safe Navigation Compose, KSP, Version Catalogs, BOM |
| `compose-anti-patterns` | Always active | 20 regressions: findViewById, setContentView, GlobalScope, LiveData, public MutableState, `!!`, lowercase composables, LazyColumn without key, LaunchedEffect(true), Material 2, modifier order, kotlinCompilerExtensionVersion, kapt, runBlocking, side effects in composition |
| `compose-architecture` | `**/*.kt` | Feature modularization, repository + data source, sealed UI state, ViewModel + stateIn, Ktor + Kotlin Serialization, Room with Flow + KSP, structured concurrency |
| `compose-performance` | `**/*.kt` | Strong-skipping (Kotlin 2.x default), `@Stable` / `@Immutable`, `remember` keys, `derivedStateOf`, LazyColumn `key` + `contentType`, deferred reads |
| `compose-testing` | Agent-requested | `createComposeRule`, semantic matchers, ViewModel tests with `runTest` + Turbine, fake repositories, Hilt tests, Paparazzi screenshots |

### Skills (4 commands)

| Skill | Command | What it does |
|-------|---------|-------------|
| New screen | `/compose-new-screen` | Scaffold stateless Screen + stateful Route + ViewModel + sealed UI state + nav route + previews + test |
| Migrate Views to Compose | `/compose-migrate-views-to-compose` | Per-screen migration from XML/Activity to setContent + Route + Screen, with View-to-Compose component mappings |
| Validate | `/compose-validate` | Scan codebase for the 20 tracked anti-patterns, report by severity |
| Modernize build | `/compose-modernize-build` | Move to Kotlin 2.x: introduce libs.versions.toml, apply kotlin-compose plugin, kapt to KSP, BOM, Material 3, target SDK 36 |

### Agent (1 subagent)

| Agent | What it does |
|-------|-------------|
| `compose-reviewer` | Reviews Kotlin/Compose code by severity: critical (crashes, build breaks), warnings (regressions), suggestions (style + performance) |

## What Makes This Different

The existing .cursorrules for Android cover style ("write idiomatic Kotlin"). None of them:

- Force the `kotlin-compose` plugin and reject `kotlinCompilerExtensionVersion` (Kotlin 2.0+ requirement)
- Enforce Material 3 imports (every legacy project leaks `androidx.compose.material.*`)
- Push `collectAsStateWithLifecycle()` over `collectAsState()`
- Catch the `Modifier.padding(...).clickable { }` dead-zone bug
- Teach type-safe Navigation Compose with `@Serializable` routes
- Bundle a build-modernization skill for the catalog + KSP migration

## Fixtures

`tests/fixtures/anti-pattern-sample/` is an Activity + XML + LiveData + GlobalScope + Material 2 mess. `tests/fixtures/correct-sample/` is the Compose-only + ViewModel + StateFlow + Material 3 + Hilt + Version Catalog equivalent.

## License

MIT - see [LICENSE](LICENSE)

## Links

- [Jetpack Compose April 2026 release](https://android-developers.googleblog.com/2026/04/jetpack-compose-april-2026-updates.html)
- [Kotlin releases](https://kotlinlang.org/docs/releases.html) (2.x series, K2 + Compose plugin moved in 2.0)
- [Compose Compiler Gradle plugin](https://developer.android.com/develop/ui/compose/setup-compose-dependencies-and-compiler)
- [Type-safe Navigation in Compose](https://developer.android.com/guide/navigation/type-safe-destinations)
- [Material 3 Adaptive](https://developer.android.com/jetpack/androidx/releases/compose-material3-adaptive)
- [Cursor Plugin Documentation](https://docs.cursor.com/plugins)
- [RoninForge](https://roninforge.org)
