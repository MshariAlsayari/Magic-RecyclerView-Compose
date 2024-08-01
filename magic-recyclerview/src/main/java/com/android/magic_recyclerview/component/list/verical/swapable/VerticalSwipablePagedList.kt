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
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * view - the data view holder.
 * onItemClicked - callback when the swappable item's been clicked
 * onItemCollapsed - callback when the swappable item's been collapsed
 * onItemExpanded - callback when the swappable item's been expanded
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyView if the list is empty.
 * startActions - list of actions if it is empty no swipe .
 * endActions - list of actions if it is empty no swipe .
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * scrollTo - scroll to item default is 0.
 * style - style is class to add style on background of actions container
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