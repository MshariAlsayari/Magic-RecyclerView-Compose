package com.android.magic_recyclerview.component.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.Constants.COLUMN_COUNT
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch





/***
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * view - the data view holder.
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyview if the list is empty.
 * actionBackgroundHeight - height of the actions background.
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * scrollTo - scroll to item default is 0.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * columnCount - number of columns default is 2
 */
@ExperimentalFoundationApi
@Composable
fun <T> GridEasyList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    emptyView: @Composable (() -> Unit)? = null,
    columnCount: Int = COLUMN_COUNT,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    isLoading: Boolean = false,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked               : (item: T, position: Int) -> Unit,
    scrollTo: Int = 0,
) {


            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                swipeEnabled = onRefresh != null,
                onRefresh = { onRefresh?.invoke() },
            ) {
                Box(contentAlignment = Alignment.Center) {

                    if (isLoading)
                        LoadingView(loadingProgress)
                    else{
                        if (list.isNotEmpty()){
                            LazyGridList(
                                modifier = modifier,
                                list = list,
                                view = view,
                                columnCount = columnCount,
                                onItemClicked = onItemClicked,
                                scrollTo = scrollTo
                            )

                        }else
                            EmptyView(emptyView)
                    }
                }

            }





}

@Composable
fun <T> LazyGridList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    columnCount: Int = COLUMN_COUNT,
    onItemClicked: (item: T, position: Int) -> Unit,
    scrollTo: Int = 0,
) {
    val listState = rememberLazyGridState()
    val coroutineScope = rememberCoroutineScope()

    LazyVerticalGrid(
        modifier = modifier,
        state = listState,
        columns = GridCells.Fixed(columnCount),
    ) {
        itemsIndexed(list) { index, item ->
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = modifier.clickable {
                        onItemClicked(item, index)
                    },
                    contentAlignment = Alignment.Center,
                ) {
                    view(item)
                }

            }


        }

        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }

}




