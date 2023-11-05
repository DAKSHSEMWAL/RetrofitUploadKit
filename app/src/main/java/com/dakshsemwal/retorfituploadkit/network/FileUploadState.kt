package com.dakshsemwal.retorfituploadkit.network

sealed class FileUploadState {
    object Uploading : FileUploadState()
    object Undefined : FileUploadState()
    data class Uploaded(val url: String?) : FileUploadState()
    data class Error(val error: String) : FileUploadState()
}