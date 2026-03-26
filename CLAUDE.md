# CLAUDE.md — AI Assistant Guide for Workspace_Kotlin

This file provides context for AI assistants (Claude, Copilot, etc.) working with this repository.

---

## Project Overview

This is a **Kotlin learning and demonstration project** for Android development. It covers modern Kotlin language features — including coroutines, Flow, value classes, sealed interfaces, delegation, and more — through practical, runnable Android examples. It is not a production application; it exists as a reference and educational sandbox.

- **Package:** `com.sample.myapplication`
- **Language:** Kotlin 2.0.21
- **Platform:** Android API 35 (Android 15), minSdk 21
- **Build System:** Gradle 8.9 with Android Gradle Plugin 8.7.3
- **UI:** AndroidX + View Binding (no `kotlin-android-extensions`)

---

## Repository Structure

```
Workspace_Kotlin/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/sample/myapplication/
│   │   │   │   ├── MainActivity.kt              # Entry point; runs all demos
│   │   │   │   ├── basics/                      # Core language fundamentals
│   │   │   │   ├── oop/                         # OOP, data/sealed/object classes, extensions
│   │   │   │   ├── functional/                  # Higher-order functions, scope functions
│   │   │   │   ├── collections/                 # Collections, ranges, loops, sequences
│   │   │   │   ├── types/                       # Type system, generics, value classes
│   │   │   │   ├── delegation/                  # Class & property delegation
│   │   │   │   └── coroutines/                  # Coroutines & Flow
│   │   │   ├── res/                             # Android resources
│   │   │   └── AndroidManifest.xml
│   │   ├── test/                                # Local JVM unit tests
│   │   └── androidTest/                         # Instrumented Android tests
│   ├── build.gradle                             # Module build config
│   └── proguard-rules.pro
├── build.gradle                                 # Root build config
├── gradle.properties                            # JVM args, Kotlin code style
├── settings.gradle                              # Module includes
└── gradle/wrapper/gradle-wrapper.properties     # Gradle 8.9
```

---

## Source Files and What They Demonstrate

### `basics/`
| File | Kotlin Concepts Covered |
|---|---|
| `VariablesAndProperties.kt` | `val`/`var`, `const val`, `lateinit`, `isInitialized`, backing fields, custom getter/setter, computed property, `by lazy`, `@Deprecated` with `ReplaceWith` |
| `Functions.kt` | Regular/single-expression functions, `Unit`, default params, named args, varargs, spread (`*`), infix notation, local functions, `tailrec`, `Nothing` return type |
| `NullSafety.kt` | Nullable types, safe call (`?.`), Elvis (`?:`), not-null assertion (`!!`), `let` scoping, `filterNotNull`, `mapNotNull`, `requireNotNull`, `checkNotNull` |

### `oop/`
| File | Kotlin Concepts Covered |
|---|---|
| `ClassesAndInheritance.kt` | Primary/secondary constructors, `init` blocks, `abstract` class, `open` class, interface with default implementations, multiple interface implementation |
| `DataClasses.kt` | Data class (`equals`/`hashCode`/`toString`/`copy`/`componentN`), destructuring, body-only properties excluded from generated fns, `data object` (Kotlin 1.9+) |
| `SealedClasses.kt` | `sealed class` hierarchy, `sealed interface` (Kotlin 1.5+), exhaustive `when` (no `else`), modelling results/UI events/state |
| `ObjectsAndCompanions.kt` | `object` declaration (singleton), `companion object` (factory + class-level members), companion implementing interface, object expression (anonymous class) |
| `Extensions.kt` | Extension functions, extension properties (no backing field), nullable receiver, Android `Context.showToast`, generic extensions with type bounds |

### `functional/`
| File | Kotlin Concepts Covered |
|---|---|
| `HigherOrderFunctions.kt` | Function types, lambdas, trailing lambda syntax, returning functions, nullable function types, `inline` functions, `inline` + `reified`, function references (`::`) closures |
| `ScopeFunctions.kt` | `let` / `run` / `with` / `apply` / `also` — context object, return value, and ideal use-case for each; combining scope functions |

### `collections/`
| File | Kotlin Concepts Covered |
|---|---|
| `CollectionsAndRanges.kt` | Ranges (`..`, `until`, `downTo`, `step`), `for`/`while`/`do-while`, labelled `break`/`continue`, `when` expression, `List`/`Map`/`Set`, collection operations (`filter`, `map`, `fold`, `groupBy`, `zip`, `chunked`, `windowed`), `Enum.entries` (Kotlin 1.9+) |
| `Sequences.kt` | Lazy vs eager evaluation, `generateSequence`, Fibonacci with pair state, `sequence` builder (`yield`/`yieldAll`), `constrainOnce` |

### `types/`
| File | Kotlin Concepts Covered |
|---|---|
| `TypeSystem.kt` | Smart casts (`is`/`!is`), unsafe cast (`as`), safe cast (`as?`), `Any`/`Unit`/`Nothing`, definitely non-nullable types `T & Any` (Kotlin 1.7+) |
| `Generics.kt` | Generic classes/functions, upper bounds, `where` clause, `out` (covariant) / `in` (contravariant) variance, star projection, `inline` + `reified` |
| `ValueClasses.kt` | `@JvmInline value class` (Kotlin 1.5+), zero-overhead type-safe wrappers, `init` validation, arithmetic operators, interface implementation |

