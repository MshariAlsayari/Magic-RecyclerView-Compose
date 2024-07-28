package com.android.magic_recyclerview.component.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.Constants.PADDING_BETWEEN_ITEMS
import com.android.magic_recyclerview.Constants.PADDING_HORIZONTAL
import com.android.magic_recyclerview.Constants.PADDING_VERTICAL
import com.android.magic_recyclerview.component.magic_recyclerview.EmptyView
import com.android.magic_recyclerview.component.magic_recyclerview.LoadingView
import kotlinx.coroutines.launch

/***
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * view - the data view holder.
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyview if the list is empty.
 * paddingBetweenItems - padding between items default is 8f.
 * paddingVertical - padding on top and bottom of the whole list default is 0.
 * paddingHorizontal - padding on left and right of the whole list default is 0.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * scrollTo - scroll to item default is 0.
 */
@Composable
fun <T> HorizontalEasyList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    emptyView: (@Composable () -> Unit)? = null,
    dividerView: (@Composable () -> Unit)? = null,
    paddingBetweenItems: Float = PADDING_BETWEEN_ITEMS,
    paddingVertical: Float = PADDING_VERTICAL,
    paddingHorizontal: Float = PADDING_HORIZONTAL,
    isLoading: Boolean = false,
    loadingProgress: (@Composable () -> Unit)? = null,
    scrollTo: Int = 0,
) {

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    val lazyRow: @Composable () -> Unit = {
        LazyRow(
            modifier = modifier,
            state = listState,
            horizontalArrangement = Arrangement.spacedBy(paddingBetweenItems.dp),
            contentPadding = PaddingValues(
                horizontal = paddingHorizontal.dp,
                vertical = paddingVertical.dp
            )
        ) {
            itemsIndexed(list) { index, item ->
                view(item)
                if (index != list.lastIndex) {
                    Surface(modifier = Modifier.padding(start = paddingBetweenItems.dp)) {
                        dividerView?.invoke()
                    }

                }


            }

            coroutineScope.launch {
                listState.animateScrollToItem(scrollTo)
            }

        }

    }

    Box(contentAlignment = Alignment.Center) {
        if (isLoading)
            LoadingView(loadingProgress)
        else {
            if (list.isNotEmpty()) {
                lazyRow()
            } else {
                EmptyView(emptyView)
            }
        }
    }


}