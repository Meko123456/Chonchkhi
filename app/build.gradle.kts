import java.util.Properties

plugins {
    id("chonchkhi.android.application")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.baselineprofile)
}

// Optional release signing: drop a keystore.properties (see keystore.properties.example)
// at the repo root and release builds get signed; without it, debug/CI builds still work.
val keystoreProps = rootProject.file("keystore.properties")

android {
    namespace = "io.github.meko123456.chonchkhi.app"

    defaultConfig {
        applicationId = "io.github.meko123456.chonchkhi"
        versionCode = 1
        versionName = "0.1.0"
    }

    signingConfigs {
        if (keystoreProps.exists()) {
            create("release") {
                val props = Properties().apply { keystoreProps.inputStream().use { load(it) } }
                storeFile = file(props.getProperty("storeFile"))
                storePassword = props.getProperty("storePassword")
                keyAlias = props.getProperty("keyAlias")
                keyPassword = props.getProperty("keyPassword")
            }
        }
    }

    lint {
        // Lint every module the app depends on, not just this one: :core and :feature-home have no
        // lint task of their own in CI, and a project generated from this template would inherit
        // that gap. With this, one lintDebug run covers the whole graph.
        checkDependencies = true
        // A warning nobody reads is not a check: any new lint warning in any module fails the build,
        // the way a failing test does, so a project generated from this template starts defended.
        warningsAsErrors = true
        // Dependabot owns version bumps and opens a PR per bump; lint repeating "a newer version is
        // available" would turn CI red on every upstream release and bury the real findings.
        disable += setOf("GradleDependency", "NewerVersionAvailable", "AndroidGradlePluginVersion")
        // OldTargetApi fires whenever a platform newer than targetSdk is available, which is now
        // always: compileSdk is 37 and targetSdk is deliberately still 36. compileSdk only widens
        // the API surface available at compile time; targetSdk changes how the app behaves at
        // runtime, and that is a decision to take on its own commit rather than as a side effect of
        // a dependency needing a newer compile target.
        disable += "OldTargetApi"
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            // Unused resources go too. Code shrinking alone leaves the drawables, strings and
            // layouts that the shrunk code no longer references sitting in the APK.
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (keystoreProps.exists()) signingConfig = signingConfigs.getByName("release")
        }
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":feature-home"))
    implementation(platform(libs.compose.bom))
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    debugImplementation(libs.compose.ui.tooling)
    testImplementation(libs.junit)
    baselineProfile(project(":baselineprofile"))
}
