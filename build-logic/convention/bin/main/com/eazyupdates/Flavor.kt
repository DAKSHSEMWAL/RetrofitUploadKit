package com.eazyupdates

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Project

enum class FlavorDimension {
    environment
}

enum class Flavor(val dimension: FlavorDimension, val applicationId: String, val host: String) {
    staging(
        dimension = FlavorDimension.environment,
        applicationId = "com.staging.eazyupdates",
        host = "\"https://api.staging.eazyupdates.com\""
    ),
    production(
        dimension = FlavorDimension.environment,
        applicationId = "com.eazyupdates",
        host = "\"https://api.eazyupdates.com\""
    ),
}

fun Project.configureFlavors(
    commonExtension: CommonExtension<*, *, *, *>
) {
    commonExtension.apply {
        flavorDimensions += FlavorDimension.environment.name
        productFlavors {
            Flavor.values().forEach {
                create(it.name) {
                    dimension = it.dimension.name
                    buildConfigField("String", "host", it.host)
                }
            }
        }
        testOptions {
            unitTests.isReturnDefaultValues = true
            unitTests.isIncludeAndroidResources = true
            unitTests.all {
                it.useJUnitPlatform()
            }
        }
    }
}
