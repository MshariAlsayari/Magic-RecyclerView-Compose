package com.android.magic_recyclerview.component.list.verical.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.R
import com.android.magic_recyclerview.component.action_row.MenuActionItem
import com.android.magic_recyclerview.component.magic_recyclerview.DropdownMenu
import com.android.magic_recyclerview.model.SelectableAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.getText

@Composable
internal fun <T> ActionContainer(
    modifier: Modifier = Modifier,
    selectedItem: List<T> = listOf(),
    actions: List<SelectableAction<T>>,
    style: SelectableListStyle = SelectableListStyle.Default,
) {

    var moreActionsExtended by rememberSaveable { mutableStateOf(false) }

    val bottomActions = mutableListOf<SelectableAction<T>>()
    val moreActions = mutableListOf<SelectableAction<T>>()
    val visibleActions = actions.filter { it.visible }

    val moreAction = SelectableAction<T>(
        clickable = true,
        iconRes = R.drawable.ic_more,
        textRes = if(visibleActions.first().getText() != null) R.string.more else null,
        onClicked = { _->
            moreActionsExtended = !moreActionsExtended
        })

    if (visibleActions.filter { it.visible }.size > 3) {
        moreActions.addAll(visibleActions.slice(3 until visibleActions.size))
        bottomActions.addAll(visibleActions.slice(0 until 3))
        bottomActions.add(moreAction)
    } else {
        bottomActions.addAll(visibleActions)
    }


    Card(
        elevation = style.menuActionsContainerStyle.elevation,
        shape = RoundedCornerShape(style.menuActionsContainerStyle.radiusCorner),
        modifier = modifier
            .background(style.menuActionsContainerStyle.color)
            .padding(style.menuActionsContainerStyle.padding)
            .fillMaxWidth()
            .height(style.menuActionsContainerStyle.height)
    ) {
        Row(
            modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            bottomActions.forEach { action ->
                MenuActionItem(
                    modifier = Modifier.fillMaxHeight(),
                    action = action,
                    style = style,
                    items = selectedItem,
                    onClicked = { items ->
                        action.onClicked(items)
                    })
            }
        }
    }

    DropdownMenu(
        itemDropdowns = moreActions,
        selectedItem = selectedItem,
        style = style,
        state = moreActionsExtended,
        onDismiss = { moreActionsExtended = false },
        onActionClicked = { selectedItems,actionClicked ->
            actionClicked.onClicked(selectedItems)
        },
    )
}