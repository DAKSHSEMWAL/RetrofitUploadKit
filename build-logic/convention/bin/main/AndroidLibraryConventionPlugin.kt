import com.android.build.gradle.LibraryExtension
import com.eazyupdates.AndroidBuildConfig
import com.eazyupdates.configureFlavors
import com.eazyupdates.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
                apply("kotlin-kapt")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = AndroidBuildConfig.targetSdkVersion
                configureFlavors(this)
            }
        }
    }
}
