package com.dakshsemwal.retorfituploadkit.network

data class VideoUploadResponse(
    val data: VideoUploadData
)

data class VideoUploadData(
    val code: Int,
    val status: String,
    val data: VideoData
)

data class VideoData(
    val videoUpload: VideoUploadDetails
)

data class VideoUploadDetails(
    val url: String,
    val originalURL: String,
    val resizedUrl: String
)
