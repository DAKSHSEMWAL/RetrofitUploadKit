package com.dakshsemwal.retrofitfileuploadkit

import com.google.gson.JsonObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query
import retrofit2.http.Url

/**
 * Interface defining the API for file uploads.
 * This interface is used by Retrofit to create an implementation for file upload operations.
 */
interface FileUploadApi {

    /**
     * Uploads a file to a server using a multipart form data POST request.
     *
     * This method is suitable for file uploads along with additional form data.
     * The file is uploaded as a part of a multipart request. It is wrapped in a [MultipartBody.Part]
     * which provides the file's metadata along with the binary content.
     *
     * @param T The expected type of the response body if the upload is successful.
     * @param url The endpoint URL where the file should be uploaded.
     * @param file The multipart part containing the file and its metadata.
     * @return A [Response] object containing either the successful result of type [T] or an error.
     */
    @Multipart
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Query("fileExtension") fileExtension: String,
        @Part file: MultipartBody.Part
    ): Response<JsonObject>

    /**
     * Uploads a file to a server using a raw byte array POST request.
     *
     * This method is typically used for uploading a file without any additional data.
     * The file's binary content is sent as a raw byte array encapsulated in a [RequestBody].
     *
     * @param url The endpoint URL where the file should be uploaded.
     * @param file The request body containing the file's binary content.
     * @return A [Response] object containing either the successful result of type [T] or an error.
     */
    @POST
    suspend fun uploadFile(
        @Url url: String,
        @Query("fileExtension") fileExtension: String,
        @Body file: RequestBody
    ): Response<JsonObject>

}
