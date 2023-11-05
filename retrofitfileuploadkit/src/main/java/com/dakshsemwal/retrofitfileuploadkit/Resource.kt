package com.dakshsemwal.retrofitfileuploadkit

/**
 * A wrapper for data with a UI-friendly status.
 * This is used to encapsulate the result of an operation, along with its status and an optional message.
 *
 * @param T The type of the data being held.
 * @property status The status of the resource, indicating whether it is in a SUCCESS, ERROR, or LOADING state.
 * @property data The actual data. This might be null if there is an error or if data is being loaded.
 * @property message An optional message that might accompany the status, typically used for errors.
 */
data class Resource<out T>(val status: Status, val data: T?, val message: String?) {

    /**
     * Status of a resource that is provided to the UI.
     *
     * These are usually wrapped around the actual data and used to represent if the data is:
     * - Currently being loaded [LOADING].
     * - Successfully loaded [SUCCESS].
     * - Failed to load due to an error [ERROR].
     */
    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        /**
         * Returns a [Resource] representing a successful result with the provided [data].
         *
         * @param T The type of data.
         * @param data The data to be encapsulated in the [Resource].
         * @return A [Resource] with the SUCCESS status and containing the provided data.
         */
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        /**
         * Returns a [Resource] representing a failed result with an optional [message] and [data].
         *
         * @param T The type of data.
         * @param message The error message to be associated with the [Resource].
         * @param data Optional data that might be available even in the case of an error.
         * @param errorCode The HTTP status code or local error code representing the error.
         * @return A [Resource] with the ERROR status, possibly containing data and always an error message.
         */
        fun <T> error(message: String, data: T? = null, errorCode: Int): Resource<T> {
            return Resource(Status.ERROR, data, message)
        }

        /**
         * Returns a [Resource] representing a loading state, with optional [data].
         *
         * This can be used to notify the UI to show a loading state when the actual result is not yet available.
         *
         * @param T The type of data that is being loaded.
         * @param data Optional data that might already be available while the new data is being loaded.
         * @return A [Resource] with the LOADING status and possibly some initial data.
         */
        fun <T> loading(data: T? = null): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}
