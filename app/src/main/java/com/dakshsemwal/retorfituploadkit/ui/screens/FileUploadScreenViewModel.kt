package com.dakshsemwal.retorfituploadkit.ui.screens

import androidx.lifecycle.viewModelScope
import com.dakshsemwal.retorfituploadkit.core.ContractViewModel
import com.dakshsemwal.retorfituploadkit.network.FileUploadService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FileUploadScreenViewModel @Inject constructor(val fileUploadService: FileUploadService) :
    ContractViewModel<FileUploadScreenContract.Event, FileUploadScreenContract.State, FileUploadScreenContract.Effect>() {
    override fun setInitialState(): FileUploadScreenContract.State =
        FileUploadScreenContract.State()

    override fun handleEvents(event: FileUploadScreenContract.Event) {
        when (event) {
            is FileUploadScreenContract.Event.OnFileSelected -> {
                viewModelScope.launch {
                    fileUploadService.uploadFile(file = event.uri, url = "upload").collectLatest {
                        setState {
                            copy(fileUploadState = it)
                        }
                    }
                }
            }
        }
    }
}