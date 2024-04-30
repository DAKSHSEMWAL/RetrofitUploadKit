plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.hilt) apply false
    alias(libs.plugins.ktlint) apply true
    alias(libs.plugins.maven) apply true
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
