package com.dakshsemwal.retorfituploadkit.network

import com.dakshsemwal.retrofitfileuploadkit.Resource
import kotlinx.coroutines.flow.flow
import java.io.File
import javax.inject.Inject

class FileUploadServiceImpl @Inject constructor(val fileUploadDataSource: FileUploadDataSource) :
    FileUploadService {
    override suspend fun uploadFile(
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean,
        isVideo: Boolean
    ) = flow {
        emit(FileUploadState.Uploading)
        if (isVideo)
            fileUploadDataSource.uploadVideo(
                file = file,
                url = url,
                name = name,
                isMultipart = isMultipart,
            ).also {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        emit(FileUploadState.Uploaded(url = it.data?.data?.data?.videoUpload?.resizedUrl))
                    }

                    Resource.Status.ERROR -> {
                        emit(FileUploadState.Error(error = it.message ?: "Something Went Wrong"))
                    }

                    else -> {
                        emit(FileUploadState.Error(error = "Something Went Wrong"))
                    }
                }
            }
        else
            fileUploadDataSource.uploadFile(
                file = file,
                url = url,
                name = name,
                isMultipart = isMultipart,
            ).also {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        emit(FileUploadState.Uploaded(url = it.data?.data?.imageUpload?.originalImgURL))
                    }

                    Resource.Status.ERROR -> {
                        emit(FileUploadState.Error(error = it.message ?: "Something Went Wrong"))
                    }

                    else -> {
                        emit(FileUploadState.Error(error = "Something Went Wrong"))
                    }
                }
            }
    }

}