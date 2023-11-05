@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("retrofitfileuploadkit.android.application")
    id("retrofitfileuploadkit.android.application.compose")
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.dakshsemwal.retrofitfileuploadkit"
    defaultConfig {
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    kotlinOptions {
        freeCompilerArgs += "-Xextended-compiler-checks"
    }


    buildTypes {
        release {
            manifestPlaceholders["crashlyticsEnabled"] = true
            buildConfigField("String", "host", "\"https://api.eazyupdates.com\"")
            // Enables code shrinking, obfuscation, and optimization for only your project's release build type. Make sure to use a build variant with `isDebuggable=false`.
            isShrinkResources = true
            isMinifyEnabled = true
            // Includes the default ProGuard rules files that are packaged with the Android Gradle plugin.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            manifestPlaceholders["crashlyticsEnabled"] = true
            buildConfigField("String", "host", "\"https://api.staging.eazyupdates.com\"")
        }
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE-LGPL-2.1.txt"
            excludes += "META-INF/LICENSE-LGPL-3.txt"
            excludes += "META-INF/LICENSE-W3C-TEST"
            excludes += "META-INF/DEPENDENCIES"
            excludes += "META-INF/LICENSE-LGPL-3.txt"
        }
    }
}

dependencies {
    implementation(projects.retrofitfileuploadkit)

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.bundles.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.glide)

    // Retrofit-Gson
    implementation(libs.bundles.retrofit)
    implementation(libs.interceptor)

    implementation(libs.hilt.android.core)
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigationCompose)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.androidx.test.ext.unit.ktx)
    implementation(libs.play.core)
}

kapt {
    correctErrorTypes = true
}
