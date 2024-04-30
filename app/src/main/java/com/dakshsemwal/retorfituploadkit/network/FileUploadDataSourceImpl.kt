package com.dakshsemwal.retorfituploadkit.network

import com.dakshsemwal.retrofitfileuploadkit.FileUploadManager
import com.dakshsemwal.retrofitfileuploadkit.Resource
import com.google.gson.reflect.TypeToken
import java.io.File
import javax.inject.Inject

class FileUploadDataSourceImpl @Inject constructor(val fileUploadManager: FileUploadManager) :
    FileUploadDataSource {
    override suspend fun uploadFile(
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean
    ): Resource<FileUpload.Response> {
        val type = object : TypeToken<FileUpload.Response>() {}.type
        return fileUploadManager.uploadFile(
            type = type,
            url = url,
            file = file,
            name = name,
            isMultipart = isMultipart
        )
    }

    override suspend fun uploadVideo(
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean
    ): Resource<VideoUploadResponse> {
        val type = object : TypeToken<VideoUploadResponse>() {}.type
        return fileUploadManager.uploadFile(
            type = type,
            url = url,
            file = file,
            name = name,
            isMultipart = isMultipart
        )
    }
}