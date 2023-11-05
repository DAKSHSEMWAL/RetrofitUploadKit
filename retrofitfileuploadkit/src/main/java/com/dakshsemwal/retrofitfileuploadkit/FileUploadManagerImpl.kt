package com.dakshsemwal.retrofitfileuploadkit

import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.lang.reflect.Type
import javax.inject.Inject

/**
 * Implementation of the FileUploadManager interface.
 *
 * This class is responsible for handling file uploads by interfacing with a backend service.
 * It extends the BaseDataSource to leverage common network operation handling like error and response parsing.
 * This class should be used internally within the module that provides it, and not be exposed directly
 * to external modules or consumers.
 *
 * @property fileUploadApi An instance of FileUploadApi to handle the actual network requests for file uploading.
 */
internal class FileUploadManagerImpl @Inject constructor(
    private val fileUploadApi: FileUploadApi
) : FileUploadManager, BaseDataSource() {

    /**
     * Uploads a file to the specified URL.
     *
     * This method decides whether to use a multipart or a byte array upload strategy based on
     * the `isMultipart` parameter. Multipart is generally used for file uploads along with additional
     * form data, whereas byte array uploads are typically used for raw file uploads without additional data.
     *
     * @param T The expected type of the response body if the upload is successful.
     * @param url The URL to which the file should be uploaded.
     * @param file The file that needs to be uploaded.
     * @param name The name of the form field to be used for the file upload. Defaults to a generic name.
     * @param isMultipart A flag to indicate if the upload should be as multipart/form-data or as a raw byte array.
     * @return A Resource wrapper containing the upload result which can be a success or error state.
     */
    override suspend fun <T> uploadFile(
        type: Type, // Add this parameter to pass the correct type to Retrofit/Gson
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean
    ): Resource<T> {
        return getResult(type) {
            val extension = getFileExtension(file.name)
            if (isMultipart) {
                val requestBody = file.asRequestBody("*/*".toMediaTypeOrNull())
                val multipartPart = MultipartBody.Part.createFormData(name, file.name, requestBody)
                fileUploadApi.uploadFile(
                    url = url,
                    fileExtension = extension,
                    file = multipartPart
                )
            } else {
                val requestBody =
                    file.readBytes().toRequestBody("application/octet-stream".toMediaTypeOrNull())
                fileUploadApi.uploadFile(
                    url = url,
                    fileExtension = extension,
                    file = requestBody
                )
            }
        }

    }

    private fun getFileExtension(fileName: String): String {
        // Handle cases where there might not be an extension or if the file starts with a dot
        val dotIndex = fileName.lastIndexOf('.')
        return if (dotIndex > 0) {
            fileName.substring(dotIndex + 1)
        } else {
            ""
        }
    }
}


