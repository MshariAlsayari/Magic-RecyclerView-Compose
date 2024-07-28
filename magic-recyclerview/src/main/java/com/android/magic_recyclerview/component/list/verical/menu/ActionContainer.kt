package com.android.magic_recyclerview.component.list.verical.menu

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
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.R
import com.android.magic_recyclerview.component.action_row.MenuActionItem
import com.android.magic_recyclerview.component.magic_recyclerview.DropdownMenu
import com.android.magic_recyclerview.model.Action

@Composable
fun <T> ActionContainer(
    modifier: Modifier = Modifier,
    selectedItem: List<T> = listOf(),
    actions: List<Action<T>>,
) {

    var moreActionsExtended by rememberSaveable { mutableStateOf(false) }

    val bottomActions = mutableListOf<Action<T>>()
    val moreActions = mutableListOf<Action<T>>()

    val moreAction = Action<T>(
        clickable = true,
        iconRes = R.drawable.ic_more,
        onClicked = { items ->
            moreActionsExtended = !moreActionsExtended
        })

    if (actions.size > 3) {
        moreActions.addAll(actions.slice(3 until actions.size))
        bottomActions.addAll(actions.slice(0 until 3))
        bottomActions.add(moreAction)
    } else {
        bottomActions.addAll(actions)
    }


    Card(
        elevation = 4.dp,
        shape = RoundedCornerShape(20.dp),
        modifier = modifier
            .padding(18.dp)
            .fillMaxWidth()
            .height(85.dp)
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
                    items = selectedItem,
                    onClicked = { items ->
                        action.onClicked?.let { it1 -> it1(items) }
                    })
            }


        }
    }

    DropdownMenu(
        itemDropdowns = moreActions,
        selectedItem = selectedItem,
        state = moreActionsExtended,
        onDismiss = { moreActionsExtended = false },
        onActionClicked = { selectedItems, actionClicked ->
            actionClicked.onClicked?.let { it1 -> it1(selectedItems) }
        },
    )
}