import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Applies the shared Android *library* config so modules don't repeat compileSdk/minSdk/Java.
 * Use with `id("chonchkhi.android.library")`.
 */
class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.android.library")
        extensions.configure(LibraryExtension::class.java) {
            // 37 because AndroidX now requires it: Compose BOM 2026.09.00 and core-ktx 1.19.0
            // ship AARs whose metadata declares a minimum compileSdk of 37, and a project on 36
            // fails at checkDebugAarMetadata before compiling a line. Note targetSdk stays where it
            // is — this only widens what is available at compile time.
            compileSdk = 37
            defaultConfig {
                minSdk = 26
            }
            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }
    }
}
