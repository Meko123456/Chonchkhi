plugins {
    id("chonchkhi.android.library")
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "io.github.meko123456.chonchkhi.feature.home"
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui)
    implementation(libs.compose.material3)
    implementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.compose.ui.tooling)
}
