package com.dakshsemwal.retorfituploadkit.ui.screens

import android.content.res.Configuration
import android.net.Uri
import android.webkit.MimeTypeMap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes.isVideo
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.dakshsemwal.retorfituploadkit.network.FileUploadState
import com.dakshsemwal.retorfituploadkit.ui.theme.RetrofitUploadKitTheme
import com.dakshsemwal.retorfituploadkit.util.inputStreamToFile
import com.dakshsemwal.retrofitfileuploadkit.R
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.File

@OptIn(UnstableApi::class)
@Composable
fun FileUploadScreen() {
    val viewModel = hiltViewModel<FileUploadScreenViewModel>()
    val state = viewModel.viewState.value
    val event: (FileUploadScreenContract.Event) -> Unit = { viewModel.setEvent(it) }
    var photoUri: Uri? by remember { mutableStateOf(null) }
    var currentProgress by remember { mutableFloatStateOf(0f) }
    val scope = rememberCoroutineScope()
    val launchTimer = remember { mutableStateOf(false) }
    val isVideo = remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    val mediaLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            photoUri = uri
            if (uri != null) {
                val contentResolver = context.contentResolver
                val mimeType = contentResolver.getType(uri)
                isVideo.value = isVideo(mimeType)
                val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType)

                // Get an input stream from the content resolver using the Uri
                val inputStream = contentResolver.openInputStream(uri)

                // Define a file in your app's private storage with the appropriate extension
                val directory = context.filesDir // or getExternalFilesDir for external storage
                val fileName =
                    "picked_media_${System.currentTimeMillis()}${extension?.let { ".$it" } ?: ""}"
                val file = File(directory, fileName.lowercase())

                // Use a coroutine to perform file I/O on a background thread
                scope.launch {
                    inputStream?.let { stream ->
                        inputStreamToFile(stream, file)
                        // Now that you have a File object, you can pass it along
                        event(FileUploadScreenContract.Event.OnFileSelected(file,isVideo.value))
                    }
                }
            }
        }


    LaunchedEffect(key1 = launchTimer.value) {
        if (launchTimer.value) {
            scope.launch {
                while (currentProgress < 1f) {
                    // Increment the progress here
                    currentProgress += 0.01f // Adjust the increment value as necessary
                    delay(100L) // Adjust the delay to control the speed of the progress
                }
            }
        } else {
            currentProgress = 0f
        }
    }
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        item {
            Spacer(modifier = Modifier.height(10.dp))
            if (photoUri != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .padding(horizontal = 16.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        if (isVideo.value) {
                            photoUri?.let {
                                VideoPlayer(videoUri = it) // Implement a Composable that uses Android's VideoView or a third-party library
                            }
                        } else {
                            GlideImage(
                                imageModel = { photoUri }, // The URI you want to load the image from.
                                // fadeIn is true by default
                                modifier = Modifier
                                    .fillMaxSize()
                                    .height(200.dp)
                                    .clip(RoundedCornerShape(4.dp))
                            )
                        }
                        IconButton(
                            onClick = { photoUri = null },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(5.dp)
                        ) {
                            Icon(Icons.Default.Close, contentDescription = "clear")
                        }
                    }
                    Spacer(modifier = Modifier.size(3.dp))
                    when (state.fileUploadState) {
                        is FileUploadState.Uploading -> {
                            launchTimer.value = true
                            LinearProgressIndicator(
                                progress = { currentProgress },
                                modifier = Modifier
                                    .fillMaxWidth(),
                            )
                        }

                        is FileUploadState.Error -> {
                            launchTimer.value = false
                            Box(modifier = Modifier.fillMaxSize()) {
                                Text(
                                    text = state.fileUploadState.error,
                                )
                            }
                        }

                        is FileUploadState.Uploaded -> {
                            launchTimer.value = false
                            Box(
                                modifier = Modifier
                                    .padding(5.dp)
                                    .fillMaxWidth(),
                            ) {
                                Text(
                                    text = "File Uploaded"
                                )
                            }
                        }

                        is FileUploadState.Undefined ->
                            launchTimer.value = false

                        else -> Unit
                    }
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(4.dp))
                        .height(200.dp)
                        .padding(horizontal = 16.dp)
                        .border(
                            width = 1.dp,
                            color = Color.Gray,
                            shape = RoundedCornerShape(4.dp)
                        )
                        .clickable {
                            mediaLauncher.launch(
                                PickVisualMediaRequest(
                                    //Here we request only photos. Change this to .ImageAndVideo if
                                    //you want videos too.
                                    //Or use .VideoOnly if you only want videos.
                                    mediaType = ActivityResultContracts.PickVisualMedia.ImageAndVideo
                                )
                            )
                        }
                ) {
                    Column(
                        modifier = Modifier
                            .align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Image(
                            painterResource(id = R.drawable.ic_image_upload),
                            contentDescription = "file_upload"
                        )
                        Spacer(modifier = Modifier.size(10.dp))
                        Text(text = "Upload Logo")
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

        }
    }
}

@Composable
fun VideoPlayer(videoUri: Uri) {
    val context = LocalContext.current
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(videoUri)
            setMediaItem(mediaItem)
            prepare()
        }
    }

    DisposableEffect(key1 = exoPlayer) {
        onDispose {
            exoPlayer.release()
        }
    }

    AndroidView(
        factory = { ctx ->
            PlayerView(ctx).apply {
                player = exoPlayer
                useController = true
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun CreateOrganizationFormPreviewLight() {
    RetrofitUploadKitTheme { FileUploadScreen() }
}

@Preview(showBackground = true, showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun CreateOrganizationFormPreviewDark() {
    RetrofitUploadKitTheme { FileUploadScreen() }
}
