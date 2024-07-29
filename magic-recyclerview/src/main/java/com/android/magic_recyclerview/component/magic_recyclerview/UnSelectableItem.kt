package com.android.magic_recyclerview.component.magic_recyclerview

import androidx.compose.runtime.Composable
import com.android.magic_recyclerview.SelectableItemBase

@Composable
internal fun <T> UnSelectableItem(
    view: @Composable (T) -> Unit,
    item: T) {
    view(item)
}