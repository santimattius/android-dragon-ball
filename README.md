# Dragon Ball Android Application

![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg)
![Build](https://github.com/santimattius/android-dragon-ball/actions/workflows/android.yml/badge.svg)

A modern Android application showcasing characters from the Dragon Ball series. This project serves
as a reference for building robust, maintainable, and testable Android apps using the latest
industry standards and tools.

## 🚀 Overview

The application fetches data from the [Dragon Ball API](https://dragonball-api.com) and presents it
through a clean, responsive interface. It follows a modular architecture and emphasizes clean code
principles.

### Key Features

- **Character Exploration**: Browse a comprehensive list of Dragon Ball characters.
- **Detailed Insights**: View in-depth information about individual characters.
- **Offline Support**: Robust data handling with reactive components.
- **Modern UI**: Fully built with Jetpack Compose following Material Design 3 guidelines.

## 🛠 Tech Stack & Architecture

This project leverages the following modern Android development tools and patterns:

- **Architecture**: MVVM (Model-View-ViewModel) with a Clean Architecture approach.
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative
  UI.
- **DI**: [Koin](https://insert-koin.io/) for lightweight and pragmatic dependency injection.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) for type-safe API communication.
- **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for
  efficient JSON parsing.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for asynchronous image loading.
- **Async & Streams**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html)
  and [Flow](https://kotlinlang.org/docs/flow.html) for reactive programming.
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for
  seamless screen transitions.

## 🏗 Project Structure

```text
app/src/main/java/com/santimattius/basic/skeleton/
├── core/          # Core data models and networking logic
├── di/            # Dependency Injection modules (Koin)
├── ui/            # UI layer (Compose screens, components, theme)
│   ├── screens/   # Individual feature screens (Home, Detail)
│   ├── component/ # Reusable UI components
│   └── navigation/# Navigation graph definitions
└── MainActivity.kt# Entry point activity
```

## ⚙️ Setup & Installation

1. **Clone the repository**:
   ```shell
   git clone https://github.com/santimattius/android-dragon-ball.git
   ```
2. **Configure API Access**:
   Create a `local.properties` file in the root directory and add your API configuration (if
   required by the API provider):
   ```properties
   apiKey="YOUR_API_KEY"
   ```
3. **Build the project**:
   Open in Android Studio and sync with Gradle.

## 🧪 Testing & Quality

High code quality is maintained through a comprehensive suite of tests and analysis tools.

### Running Tests

Execute all unit tests via Gradle:

```shell
./gradlew test
```

### Code Analysis (Detekt)

Run static code analysis to ensure adherence to Kotlin coding standards:

```shell
./gradlew :app:detekt
```

### Test Coverage

Generate coverage reports for your builds:

- **Debug**: `./gradlew :app:testDebugUnitTestCoverage`
- **Release**: `./gradlew :app:testReleaseUnitTestCoverage`

## 📚 Libraries Used in Testing

- **[Mockk](https://mockk.io/)**: For powerful and idiomatic mocking in Kotlin.
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**: For hermetic
  network testing.
- **[Turbine](https://github.com/cashapp/turbine)**: For testing Coroutine Flows.

---

## R8 Configuration Analyzer — Practical Demo

This repository is a reproducible case study for the **R8 Configuration Analyzer**. It demonstrates
the impact of broad vs. surgical ProGuard keep rules on R8's shrinking, obfuscation, and optimization
scores. See the [article] for the full write-up.

### How the Analyzer Works

The R8 Configuration Analyzer produces an **HTML report** with scores for shrinking, obfuscation,
and optimization, and identifies unused, duplicate, and subsumed rules. It is activated by passing a
system property to the R8 build:

```bash
./gradlew :app:assembleBenchmark \
  -Dcom.android.tools.r8.dumpkeepradiushtmltodirectory=/tmp/r8-report
# Open /tmp/r8-report/configanalyzer.html in your browser
```

> Requires R8 9.3.7-dev+ (already pinned in `settings.gradle.kts`) and AGP 9.2+.

### Reproducing the Flow

The git history has three tags representing each state of the demo:

| Tag                                                                                                               | State            | Description                                                         |
|-------------------------------------------------------------------------------------------------------------------|------------------|---------------------------------------------------------------------|
| [`r8-demo/00-baseline`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F00-baseline)           | Baseline         | Minification active, `proguard-rules.pro` empty                     |
| [`r8-demo/01-broad-rule`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F01-broad-rule)       | Broad rule       | `-keep class core.data.** { *; }` — the "just in case" anti-pattern |
| [`r8-demo/02-surgical-rule`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F02-surgical-rule) | Surgical rule    | Keep scoped to `@SerializedName` fields and constructors only        |

### Generating Reports Automatically

The `scripts/r8-analyze.sh` script checks out each tag, assembles the benchmark build, and opens
the reports in the browser:

```bash
# Analyze all 3 demo tags
./scripts/r8-analyze.sh

# Analyze specific tags only
./scripts/r8-analyze.sh 01 02
```

Reports are saved to `build/r8-analyzer-reports/<tag>/configanalyzer.html`.

### Generating Reports in CI

Go to GitHub Actions → **Run workflow** (manual dispatch) → job `r8-analyzer`. The HTML reports are
uploaded as workflow artifacts and can be downloaded from the Actions UI.

The `r8_tags` input controls which tags are analyzed (default:
`r8-demo/01-broad-rule,r8-demo/02-surgical-rule`).

### Analyzer Results

| Metric             | 01-broad-rule | 02-surgical-rule | Delta |
|--------------------|---------------|------------------|-------|
| Shrinking score    | 99.0%         | 99.1%            | +0.1% |
| Obfuscation score  | 99.0%         | 99.1%            | +0.1% |
| Optimization score | 98.6%         | 98.7%            | +0.1% |
| AAB size (MB)      | TBD           | TBD              | TBD   |
| Cold start (ms)    | TBD           | TBD              | TBD   |

### Other Useful Commands

```bash
# Assemble minified build
./gradlew :app:assembleBenchmark

# Run smoke tests (requires connected emulator or GMD)
./gradlew :app:pixel6Api34BenchmarkAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.annotation=com.santimattius.basic.skeleton.smoke.SmokeTest

# Run macrobenchmark (requires physical device or unlocked emulator)
./gradlew :macrobenchmark:connectedBenchmarkAndroidTest
```

### Note on Gson vs. kotlinx.serialization

The app uses **Gson** for JSON deserialization (via Retrofit + `GsonConverterFactory`), so keep rules
target Gson's `@SerializedName` annotation rather than kotlinx.serialization's `@Serializable`.

---
Developed with ❤️ by [Santiago Mattiauda](https://github.com/santimattius)
