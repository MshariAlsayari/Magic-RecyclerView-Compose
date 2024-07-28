package com.android.magic_recyclerview.component.list.verical.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.magic_recyclerview.Selection

@Composable
internal fun <T> SelectableListItem(
    item: T,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    isLastItem:Boolean,
    isMultiSelectionMode: Boolean = false,
){

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            AnimatedVisibility(
                visible = isMultiSelectionMode,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                Selection(
                    backgroundColor = Color.Blue,
                    isSelected = (item as SelectableItemBase).isSelected
                )
            }
            view(item)
        }

        if (!isLastItem && dividerView != null) {
            dividerView()
        }


    }

}