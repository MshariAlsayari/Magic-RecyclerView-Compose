package com.android.magic_recyclerview.component.list.verical.menu

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.SelectableItemBase
import com.android.magic_recyclerview.component.magic_recyclerview.Selection
import com.android.magic_recyclerview.model.SelectableListStyle

@Composable
internal fun <T> SelectableListItem(
    item: T,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    selectedSelectionView: (@Composable () -> Unit)? = null,
    unselectedSelectionView: (@Composable () -> Unit)? = null,
    style: SelectableListStyle = SelectableListStyle.Default,
    isLastItem:Boolean,
    isMultiSelectionMode: Boolean = false,
){
    val startPadding = if (isMultiSelectionMode) 16.dp else 0.dp

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ){
            AnimatedVisibility(
                modifier = Modifier.padding(horizontal = startPadding),
                visible = isMultiSelectionMode,
                enter = scaleIn(),
                exit = scaleOut(),
            ) {
                if(selectedSelectionView!= null  && unselectedSelectionView != null){
                    if((item as SelectableItemBase).isSelected){
                        selectedSelectionView()
                    }else{
                        unselectedSelectionView()
                    }
                }else{
                    Selection(
                        style = style,
                        isSelected = (item as SelectableItemBase).isSelected
                    )
                }

            }
            view(item)
        }

        if (!isLastItem && dividerView != null) {
            dividerView()
        }


    }

}