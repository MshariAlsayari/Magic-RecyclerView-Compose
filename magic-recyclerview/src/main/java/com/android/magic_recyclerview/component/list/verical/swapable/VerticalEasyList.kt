package com.android.magic_recyclerview.component.list.verical.swapable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.action_row.ActionRowType
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.android.magic_recyclerview.component.magic_recyclerview.UnSelectableItem
import com.android.magic_recyclerview.model.Action
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
 * emptyView - (optional) emptyview if the list is empty.
 * startActions - list of actions if it is empty no swipe .
 * endActions - list of actions if it is empty no swipe .
 * startActionBackgroundColor - background color of the list of the start actions.
 * endActionBackgroundColor - background color of the list of the end actions.
 * actionBackgroundRadiusCorner - radius corner for both start background and end background actions.
 * actionBackgroundHeight - height of the actions background.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * paddingBetweenItems - padding between items default is 8f.
 * paddingVertical - padding on top and bottom of the whole list default is 0.
 * paddingHorizontal - padding on left and right of the whole list default is 0.
 * scrollTo - scroll to item default is 0.
 */

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun <T> VerticalEasyList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    onItemCollapsed: ((item: T) -> Unit)? = null,
    onItemExpanded: ((item: T) -> Unit)? = null,
    startActions: List<Action<T>> = listOf(),
    endActions: List<Action<T>> = listOf(),
    actionBackgroundRadiusCorner: Float = 0f,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    scrollTo: Int = 0,
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
                if (list.isNotEmpty()) {
                    LazyList(
                        list = list,
                        view = view,
                        dividerView = dividerView,
                        startActions = startActions,
                        endActions = endActions,
                        onItemClicked = onItemClicked,
                        onItemDoubleClicked = onItemDoubleClicked,
                        onItemCollapsed = onItemCollapsed,
                        onItemExpanded = onItemExpanded,
                        actionBackgroundRadiusCorner = actionBackgroundRadiusCorner,
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
fun <T> LazyList(
    list: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    startActions: List<Action<T>> = listOf(),
    endActions: List<Action<T>> = listOf(),
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    onItemCollapsed: ((item: T) -> Unit)? = null,
    onItemExpanded: ((item: T) -> Unit)? = null,
    actionBackgroundRadiusCorner: Float = 0f,
    scrollTo: Int = 0,
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        state = listState
    ) {
        itemsIndexed(list) { index, item ->
            if ((item as SelectableItemBase).isSelectable) {
                ListItem(
                    item = item,
                    isLast = index == list.lastIndex,
                    view = { view(item) },
                    dividerView = dividerView,
                    startActions = startActions,
                    endActions = endActions,
                    actionBackgroundRadiusCorner = actionBackgroundRadiusCorner,
                    onItemClicked = onItemClicked,
                    onItemDoubleClicked = onItemDoubleClicked,
                    onItemCollapsed = onItemCollapsed,
                    onItemExpanded = onItemExpanded
                )
            } else {
                UnSelectableItem(view = view, item = item)
            }

        }


        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }

}