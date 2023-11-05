package com.dakshsemwal.retrofitfileuploadkit

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * A module for providing the FileUploadManager instance.
 *
 * This module uses the Hilt library to set up dependency injection, which makes it easier
 * to manage the creation and provisioning of FileUploadManager instances across the app.
 * By declaring the provider within an object annotated with @Module and @InstallIn,
 * we ensure that Hilt treats this provider as a part of the dependency graph.
 *
 * @InstallIn(SingletonComponent::class) specifies that the provided instances will
 * behave as singletons within the scope of the SingletonComponent, which typically
 * lives for the duration of the application's lifecycle.
 */
@Module
@InstallIn(SingletonComponent::class)
object FileUploadManagerProvider {

    /**
     * Provides a singleton instance of FileUploadManager.
     *
     * The function is annotated with @Provides and @Singleton to indicate that it is a
     * provider function and should only instantiate a single instance of FileUploadManager
     * to be used wherever FileUploadManager is injected within the SingletonComponent scope.
     *
     * The FileUploadManagerImpl is created by passing the FileUploadApi instance which is required
     * by the implementation. This allows for the separation of concerns, where the FileUploadManager
     * implementation details are abstracted away from the consumers.
     *
     * @param fileUploadApi An instance of FileUploadApi to be used by the FileUploadManager for
     * handling file upload operations.
     * @return An instance of FileUploadManager that can be used for uploading files.
     */
    @Provides
    @Singleton
    fun provideFileUploadManager(fileUploadApi: FileUploadApi): FileUploadManager =
        FileUploadManagerImpl(fileUploadApi = fileUploadApi)
}
