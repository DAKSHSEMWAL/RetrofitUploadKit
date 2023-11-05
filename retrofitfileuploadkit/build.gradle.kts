@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    id("retrofitfileuploadkit.android.library")
    alias(libs.plugins.hilt)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
}

dependencies {
    // module dependency
    implementation(libs.androidx.core.ktx)

    implementation(libs.hilt.android.core)
    implementation(libs.hilt.android)
    kapt(libs.androidx.hilt.compiler)
    kapt(libs.hilt.android.compiler)

    implementation(libs.androidx.lifecycle.runtime.ktx)

    implementation(libs.bundles.retrofit)
    implementation(libs.interceptor)

    // Test Dependency
    testImplementation(libs.bundles.test)
    testRuntimeOnly(libs.junit5.engine)
}
android {
    namespace = "com.dakshsemwal.retrofitfileuploadkit"
    buildTypes {
        release {
            isMinifyEnabled = true
            consumerProguardFile("proguard-rules.pro")
        }
    }
}

kapt {
    correctErrorTypes = true
}
