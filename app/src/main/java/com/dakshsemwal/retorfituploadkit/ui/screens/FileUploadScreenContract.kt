package com.dakshsemwal.retorfituploadkit.ui.screens

import com.dakshsemwal.retorfituploadkit.core.ViewEvent
import com.dakshsemwal.retorfituploadkit.core.ViewSideEffect
import com.dakshsemwal.retorfituploadkit.core.ViewState
import com.dakshsemwal.retorfituploadkit.network.FileUploadState
import java.io.File

class FileUploadScreenContract {
    sealed class Event : ViewEvent {
        data class OnFileSelected(val uri: File, val value: Boolean) : Event()
    }

    data class State(
        val fileUploadState: FileUploadState = FileUploadState.Undefined
    ) : ViewState

    object Effect : ViewSideEffect
}