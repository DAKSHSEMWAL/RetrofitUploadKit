plugins {
    `kotlin-dsl`
    `kotlin-dsl-precompiled-script-plugins`
}

group = "com.dakshsemwal"


dependencies {
    implementation(libs.android.r8)
    implementation(libs.hilt.android.gradle.plugin)
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.org.jacoco.core)
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "retrofitfileuploadkit.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidApplicationCompose") {
            id = "retrofitfileuploadkit.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "retrofitfileuploadkit.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "retrofitfileuploadkit.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
    }
}