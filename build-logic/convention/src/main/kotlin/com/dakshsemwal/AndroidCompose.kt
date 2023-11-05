package com.dakshsemwal

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType

internal fun Project.configureAndroidCompose(
    commonExtension: CommonExtension<*, *, *, *, *>,
) {
    val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

    commonExtension.apply {
        buildFeatures {
            compose = true
            viewBinding = true
        }

        composeOptions {
            kotlinCompilerExtensionVersion = libs.findVersion("androidx-compose").get().toString()
        }

        kotlinOptions {
            freeCompilerArgs = freeCompilerArgs
        }
    }
}
