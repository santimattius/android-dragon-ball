pluginManagement {
    buildscript {
        repositories {
            google()
            mavenCentral()
            maven("https://storage.googleapis.com/r8-releases/raw")
        }
        dependencies {
            classpath("com.android.tools:r8:9.3.7-dev") // pin for R8 Configuration Analyzer (Path A quantitative)
        }
    }
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
rootProject.name = "android-dragon-ball"
include(":app")
include(":macrobenchmark")
