package com.dakshsemwal.retorfituploadkit.network

import com.dakshsemwal.retrofitfileuploadkit.Resource
import java.io.File

interface FileUploadDataSource {
    suspend fun uploadFile(
        url: String = "/projects/image",
        file: File,
        name: String = "imageUpload",
        isMultipart: Boolean = true,
    ): Resource<FileUpload.Response>
    suspend fun uploadVideo(
        url: String = "/projects/video",
        file: File,
        name: String = "videoUpload",
        isMultipart: Boolean = true,
    ): Resource<VideoUploadResponse>
}