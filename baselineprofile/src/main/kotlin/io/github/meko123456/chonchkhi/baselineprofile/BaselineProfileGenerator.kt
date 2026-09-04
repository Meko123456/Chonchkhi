package io.github.meko123456.chonchkhi.baselineprofile

import androidx.benchmark.macro.junit4.BaselineProfileRule
import androidx.test.uiautomator.By
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test

/**
 * Records which code the app runs, so ART compiles it ahead of time instead of interpreting it on
 * a user's first launch. On a Compose app that is most of the win: the profiles generated here are
 * about 9,800 lines, roughly 7,400 of them Compose runtime and UI.
 *
 * There are deliberately two journeys, because they produce two different files:
 *
 *  - [startup] is the only one marked `includeInStartupProfile`, so it alone becomes
 *    `startup-prof.txt`. That file is applied with higher priority at install time, and it only
 *    helps while it stays small — everything added to it dilutes what it prioritises.
 *  - [homeScreen] goes into the baseline profile only. This is where journeys belong once the app
 *    has more than one screen.
 *
 * Marking every journey as startup collapses both files into the same content and quietly throws
 * that distinction away.
 *
 * ## Extending this
 *
 * The profile only covers what these journeys touch. Add a `@Test` per screen a user reaches
 * early — navigate to it, wait for content that proves it composed, and leave
 * `includeInStartupProfile` at its default.
 *
 * Regenerate with `./gradlew :app:generateBaselineProfile` on a connected device (API 33+, no root
 * needed) and commit the files it writes under `app/src/release/generated/baselineProfiles/`.
 */
class BaselineProfileGenerator {

    @get:Rule
    val rule = BaselineProfileRule()

    @Test
    fun startup() = rule.collect(
        packageName = PACKAGE,
        includeInStartupProfile = true,
    ) {
        pressHome()
        startActivityAndWait()
    }

    @Test
    fun homeScreen() = rule.collect(packageName = PACKAGE) {
        pressHome()
        startActivityAndWait()

        // Compose renders asynchronously, so waiting for the activity is not the same as waiting
        // for a frame. Without this the profile records an empty window and misses the very
        // composition work it exists to speed up — which is why this waits on text from the
        // feature module rather than on the package being on screen.
        device.wait(Until.hasObject(By.text(GREETING)), UI_TIMEOUT_MS)
        device.waitForIdle()
    }

    private companion object {
        const val PACKAGE = "io.github.meko123456.chonchkhi"

        /** `Greeting.greet("Chonchkhi")` — proves `:core` and `:feature-home` both ran. */
        const val GREETING = "Hello, Chonchkhi!"
        const val UI_TIMEOUT_MS = 5_000L
    }
}
