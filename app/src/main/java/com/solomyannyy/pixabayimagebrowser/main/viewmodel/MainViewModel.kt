package com.solomyannyy.pixabayimagebrowser.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.solomyannyy.pixabayimagebrowser.main.model.ImageItemUiModel
import com.solomyannyy.pixabayimagebrowser.main.interactor.MainInteractor
import com.solomyannyy.pixabayimagebrowser.data.DetailsNavigationEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainInteractor) : ViewModel() {
    private var updateJob: Job? = null

    private val _bottomSheetContentFlow = MutableStateFlow<ImageItemUiModel?>(null)
    val bottomSheetContentFlow = _bottomSheetContentFlow.asStateFlow()

    private val _itemsFlow = MutableStateFlow(flowOf(PagingData.empty<ImageItemUiModel>()))
    val itemsFlow = _itemsFlow.asStateFlow()

    private val _navigationEventsFlow = MutableStateFlow<DetailsNavigationEvent?>(null)
    val navigationEventsFlow = _navigationEventsFlow.asStateFlow()

    init {
        updateItems(DEFAULT_QUERY)
    }

    fun updateItems(query: String) {
        updateJob?.cancel()
        updateJob = viewModelScope.launch {
            delay(DEBOUNCE_DELAY)
            _itemsFlow.value = repository.getImages(query).cachedIn(viewModelScope)
        }
    }

    fun onImageItemClick(imageItem: ImageItemUiModel) {
        _bottomSheetContentFlow.value = imageItem
    }

    fun onBottomSheetHidden() {
        _bottomSheetContentFlow.value = null
    }

    fun onDetailsDialogButtonClick(imageItem: ImageItemUiModel) {
        _bottomSheetContentFlow.value = null
        _navigationEventsFlow.value = DetailsNavigationEvent(imageItem.id)
    }

    fun onDetailsNavigated() {
        _navigationEventsFlow.value = null
    }

    private companion object {
        const val DEBOUNCE_DELAY = 300L
        const val DEFAULT_QUERY = "fruits"
    }
}