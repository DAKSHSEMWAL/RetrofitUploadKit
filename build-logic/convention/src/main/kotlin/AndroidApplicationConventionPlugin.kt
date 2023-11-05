import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import com.dakshsemwal.AndroidBuildConfig
import com.dakshsemwal.configureKotlinAndroid
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
                apply("org.jetbrains.kotlin.kapt")
            }
            extensions.configure<BaseAppModuleExtension> {
                configureKotlinAndroid(this)
                defaultConfig {
                    applicationId = AndroidBuildConfig.applicationId
                    targetSdk = AndroidBuildConfig.targetSdkVersion
                    versionCode = AndroidBuildConfig.versionCode
                    versionName = AndroidBuildConfig.versionName
                }
            }
        }
    }
}
