package com.dakshsemwal.retorfituploadkit.network

import kotlinx.coroutines.flow.Flow
import java.io.File

interface FileUploadService {
    suspend fun uploadFile(
        url: String = "/projects/image",
        file: File,
        name: String = "imageUpload",
        isMultipart: Boolean = true
    ): Flow<FileUploadState>
}