plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.spotless)
    alias(libs.plugins.detekt)
}

detekt {
    buildUponDefaultConfig = true
    config.setFrom("$rootDir/config/detekt/detekt.yml")
    // Every module, not a hand-kept list. The list version silently stopped covering each new
    // module the moment someone added one, and a linter that quietly skips code is worse than no
    // linter: the green tick says the code was checked.
    source.setFrom(subprojects.map { file("${it.projectDir}/src") })
}

spotless {
    kotlin {
        target("**/*.kt")
        targetExclude("**/build/**")
        ktlint().editorConfigOverride(
            mapOf("ktlint_function_naming_ignore_when_annotated_with" to "Composable"),
        )
    }
    kotlinGradle {
        target("**/*.kts")
        targetExclude("**/build/**")
        ktlint()
    }
}
