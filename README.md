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
Developed with ❤️ by [Santiago Mattiauda](https://github.com/santimattius)
