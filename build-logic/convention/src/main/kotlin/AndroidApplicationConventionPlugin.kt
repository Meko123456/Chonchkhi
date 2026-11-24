import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies the shared Android *application* config so modules don't repeat compileSdk/minSdk/Java.
 * Use with `id("chonchkhi.android.application")`.
 */
class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.application")
        extensions.configure(ApplicationExtension::class.java) {
            // 37.1 because AndroidX requires it: Compose 1.13 (BOM 2026.11.01) ships AARs whose
            // metadata declares a minimum compileSdk of 37.1, and a project on 37.0 fails at
            // checkDebugAarMetadata before compiling a line (37 came the same way, from BOM
            // 2026.09.00 and core-ktx 1.19.0). Needs the 37.1 platform, which AGP fetches on CI.
            compileSdk = 37
            compileSdkMinor = 1
            defaultConfig {
                minSdk = 26
                targetSdk = 37
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}
