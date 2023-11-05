package com.dakshsemwal.retorfituploadkit.network

import com.google.gson.annotations.SerializedName

sealed class FileUpload {
    data class Response(
        @SerializedName("data") var data: Data? = Data()
    )
}

data class ResizedUrls(

    @SerializedName("s1024") var s1024: ArrayList<String> = arrayListOf(),
    @SerializedName("s160") var s160: ArrayList<String> = arrayListOf(),
    @SerializedName("s320") var s320: ArrayList<String> = arrayListOf(),
    @SerializedName("s640") var s640: ArrayList<String> = arrayListOf()

)

data class ImageUpload(

    @SerializedName("url") var url: String? = null,
    @SerializedName("originalImgURL") var originalImgURL: String? = null,
    @SerializedName("resizedUrls") var resizedUrls: ResizedUrls? = ResizedUrls()

)


data class Data(

    @SerializedName("imageUpload") var imageUpload: ImageUpload? = ImageUpload()

)