### `delegation/`
| File | Kotlin Concepts Covered |
|---|---|
| `DelegationDemo.kt` | Class delegation (`by`), `by lazy`, `Delegates.observable`, `Delegates.vetoable`, custom property delegate (`getValue`/`setValue`), Map-backed delegation |

### `coroutines/`
| File | Kotlin Concepts Covered |
|---|---|
| `CoroutinesBasics.kt` | `suspend` functions, `runBlocking`, `launch`/`Job`, `async`/`Deferred`/`await`, `delay`, `coroutineScope` (parallel decomposition), `withContext`, cancellation (`ensureActive`/`cancel`), `withTimeout`/`withTimeoutOrNull`, `CoroutineExceptionHandler` |
| `FlowDemo.kt` | `flow` builder, `flowOf`, `asFlow`, `collect`, `filter`/`map`/`transform`/`take`/`onEach`, `catch`, `onCompletion`, `zip`, `flatMapConcat`, `StateFlow` (hot), `SharedFlow` (hot, multicast) |

---

## Build & Run

### Prerequisites
- Android Studio (Hedgehog or newer)
- JDK 17 (required by AGP 8.x)
- Android SDK with API 35 installed

### Build Commands

```bash
# Build the project (from repo root)
./gradlew assembleDebug

# Run unit tests (local JVM)
./gradlew test

# Run instrumented tests (requires connected device or emulator)
./gradlew connectedAndroidTest

# Clean build artifacts
./gradlew clean

# Full build with tests
./gradlew build
```

### Key Gradle Properties
- `kotlin.code.style=official` — enforces official Kotlin code style
- `org.gradle.jvmargs=-Xmx1536m` — JVM heap limit for Gradle

---

## Testing

### Unit Tests
- Location: `app/src/test/java/com/sample/myapplication/ExampleUnitTest.kt`
- Framework: JUnit 4.13.2
- Run with: `./gradlew test`

### Instrumented Tests
- Location: `app/src/androidTest/java/com/sample/myapplication/ExampleInstrumentedTest.kt`
- Framework: AndroidJUnit4 (`androidx.test.ext`) + Espresso 3.6.1
- Run with: `./gradlew connectedAndroidTest`
- Requires a running Android emulator or connected physical device

---

## Dependencies

### Runtime
| Dependency | Version | Purpose |
|---|---|---|
| `kotlin-stdlib` | 2.0.21 | Kotlin standard library |
| `androidx.appcompat:appcompat` | 1.7.0 | AndroidX AppCompat |
| `androidx.constraintlayout:constraintlayout` | 2.2.0 | ConstraintLayout for UI |
| `kotlinx-coroutines-android` | 1.8.1 | Coroutines + Android dispatcher |

### Testing
| Dependency | Version | Purpose |
|---|---|---|
| `junit` | 4.13.2 | Unit testing |
| `androidx.test.ext:junit` | 1.2.1 | AndroidX test runner |
| `espresso-core` | 3.6.1 | UI instrumented testing |

> Note: This project uses **AndroidX** (`androidx.*`). Do not revert to legacy support libraries (`com.android.support.*`).

---

## Code Conventions

- **Code style:** `kotlin.code.style=official` (enforced via `gradle.properties`)
- **Indentation:** 4 spaces
- **Naming:**
  - Classes / objects: `PascalCase`
  - Functions and variables: `camelCase`
  - Constants: `UPPER_SNAKE_CASE` (for `const val`)
  - Files: `PascalCase.kt` matching the primary class/concept name
- **No code quality tools** (ktlint, detekt) configured — follow standard Kotlin idioms
- Prefer `val` over `var`; use null-safety operators (`?.`, `?:`) over explicit null checks
- Each `demoXxx()` function is the public entry point for its topic; sub-helpers are private

---

## Architecture Notes

- **Single-module project**: only `:app` exists
- **Single Activity**: `MainActivity` calls all `demoXxx()` functions; output goes to Logcat
- **Sub-packages group topics**, not layers — this is a feature demo, not a layered app
- **No architecture pattern** (MVVM, MVP, etc.)
- **No networking, database, or persistence** layers
- **View Binding** is enabled — access views via `binding.viewId` (no `findViewById` boilerplate)
- **Coroutines** demos run on a background `Thread` in `MainActivity` to avoid blocking the UI thread

---

## What to Avoid

- Do not revert to `com.android.support.*` — the project uses AndroidX
- Do not use `kotlin-android-extensions` (deprecated) — use View Binding instead
- Do not downgrade Gradle / AGP without testing compatibility (current: Gradle 8.9 / AGP 8.7.3)
- Do not introduce new modules without updating `settings.gradle`
- Do not add production-oriented architecture (repositories, ViewModels, etc.) unless explicitly asked
- Do not create new files for one-off demonstrations — add to an existing relevant file in the correct sub-package

---

## CI/CD

No CI/CD pipeline is configured. There is no `.github/`, `Jenkinsfile`, or similar. All builds and tests must be run locally.

---

## Git

- **Main branch:** `master`
- **Remote:** `vandit067/Workspace_Kotlin`
- Commit messages follow a simple imperative style (e.g., `"Add sealed classes demo"`, `"Upgrade to Kotlin 2.0"`)
