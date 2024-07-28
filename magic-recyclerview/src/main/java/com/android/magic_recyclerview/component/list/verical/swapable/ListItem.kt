package com.android.magic_recyclerview.component.list.verical.swapable

import android.os.Handler
import android.os.Looper
import androidx.compose.foundation.layout.Row
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.android.magic_recyclerview.component.action_row.ActionRowType
import com.android.magic_recyclerview.component.action_row.ActionsRow
import com.android.magic_recyclerview.component.swippable_item.SwappableItem
import com.android.magic_recyclerview.model.Action

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun <T> ListItem(
    item: T,
    isLast: Boolean,
    view: @Composable () -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    startActions: List<Action<T>> = listOf(),
    endActions: List<Action<T>> = listOf(),
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    onItemCollapsed: ((item: T) -> Unit)? = null,
    onItemExpanded: ((item: T) -> Unit)? = null,
    actionBackgroundRadiusCorner: Float = 0f,
) {

    val isActionClicked = rememberSaveable { mutableStateOf(false) }
    val isRTL = LocalLayoutDirection.current == LayoutDirection.Rtl


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
                radiusCorner = actionBackgroundRadiusCorner,
                actions = startActions,
                isActionClicked = {
                    isActionClicked.value = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        isActionClicked.value = false
                    }, 1000)
                }
            )
            ActionsRow(
                modifier = Modifier.weight(1f),
                item = item,
                radiusCorner = actionBackgroundRadiusCorner,
                actions = endActions,
                isActionClicked = {
                    isActionClicked.value = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        isActionClicked.value = false
                    }, 1000)
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
            isActionClicked = isActionClicked.value,
            onCollapsed = { item -> onItemCollapsed?.invoke(item) },
            onExpanded = { type, item -> onItemExpanded?.invoke(item) },
            enableLTRSwipe = if (isRTL) endActions.isNotEmpty() else startActions.isNotEmpty(),
            enableRTLSwipe = if (isRTL) startActions.isNotEmpty() else endActions.isNotEmpty(),
            onItemClicked = { onItemClicked(it) },
            onItemDoubleClicked = { onItemDoubleClicked(it) }
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