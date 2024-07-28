package com.android.magic_recyclerview.component.list.verical.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.magic_recyclerview.DropdownMenu
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.android.magic_recyclerview.model.Action
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T:SelectableItemBase> SelectableVerticalList(
    modifier: Modifier = Modifier,
    list: List<T>,
    selectMenuItem: T,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemLongClicked: (item: T, position: Int) -> Unit,
    onDismissMenu: () -> Unit,
    actions: List<Action<T>> = listOf(),
    actionBackgroundRadiusCorner: Float = 0f,
    isMultiSelectionMode: Boolean = false,
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
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {

                        SelectableLazyList(
                            list = list,
                            selectMenuItem =selectMenuItem,
                            view = view,
                            dividerView = dividerView,
                            actions = actions,
                            isMultiSelectionMode = isMultiSelectionMode,
                            scrollTo = scrollTo,
                            onItemClicked = onItemClicked,
                            onItemLongClicked = onItemLongClicked,
                            onDismissMenu = onDismissMenu
                        )

                        AnimatedVisibility(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            visible = isMultiSelectionMode && actions.isNotEmpty(),
                            enter = scaleIn(),
                            exit = scaleOut(),
                        ) {
                            ActionContainer(
                                selectedItem = list.filter { (it as SelectableItemBase).isSelected },
                                actions = actions
                            )
                        }

                    }

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
    selectMenuItem: T,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    isMultiSelectionMode: Boolean = false,
    scrollTo: Int = 0,
    actions: List<Action<T>> = listOf(),
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemLongClicked: (item: T, position: Int) -> Unit,
    onDismissMenu: () -> Unit,
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val isActionClicked = rememberSaveable { mutableStateOf(false) }


    LazyColumn(
        state = listState
    ) {
        itemsIndexed(list) { index, item ->


            Box(modifier = Modifier.combinedClickable (
                onClick = {
                    onItemClicked(item,index)
                },
                onLongClick = {
                    onItemLongClicked(item,index)
                })) {

                SelectableListItem(
                    item = item,
                    view=view,
                    dividerView=dividerView,
                    isLastItem = index == list.lastIndex,
                    isMultiSelectionMode=isMultiSelectionMode
                )
            }
        }

        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }
}

