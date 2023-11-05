package com.dakshsemwal.retorfituploadkit.di

import com.dakshsemwal.retorfituploadkit.network.FileUploadDataSource
import com.dakshsemwal.retorfituploadkit.network.FileUploadDataSourceImpl
import com.dakshsemwal.retorfituploadkit.network.FileUploadService
import com.dakshsemwal.retorfituploadkit.network.FileUploadServiceImpl
import com.dakshsemwal.retrofitfileuploadkit.BaseUrlProvider
import com.dakshsemwal.retrofitfileuploadkit.FileUploadManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideBaseUrlProvider(): BaseUrlProvider = object : BaseUrlProvider {
        override val baseUrl: String
            get() = "http://192.168.0.206:8080" // Replace with the actual base URL
    }

    @Singleton
    @Provides
    fun provideFileUploadService(
        fileUploadDataSource: FileUploadDataSource
    ): FileUploadService =
        FileUploadServiceImpl(
            fileUploadDataSource = fileUploadDataSource
        )

    @Singleton
    @Provides
    fun provideFileUploadSource(
        fileUploadManager: FileUploadManager
    ): FileUploadDataSource = FileUploadDataSourceImpl(
        fileUploadManager = fileUploadManager
    )
}