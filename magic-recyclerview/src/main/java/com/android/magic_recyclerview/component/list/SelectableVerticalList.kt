package com.android.magic_recyclerview.component.list

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.android.magic_recyclerview.component.action_row.ActionRowType
import com.android.magic_recyclerview.component.action_row.ActionsRow
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.android.magic_recyclerview.component.magic_recyclerview.SelectorView
import com.android.magic_recyclerview.component.swippable_item.SwappableItem
import com.android.magic_recyclerview.component.swippable_item.SwipeDirection
import com.android.magic_recyclerview.model.Action
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T> SelectableVerticalList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemDoubleClicked: (item: T, position: Int) -> Unit,
    actions: List<Action<T>> = listOf(),
    actionBackgroundRadiusCorner: Float = 0f,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    scrollTo: Int = 0,
) {


    SwipeRefresh(
        modifier = Modifier.fillMaxSize(),
        state = rememberSwipeRefreshState(isRefreshing),
        swipeEnabled = onRefresh != null,
        onRefresh = { onRefresh?.invoke() },
    ) {

        Box(
            modifier = modifier,
            contentAlignment = Alignment.Center
        ) {

            if (isLoading)
                LoadingView(loadingProgress)
            else {
                if (list.isNotEmpty()) {
                    SelectableLazyList(
                        list = list,
                        view = view,
                        dividerView = dividerView,
                        actions = actions,
                        scrollTo = scrollTo,
                    )
                } else {
                    EmptyView(emptyView)
                }
            }


        }
    }

}

@OptIn(ExperimentalFoundationApi::class)
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun <T> SelectableLazyList(
    list: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    actions: List<Action<T>> = listOf(),
    scrollTo: Int = 0,
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isActionClicked = rememberSaveable { mutableStateOf(false) }
    val isMultiSelection = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isMultiSelection){

    }

    LazyColumn(
        state = listState
    ) {
        itemsIndexed(list) { index, item ->
            Column(
                modifier = Modifier.combinedClickable (
                    onClick = {

                    },
                    onLongClick = {
                        isMultiSelection.value = !isMultiSelection.value
                    },


                ),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ){
                    if(isMultiSelection.value){
                        SelectorView()
                    }
                    view(item)
                }

                if (index != list.lastIndex && dividerView != null) {
                    Box(
                        contentAlignment = Alignment.Center
                    ) {
                        dividerView()
                    }

                }


            }


        }

        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }
}
