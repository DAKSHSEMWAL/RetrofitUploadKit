package com.dakshsemwal.retrofitfileuploadkit

import java.io.File
import java.lang.reflect.Type

interface FileUploadManager {
    suspend fun <T> uploadFile(
        type: Type,
        url: String,
        file: File,
        name: String = "imageUpload",
        isMultipart: Boolean = true,
    ): Resource<T>
}