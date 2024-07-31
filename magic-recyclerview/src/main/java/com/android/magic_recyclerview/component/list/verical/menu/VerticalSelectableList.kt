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
import com.android.magic_recyclerview.model.MenuAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.SwipableListStyle
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

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
    actions: List<MenuAction<T>> = listOf(),
    isMultiSelectionMode: Boolean = false,
    style: SelectableListStyle = SelectableListStyle.Default,
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
                        )

                        AnimatedVisibility(
                            modifier = Modifier.align(Alignment.BottomCenter),
                            visible = isMultiSelectionMode && actions.isNotEmpty(),
                            enter = scaleIn(),
                            exit = scaleOut(),
                        ) {
                            ActionContainer(
                                selectedItem = list.filter { (it as SelectableItemBase).isSelected },
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
private fun <T> SelectableLazyList(
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
) {


    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()


    LazyColumn(
        state = listState
    ) {
        itemsIndexed(list) { index, item ->

            if((item as SelectableItemBase).isSelectable){
                Box(modifier = Modifier.combinedClickable (
                    onClick = {
                        onItemClicked(item,index)
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

