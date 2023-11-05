package com.dakshsemwal

const val PUBLISHING_GROUP = "io.github.dakshsemwal"

object AndroidBuildConfig {
    const val applicationId = "com.dakshsemwal.retrofitfileuploadkit"
    const val compileSdkVersion = 34
    const val minSdkVersion = 26
    const val targetSdkVersion = 33

    // TODO update on every release
    private const val buildNumber = "0" // increment this for each build on the same day
    val versionCode = 1
    const val versionName: String = "1.0.${buildNumber}"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
