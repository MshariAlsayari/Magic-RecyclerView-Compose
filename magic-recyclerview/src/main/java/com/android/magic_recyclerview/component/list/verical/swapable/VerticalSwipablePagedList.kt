package com.android.magic_recyclerview.component.list.verical.swapable


import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.android.magic_recyclerview.component.magic_recyclerview.UnSelectableItem
import com.android.magic_recyclerview.model.SwipableAction
import com.android.magic_recyclerview.model.SwipableListStyle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

/***
 * modifier: Modifier for styling the list.
 * list: The LazyPagingItems list to display.
 * view: Composable function to display each item.
 * dividerView: (Optional) Composable function to show between items.
 * emptyView: (Optional) Composable function for the empty state.
 * loadingProgress: (Optional) Composable function for loading state.
 * onItemClicked: Callback for item click action.
 * onItemDoubleClicked: Callback for item double click action.
 * startActions: Actions available when swiping from the start.
 * endActions: Actions available when swiping from the end.
 * style: Custom style for the swipable list.
 * isLoading: Boolean indicating loading state.
 * isRefreshing: Boolean indicating refresh state.
 * onRefresh: Callback for refreshing the list.
 * scrollTo: Index to scroll to on initialization.
 */
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun <T : SelectableItemBase> VerticalSwipablePagedList(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<T>?,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    startActions: List<SwipableAction<T>> = listOf(),
    endActions: List<SwipableAction<T>> = listOf(),
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    style: SwipableListStyle = SwipableListStyle.Default,
    scrollTo: Int = 0,
    onRefresh: (() -> Unit)? = null,
) {

    if (startActions.size > 3) {
        throw Exception("the start list action length is > 3 it must be 3 or less")
    }

    if (endActions.size > 3) {
        throw Exception("the end list action length is > 3 it must be 3 or less")
    }

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
                if (list != null && list.itemCount > 0) {
                    LazyPagedList(
                        list = list,
                        view = view,
                        dividerView = dividerView,
                        startActions = startActions,
                        endActions = endActions,
                        onItemClicked = onItemClicked,
                        onItemDoubleClicked = onItemDoubleClicked,
                        style = style,
                        scrollTo = scrollTo,
                    )
                } else {
                    EmptyView(emptyView)
                }
            }


        }
    }


}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
private fun <T : SelectableItemBase> LazyPagedList(
    list: LazyPagingItems<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    startActions: List<SwipableAction<T>> = listOf(),
    endActions: List<SwipableAction<T>> = listOf(),
    style: SwipableListStyle = SwipableListStyle.Default,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    scrollTo: Int = 0,
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState
    ) {
        items(count = list.itemCount,
            key = list.itemKey { it.id },
            contentType = {
                if (list[it] is SelectableItemBase) "selectableItem" else "unSelectableItem"
            }) { index ->
            val item = list[index]
            item?.let {

                if ((item as SelectableItemBase).selectable) {
                    ListItem(
                        item = item,
                        isLast = index == list.itemCount,
                        view = { view(item) },
                        dividerView = dividerView,
                        startActions = startActions,
                        endActions = endActions,
                        style = style,
                        onItemClicked = onItemClicked,
                        onItemDoubleClicked = onItemDoubleClicked,
                    )

                } else {
                    UnSelectableItem(view = view, item = item)
                }

            }

        }


        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }

}