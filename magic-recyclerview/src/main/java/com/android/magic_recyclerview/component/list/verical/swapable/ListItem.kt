package com.android.magic_recyclerview.component.list.verical.swapable

import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.android.magic_recyclerview.component.action_row.ActionsRow
import com.android.magic_recyclerview.component.swippable_item.SwappableItem
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.SwipableAction
import com.android.magic_recyclerview.model.SwipableListStyle
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun <T> ListItem(
    item: T,
    isLast: Boolean,
    view: @Composable () -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    startActions: List<SwipableAction<T>> = listOf(),
    endActions: List<SwipableAction<T>> = listOf(),
    style: SwipableListStyle = SwipableListStyle.Default,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
) {

    var isActionClicked by rememberSaveable { mutableStateOf(false) }
    val isRTL = LocalLayoutDirection.current == LayoutDirection.Rtl

    LaunchedEffect(key1 = isActionClicked) {
        if(isActionClicked){
            delay(1000)
            isActionClicked = false
        }

    }


    ConstraintLayout {
        val (actionContainer, swappableItemContainer, divider) = createRefs()
        Row(
            modifier = Modifier.constrainAs(actionContainer) {
                top.linkTo(swappableItemContainer.top)
                bottom.linkTo(swappableItemContainer.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                height = Dimension.fillToConstraints
            },

            ) {
            ActionsRow(
                modifier = Modifier.weight(1f),
                item = item,
                style = style,
                actions = startActions,
                isActionClicked = {
                    isActionClicked = true
                }
            )
            ActionsRow(
                modifier = Modifier.weight(1f),
                item = item,
                style = style,
                actions = endActions,
                isActionClicked = {
                    isActionClicked = true
                }
            )

        }


        SwappableItem(
            modifier = Modifier.constrainAs(swappableItemContainer) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            },
            item = item,
            mainItem = { view() },
            isActionClicked = isActionClicked,
            onCollapsed = {},
            onExpanded = {},
            enableLTRSwipe = if (isRTL) endActions.isNotEmpty() else startActions.isNotEmpty(),
            enableRTLSwipe = if (isRTL) startActions.isNotEmpty() else endActions.isNotEmpty(),
            onItemClicked = onItemClicked,
            onItemDoubleClicked = onItemDoubleClicked
        )

        if (!isLast && dividerView != null) {
            Surface(
                modifier = Modifier
                    .constrainAs(divider) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(swappableItemContainer.bottom)
                    }
            ) {
                dividerView()
            }

        }

    }
}