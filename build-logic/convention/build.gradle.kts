plugins {
    `kotlin-dsl`
}

group = "io.github.meko123456.chonchkhi.buildlogic"

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "chonchkhi.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibrary") {
            id = "chonchkhi.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}
