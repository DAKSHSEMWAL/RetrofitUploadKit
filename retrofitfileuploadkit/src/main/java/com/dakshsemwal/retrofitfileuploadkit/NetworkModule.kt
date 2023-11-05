package com.dakshsemwal.retrofitfileuploadkit

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

/**
 * Provides network-related services for the entire application as a singleton.
 *
 * The NetworkModule is responsible for setting up the Retrofit instance and any associated services
 * for network communication. It leverages the Hilt library for dependency injection to supply
 * network-related classes as singletons available for use throughout the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {

    /**
     * Provides a singleton Retrofit instance configured with an OkHttpClient and Gson instance.
     *
     * The Retrofit instance is customized with a base URL, a client that defines read, connect, and write
     * timeouts, a call adapter factory for coroutine support, and a converter factory for JSON serialization
     * and deserialization with Gson.
     *
     * @param baseUrlProvider Provides the base URL for the Retrofit instance.
     * @param okHttpClient The OkHttpClient instance with custom timeouts and logging interceptor.
     * @param gson The Gson instance for JSON processing.
     * @return A Retrofit instance ready for creating service interfaces.
     */
    @Provides
    @Singleton
    fun provideRetrofit(
        baseUrlProvider: BaseUrlProvider,
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlProvider.baseUrl) // Utilizes the base URL from BaseUrlProvider.
            .client(okHttpClient)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides a singleton FileUploadApi service by leveraging the Retrofit instance.
     *
     * This service is used to initiate file upload operations via the FileUploadApi interface.
     * Retrofit dynamically generates the implementation based on the annotations and parameters
     * provided within the interface.
     *
     * @param retrofit The Retrofit instance used to create the service.
     * @return An implementation of FileUploadApi ready for network operations.
     */
    @Provides
    @Singleton
    fun provideFileUploadService(retrofit: Retrofit): FileUploadApi {
        return retrofit.create(FileUploadApi::class.java)
    }

    /**
     * Provides a customized OkHttpClient for uploading operations with increased timeouts and
     * a logging interceptor for debugging purposes.
     *
     * The OkHttpClient is crucial for establishing connections to the server and defines how
     * network requests are sent and responses are received.
     *
     * @param loggingInterceptor An interceptor for logging network request and response information.
     * @return An OkHttpClient with custom configuration.
     */
    @Singleton
    @Provides
    fun provideUploadOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(90, TimeUnit.SECONDS)
            .connectTimeout(90, TimeUnit.SECONDS)
            .writeTimeout(90, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    /**
     * Provides a Gson instance for serializing and deserializing objects to and from JSON.
     *
     * This Gson instance is configured to serialize null values in the JSON output.
     *
     * @return A Gson instance for JSON processing.
     */
    @Singleton
    @Provides
    fun providesGson(): Gson = GsonBuilder().serializeNulls().create()

    /**
     * Provides an HttpLoggingInterceptor with a body logging level.
     *
     * An interceptor that logs the full request and response body of all HTTP requests.
     * This interceptor is intended for development purposes to provide insight into the actual
     * network traffic being transmitted and received. It logs the body of both HTTP requests and
     * responses to the logcat at the BODY level which includes full contents of the HTTP messages.
     *
     * Note: Logging at the BODY level can lead to sensitive data being logged and large amounts of
     * logged data. It should be used with caution in production environments.
     *
     * @return An instance of HttpLoggingInterceptor with the log level set to BODY.
     */
    @Singleton
    @Provides
    fun providesHttpInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

}
