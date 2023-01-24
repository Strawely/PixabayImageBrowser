package com.solomyannyy.pixabayimagebrowser.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.solomyannyy.pixabayimagebrowser.details.model.ImageDetailsUiModel
import com.solomyannyy.pixabayimagebrowser.details.repository.DetailsRepository
import com.solomyannyy.pixabayimagebrowser.utils.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: DetailsRepository) : ViewModel() {
    private val _contentFlow = MutableStateFlow<ImageDetailsUiModel?>(null)
    val contentFlow = _contentFlow.asStateFlow()

    private val _detailedInfoIsVisibleFlow = MutableStateFlow(true)
    val detailedInfoIsVisibleFlow = _detailedInfoIsVisibleFlow.asStateFlow()

    private val _isLoadingFlow = MutableStateFlow(false)
    val isLoadingFlow = _isLoadingFlow.asStateFlow()

    private val _isErrorFlow = MutableStateFlow(false)
    val isErrorFlow = _isErrorFlow.asStateFlow()

    fun updateContent(imageId: Long) {
        repository.getImageDetails(imageId).onEach { data ->
            _isLoadingFlow.value = data is DataState.Loading
            _isErrorFlow.value = data is DataState.Error
            if (data is DataState.Success) {
                _contentFlow.value = data.data
            } else {
                _contentFlow.value = null
            }

        }.launchIn(viewModelScope)
    }

    fun onImageClick() {
        _detailedInfoIsVisibleFlow.value = !detailedInfoIsVisibleFlow.value
    }
}