package com.eazyupdates

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object AndroidBuildConfig {
    const val applicationId = "com.eazyupdates"
    const val compileSdkVersion = 33
    const val minSdkVersion = 21
    const val targetSdkVersion = 32

    // yyyymmdd followed by build number on the given day
    // eg: second build on 15th march 2032 will be 2032031502
    val currentDate: String = SimpleDateFormat("yyyyMMdd", Locale.US).format(Date())
    const val buildNumber = "11" // increment this for each build on the same day
    val versionCode =
        (currentDate + buildNumber).toInt() // e.g. 2023050201 for May 2nd, 2023 (first build of the day)
    const val versionName = "1.0.11"
    const val testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
}
