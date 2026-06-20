# Dragon Ball Android Application

![Android](https://img.shields.io/badge/Platform-Android-brightgreen.svg)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-blue.svg)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-orange.svg)
![Build](https://github.com/santimattius/android-dragon-ball/actions/workflows/android.yml/badge.svg)

A modern Android application showcasing characters from the Dragon Ball series. This project serves as a reference for building robust, maintainable, and testable Android apps using the latest industry standards and tools.

## 🚀 Overview

The application fetches data from the [Dragon Ball API](https://dragonball-api.com) and presents it through a clean, responsive interface. It follows a modular architecture and emphasizes clean code principles.

### Key Features
- **Character Exploration**: Browse a comprehensive list of Dragon Ball characters.
- **Detailed Insights**: View in-depth information about individual characters.
- **Offline Support**: Robust data handling with reactive components.
- **Modern UI**: Fully built with Jetpack Compose following Material Design 3 guidelines.

## 🛠 Tech Stack & Architecture

This project leverages the following modern Android development tools and patterns:

- **Architecture**: MVVM (Model-View-ViewModel) with a Clean Architecture approach.
- **UI**: [Jetpack Compose](https://developer.android.com/jetpack/compose) for a fully declarative UI.
- **DI**: [Koin](https://insert-koin.io/) for lightweight and pragmatic dependency injection.
- **Networking**: [Retrofit](https://square.github.io/retrofit/) for type-safe API communication.
- **Serialization**: [Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization) for efficient JSON parsing.
- **Image Loading**: [Coil](https://coil-kt.github.io/coil/) for asynchronous image loading.
- **Async & Streams**: [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/flow.html) for reactive programming.
- **Navigation**: [Jetpack Navigation Compose](https://developer.android.com/jetpack/compose/navigation) for seamless screen transitions.

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
   Create a `local.properties` file in the root directory and add your API configuration (if required by the API provider):
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
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**: For hermetic network testing.
- **[Turbine](https://github.com/cashapp/turbine)**: For testing Coroutine Flows.

---

## R8 Configuration Analyzer — caso practico

Este repositorio incluye una demo practica del **R8 Configuration Analyzer**. La demo permite comparar el impacto de reglas ProGuard amplias vs. quirurgicas sobre la calidad de la minificacion. Ver el [articulo] para el contexto completo.

### Como funciona el analyzer

El R8 Configuration Analyzer genera un **reporte HTML** que muestra scores de shrinking, obfuscation y optimization, e identifica reglas unused, duplicadas y subsumed. Se activa pasando un system property al build de R8:

```bash
./gradlew :app:assembleBenchmark \
  -Dcom.android.tools.r8.dumpkeepradiushtmltodirectory=/tmp/r8-report
# Abre /tmp/r8-report/configanalyzer.html en el navegador
```

> Requiere R8 9.3.7-dev+ (ya pinned en `settings.gradle.kts`) y AGP 9.2+.

### Como reproducir el flujo

El historial tiene tres tags que representan cada estado del demo:

| Tag | Estado | Descripcion |
|-----|--------|-------------|
| [`r8-demo/00-baseline`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F00-baseline) | Estado base | Minificacion activa, `proguard-rules.pro` vacio |
| [`r8-demo/01-broad-rule`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F01-broad-rule) | Regla amplia | `-keep class core.data.** { *; }` — el anti-patron "por las dudas" |
| [`r8-demo/02-surgical-rule`](https://github.com/santimattius/android-dragon-ball/tree/r8-demo%2F02-surgical-rule) | Regla quirurgica | Keep acotado a `@SerializedName` unicamente |

### Generar reportes automaticamente

El script `scripts/r8-analyze.sh` hace checkout de cada tag, ensambla el build y abre los reportes en el navegador:

```bash
# Analizar los 3 tags del demo
./scripts/r8-analyze.sh

# Analizar solo tags especificos (01 y 02)
./scripts/r8-analyze.sh 01 02
```

Los reportes se guardan en `build/r8-analyzer-reports/<tag>/configanalyzer.html`.

### Generar reportes en CI

En GitHub Actions → **Run workflow** (dispatch manual) → job `r8-analyzer`. Los reportes HTML quedan disponibles como artifacts del run y pueden descargarse desde la UI de Actions.

Se puede especificar qué tags analizar en el input `r8_tags` (por defecto: `r8-demo/01-broad-rule,r8-demo/02-surgical-rule`).

### Resultados del analyzer

> **Nota**: completar esta tabla despues de correr el script `r8-analyze.sh` o el job de CI.

| Metrica            | 01-broad-rule | 02-surgical-rule | Delta |
|--------------------|---------------|------------------|-------|
| Shrinking score    | TBD           | TBD              | TBD   |
| Obfuscation score  | TBD           | TBD              | TBD   |
| Optimization score | TBD           | TBD              | TBD   |
| AAB size (MB)      | TBD           | TBD              | TBD   |
| Cold start (ms)    | TBD           | TBD              | TBD   |

### Otros comandos utiles

```bash
# Ensamblar build minificado
./gradlew :app:assembleBenchmark

# Correr smoke tests (requiere emulador conectado o GMD)
./gradlew :app:pixel6Api34BenchmarkAndroidTest \
  -Pandroid.testInstrumentationRunnerArguments.annotation=com.santimattius.basic.skeleton.smoke.SmokeTest

# Correr macrobenchmark (requiere dispositivo fisico o emulador desbloqueado)
./gradlew :macrobenchmark:connectedBenchmarkAndroidTest
```

### Nota sobre Gson vs kotlinx.serialization

La app usa **Gson** para la deserializacion de JSON (via Retrofit + `GsonConverterFactory`), por lo que las keep rules apuntan a la anotacion `@SerializedName` de Gson, no a `@Serializable` de kotlinx.serialization.

---
Developed with ❤️ by [Santiago Mattiauda](https://github.com/santimattius)
