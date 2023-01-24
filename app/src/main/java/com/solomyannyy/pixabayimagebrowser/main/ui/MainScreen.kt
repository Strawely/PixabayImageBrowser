package com.solomyannyy.pixabayimagebrowser.main.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue.Hidden
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.solomyannyy.pixabayimagebrowser.R
import com.solomyannyy.pixabayimagebrowser.commonui.ErrorText
import com.solomyannyy.pixabayimagebrowser.data.PixabayApi
import com.solomyannyy.pixabayimagebrowser.main.model.ImageItemUiModel
import com.solomyannyy.pixabayimagebrowser.main.viewmodel.MainViewModel
import com.solomyannyy.pixabayimagebrowser.ui.BottomSheetContent

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MainScreen(viewModel: MainViewModel, onNavigateDetails: (imageId: Long) -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState(Hidden, confirmStateChange = {
        if (it == Hidden) viewModel.onBottomSheetHidden()
        true
    })
    val bottomSheetContent by viewModel.bottomSheetContentFlow.collectAsState()
    val navigationEvent by viewModel.navigationEventsFlow.collectAsState()

    ModalBottomSheetLayout(
        content = { Content(viewModel) },
        sheetContent = { BottomSheetContent(bottomSheetContent, viewModel) },
        sheetState = modalBottomSheetState,
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    )

    LaunchedEffect(bottomSheetContent) {
        if (bottomSheetContent != null) {
            modalBottomSheetState.show()
        } else {
            modalBottomSheetState.hide()
        }
    }

    LaunchedEffect(navigationEvent) {
        if (navigationEvent != null) {
            onNavigateDetails.invoke(navigationEvent!!.imageId)
            viewModel.onDetailsNavigated()
        }
    }
}


@ExperimentalMaterialApi
@Composable
private fun Content(viewModel: MainViewModel) {
    val itemsFlow by viewModel.itemsFlow.collectAsState()
    val items = itemsFlow.collectAsLazyPagingItems()
    val loadStateRefresh = items.loadState.refresh
    val loadStateAppend = items.loadState.append

    Column {
        QueryTextField(viewModel::updateItems)

        Box(modifier = Modifier.fillMaxSize()) {
            ItemsList(items, viewModel, isLoading = loadStateAppend is LoadState.Loading)

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                if (loadStateRefresh is LoadState.Loading) {
                    CircularProgressIndicator()
                } else if (loadStateRefresh is LoadState.Error) {
                    ErrorText()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun ItemCard(onClick: () -> Unit, Content: @Composable () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = 4.dp,
        onClick = onClick
    ) {
        Content()
    }
}

@Composable
private fun ItemsList(
    items: LazyPagingItems<ImageItemUiModel>,
    viewModel: MainViewModel,
    isLoading: Boolean
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
    ) {
        items(items) { item ->
            item ?: return@items
            ItemCard(onClick = { viewModel.onImageItemClick(item) }) {
                ImageListItem(item)
            }
        }
        if (isLoading) {
            items(PixabayApi.PER_PAGE) {
                ItemCard(onClick = { /* no-op */ }) {
                    LoadingItem()
                }
            }
        }
    }

}

@Composable
private fun QueryTextField(onValueChanged: (String) -> Unit) {
    var value by remember { mutableStateOf(String()) }
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = {
            value = it
            onValueChanged(it)
        },
        placeholder = { Text(stringResource(R.string.main_screen_search_placeholder)) }
    )
}

