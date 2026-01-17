# Dragon Ball Android Application

This is an Android application that showcases characters from the Dragon Ball series, built using modern Android development practices. It fetches data from a public Dragon Ball API and displays it in a user-friendly interface.

The project is built with:
- 100% Kotlin
- Jetpack Compose for the UI
- Material Design components
- A single-activity architecture with Jetpack Navigation
- A reactive programming model using Kotlin Coroutines and Flows.

## How to Build

1. Clone the repository.
2. You will need to provide your own API key for the Dragon Ball API. Create a `local.properties` file in the root of the project and add the following line:
```properties
apiKey="YOUR_API_KEY"
```
3. Build and run the app using Android Studio.

## Verification

Run check project:

```shell
> ./gradlew check
```

Run tests project:

```shell
> ./gradlew test
```

## DeteKt

```shell
> ./gradlew :app:detekt
```

## Coverage

Debug:

```shell
> ./gradlew :app:testDebugUnitTestCoverage
```

Release:

```shell
> ./gradlew :app:testReleaseUnitTestCoverage
```

## Core Libraries Used

- **[Jetpack Compose](https://developer.android.com/jetpack/compose)**: Modern toolkit for building native Android UI.
- **[Koin](https://insert-koin.io/)**: A pragmatic lightweight dependency injection framework for Kotlin.
- **[Retrofit](https://square.github.io/retrofit/)**: A type-safe HTTP client for Android and Java.
- **[Kotlinx Serialization](https://github.com/Kotlin/kotlinx.serialization)**: For parsing JSON data from the API.
- **[Coil](https://coil-kt.github.io/coil/compose/)**: An image loading library for Android backed by Kotlin Coroutines.
- **[Kotlin Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)** and **[Flow](https://kotlinlang.org/docs/flow.html)**: For managing background threads and handling asynchronous operations.
- **[Jetpack Navigation](https://developer.android.com/guide/navigation)**: For navigating between screens in the app.
- **[Mockk](https://mockk.io/)**: A mocking library for Kotlin.
- **[MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver)**: For testing network requests and responses.
- **[Turbine](https://github.com/cashapp/turbine)**: A small testing library for kotlinx.coroutines Flow.
