# RetrofitUploadKit
![Maven Central](https://img.shields.io/maven-central/v/io.github.dakshsemwal/retrofitfileuploadkit.svg) [![GitHub release (latest by date)](https://img.shields.io/github/v/release/DAKSHSEMWAL/RetrofitUploadKit.svg?sort=semver)](https://github.com/DAKSHSEMWAL/RetrofitUploadKit/releases)



RetrofitUploadKit is a Kotlin-based library that simplifies the file upload process using Retrofit.
This library provides a flexible DSL for uploading files as multipart or byte arrays and is designed
to support both single and multiple file uploads with minimal configuration.

## Features

- **Easy Integration**: Quickly set up file uploading with just a few lines of code.
- **Flexible Upload Options**: Choose between multipart or byte array upload methods.
- **Multiple File Uploads**: Conveniently upload multiple files in one go.
- **Kotlin DSL**: Use a clean and concise Kotlin DSL to configure your file upload process.
- **Hilt Support**: Built with Hilt support to streamline the setup process through dependency
  injection.

## Installation

To include RetrofitUploadKit in your project, add the following dependency to your `build.gradle`
file:

```groovy
dependencies {
    implementation 'io.github.dakshsemwal:retrofitfileuploadkit:1.0.2'
}
```

## Usage

Ensure you have Hilt initialized in your application. Then integrate RetrofitFileUploadKit as
follows:

## Providing Base URL

```kotlin
    @Provides
    @Singleton
    fun provideBaseUrlProvider(): BaseUrlProvider = object : BaseUrlProvider { 
        override val baseUrl: String get() = "https://your.api.com" // Replace with your base URL
    }
```

## Setting Up Dependencies
```kotlin
    @Singleton
    @Provides
    fun provideFileUploadSource(fileUploadManager: FileUploadManager): FileUploadDataSource =
        FileUploadDataSourceImpl(fileUploadManager)
```

## Injecting the Manager

## Single File Upload
```kotlin
class FileUploadDataSourceImpl @Inject constructor(
    private val fileUploadManager: FileUploadManager
) : FileUploadDataSource {

    override suspend fun uploadFile(
        url: String,
        file: File,
        name: String,
        isMultipart: Boolean
    ): Resource<YOUR_DATA_CLASS> {
        val type = object : TypeToken<YOUR_DATA_CLASS>() {}.type
        return fileUploadManager.uploadFile(
            type,
            url,
            file,
            name,
            isMultipart
        )
    }
}
```
## Multiple File Upload
```kotlin
// List of files to be uploaded
val files: List<File> = listOf(file1, file2, file3)

// Iterate and upload each file
files.forEach { file ->
    fileUploadManager.uploadFile(
        url = "/upload",
        file = file,
        name = "file",
        isMultipart = true // Set false for byte array uploads
    )
}
```

## Customization
For advanced customization, modify the DSL configuration or extend the library classes with your implementations.

## Contributing
Contributions are welcome! Feel free to submit pull requests or open issues for bugs, feature requests, or enhancements.

## License
RetrofitUploadKit is made available under the MIT License. For more details, see the LICENSE file in the repository.

**Remember to replace placeholder text (like `YOUR_DATA_CLASS`) with the actual content or provide clear instructions on what users should replace it with based on their use case.**


## SERVER
if You don't have a server for testing you can try cloning my other repo which simulates the Server
https://github.com/DAKSHSEMWAL/file-upload-server
[FILE_UPLOAD_SERVER](https://github.com/DAKSHSEMWAL/file-upload-server#readme)
