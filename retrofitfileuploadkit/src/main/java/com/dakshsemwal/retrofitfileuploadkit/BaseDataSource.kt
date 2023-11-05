package com.dakshsemwal.retrofitfileuploadkit

import android.util.Log
import com.google.gson.Gson
import retrofit2.HttpException
import retrofit2.Response
import java.lang.reflect.Type

/**
 * Base data source class that abstracts the common logic for making API calls and processing responses.
 * This class is intended to be the foundation for any repository class that interfaces with network requests.
 */
internal abstract class BaseDataSource {

    /**
     * Executes a network call and returns the result wrapped in a [Resource].
     * It handles success and error cases and wraps the response into a [Resource] object that
     * the UI can easily interpret.
     *
     * @param T The type parameter of the expected result.
     * @param call A suspend lambda that executes a network request and returns a [Response].
     * @return A [Resource] object containing the result of the network call.
     */
    protected suspend fun <T> getResult(type: Type, call: suspend () -> Response<*>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                val body = response.body()?.toString()
                body?.let {
                    // Use Gson to parse the JSON into the correct type
                    val result = Gson().fromJson<T>(body, type)
                    return Resource.success(result)
                }
            }
            return error("No body in response", 204) // Or appropriate error handling
        } catch (e: HttpException) {
            return error(e.localizedMessage ?: "Network error", e.code())
        } catch (e: Exception) {
            Log.e("Api Exception Log", e.message ?: e.localizedMessage)
            return error(e.message ?: "An error occurred", 0)
        }
    }

    /**
     * Helper method to create an error [Resource] with a given message and error code.
     *
     * @param T The type parameter of the Resource which corresponds to the type of data expected.
     * @param message A string containing the error message.
     * @param code An integer representing the error code, typically an HTTP status code.
     * @return A [Resource] object with an ERROR status, containing the error message and code.
     */
    private fun <T> error(message: String, code: Int): Resource<T> {
        return Resource.error(message, errorCode = code)
    }
}
