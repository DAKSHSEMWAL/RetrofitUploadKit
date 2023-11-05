package com.dakshsemwal

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AndroidBuildConfig {
    const val applicationId = "com.dakshsemwal.retrofitfileuploadkit"
    const val compileSdkVersion = 34
    const val minSdkVersion = 26
    const val targetSdkVersion = 33

    // yyyymmdd followed by build number on the given day
    // eg: second build on 15th march 2032 will be 2032031502
    private val currentDate: String = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())

    // TODO update on every release
    private const val buildNumber = "0" // increment this for each build on the same day
    private const val patch = "01" // increment this for each build on the same day
    val versionCode =
        (currentDate + patch).toInt() // e.g. 2023050201 for May 2nd, 2023 (first build of the day)
    const val versionName: String = "1.5.${buildNumber}"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
