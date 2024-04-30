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
    implementation(libs.androidx.media3.exoplayer)
    implementation(libs.androidx.media3.ui)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    testImplementation(libs.androidx.test.ext.unit.ktx)
    implementation(libs.play.core)
}

kapt {
    correctErrorTypes = true
}
