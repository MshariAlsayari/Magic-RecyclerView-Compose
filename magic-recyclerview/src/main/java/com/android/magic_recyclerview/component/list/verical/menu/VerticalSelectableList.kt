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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import com.android.magic_recyclerview.component.magic_recyclerview.UnSelectableItem
import com.android.magic_recyclerview.model.SelectableAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch
/**
 * @modifier: A Modifier for the composable.
 * @list: The list of items to display.
 * @selectedItemsList: The list of currently selected items.
 * @view: A composable function to display each item.
 * @dividerView: A composable function to display between items.
 * @selectedSelectionView: A composable function to display for selection view .
 * @unselectedSelectionView: A composable function to display for un-selection view.
 * @emptyView: A composable function to display when the list is empty.
 * @loadingProgress: A composable function to display while loading.
 * @onItemClicked: A callback for item click events.
 * @onItemLongClicked: A callback for item long-click events.
 * @onItemDoubleClicked: A callback for item double-click events.
 * @actions: A list of actions that can be performed on items.
 * @isMultiSelectionMode: A flag to indicate if multi-selection mode is enabled.
 * @style: The style for the selectable list.
 * @scrollTo: The index to scroll to.
 * @isLoading: A flag to indicate if the list is loading.
 * @isRefreshing: A flag to indicate if the list is refreshing.
 * @onRefresh: A callback to trigger refresh.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T:SelectableItemBase> VerticalSelectableList(
    modifier: Modifier = Modifier,
    list: List<T>,
    selectedItemsList: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    selectedSelectionView: (@Composable () -> Unit)? = null,
    unselectedSelectionView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemLongClicked: (item: T, position: Int) -> Unit,
    onItemDoubleClicked: (item: T, position: Int) -> Unit,
    actions: List<SelectableAction<T>> = listOf(),
    isMultiSelectionMode: Boolean = false,
    style: SelectableListStyle = SelectableListStyle.Default,
    scrollTo: Int = 0,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
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
                            selectedItemsList=selectedItemsList,
                            view = view,
                            dividerView = dividerView,
                            selectedSelectionView=selectedSelectionView,
                            unselectedSelectionView = unselectedSelectionView,
                            isMultiSelectionMode = isMultiSelectionMode,
                            style = style,
                            scrollTo = scrollTo,
                            onItemClicked = onItemClicked,
                            onItemLongClicked = onItemLongClicked,
                            onItemDoubleClicked = onItemDoubleClicked
                        )

                        AnimatedVisibility(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            visible = isMultiSelectionMode && actions.isNotEmpty(),
                            enter = scaleIn(),
                            exit = scaleOut(),
                        ) {
                            ActionContainer(
                                selectedItem = selectedItemsList,
                                style = style,
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
private fun <T:SelectableItemBase> SelectableLazyList(
    list: List<T>,
    selectedItemsList: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    selectedSelectionView: (@Composable () -> Unit)? = null,
    unselectedSelectionView: (@Composable () -> Unit)? = null,
    isMultiSelectionMode: Boolean = false,
    style: SelectableListStyle = SelectableListStyle.Default,
    scrollTo: Int = 0,
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemLongClicked: (item: T, position: Int) -> Unit,
    onItemDoubleClicked: (item: T, position: Int) -> Unit,
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LazyColumn(
        state = listState
    ) {
        itemsIndexed(list) { index, item ->


            if((item as SelectableItemBase).selectable){
                Box(modifier = Modifier.combinedClickable (
                    onClick = {
                        onItemClicked(item,index)
                    },
                    onDoubleClick = {
                        onItemDoubleClicked(item,index)
                    },
                    onLongClick = {
                        onItemLongClicked(item,index)
                    })) {

                    SelectableListItem(
                        item = item,
                        isSelected = selectedItemsList.contains(item),
                        view=view,
                        selectedSelectionView=selectedSelectionView,
                        unselectedSelectionView = unselectedSelectionView,
                        style = style,
                        dividerView=dividerView,
                        isLastItem = index == list.lastIndex,
                        isMultiSelectionMode=isMultiSelectionMode
                    )
                }
            }else{
                UnSelectableItem(view = view, item = item)
            }

        }

        coroutineScope.launch {
            listState.animateScrollToItem(scrollTo)
        }
    }
}

