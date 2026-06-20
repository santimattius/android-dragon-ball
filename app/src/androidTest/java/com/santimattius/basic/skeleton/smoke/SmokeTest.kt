package com.santimattius.basic.skeleton.smoke

/** Marks instrumentation tests that must pass on the minified (R8) build. */
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
annotation class SmokeTest
