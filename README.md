# RetrofitUploadKit
RetrofitUploadKit is a convenient, streamlined Kotlin-based library that wraps around the Retrofit networking library, specifically tailored for simplifying file upload tasks. This kit abstracts the complex setup and configuration typically associated with making multipart HTTP requests for file uploads

# RetrofitFileUploadKit

RetrofitFileUploadKit is a Kotlin-based Android library that simplifies the file upload process using Retrofit. It offers a flexible and easy-to-use DSL for uploading files as multipart or as byte arrays, with support for single or multiple file uploads.

## Features

- **Easy Integration**: Set up file uploading with just a few lines of code.
- **Flexible Upload Options**: Choose between multipart or byte array upload methods.
- **Support for Multiple File Uploads**: Easily upload multiple files at once.
- **DSL Approach**: Configure your file upload using a clean and concise Kotlin DSL.
- **Hilt Support**: Leverages Hilt for dependency injection, simplifying the setup process.

## Installation

Add the following to your `build.gradle` file:

```groovy
dependencies {
    implementation 'io.github.dakshsemwal:retrofitfileuploadkit:1.0.1'
}
```
## Usage
First, make sure you've initialized Hilt in your application. Then, to use RetrofitFileUploadKit, follow these steps:

## Single File Upload

How to provide your BASE_URL

```kotlin
    @Provides
    @Singleton
    fun provideBaseUrlProvider(): BaseUrlProvider = object : BaseUrlProvider {
        override val baseUrl: String
            get() = "https://your.example.com" // Replace with the actual base URL
    }
```

```kotlin
class FileUploadDataSourceImpl @Inject constructor(val fileUploadManager: FileUploadManager) :
    FileUploadDataSource {
    override suspend fun uploadFile(
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean
    ): Resource<YOUR_DATA_CLASS> {
        val type = object : TypeToken<YOUR_DATA_CLASS>() {}.type
        return fileUploadManager.uploadFile(
            type = type,
            url = url,
            file = file,
            name = name,
            isMultipart = isMultipart
        )
    }
}
```
## Multiple File Upload
```kotlin
// Define your files and other parameters
val files: List<File> = listOf(file1, file2, file3)

// Upload files
files.forEachIndexed { index, file ->
    fileUploadManager.uploadFile(
        url = "/projects/image",
        file = file,
        name = "file", // Optional: customize the form name
        isMultipart = true // or false for byte array
    )
}
```




