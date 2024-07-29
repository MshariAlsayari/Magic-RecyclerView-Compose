package com.android.magic_recyclerview.component.action_row

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.android.magic_recyclerview.Constants
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.SwipableAction
import com.android.magic_recyclerview.model.SwipableListStyle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun <T> ActionsRow(
    modifier: Modifier,
    item: T,
    actions: List<SwipableAction<T>>,
    style: SwipableListStyle = SwipableListStyle.Default,
    isActionClicked: (() -> Unit) = {}
) {

    val coroutine = rememberCoroutineScope()

    Card(
        modifier = modifier.fillMaxHeight(),
        backgroundColor = style.swipeBackgroundStyle.color,
        shape = RoundedCornerShape(style.swipeBackgroundStyle.radiusCorner),
    ) {

        Row(
            modifier = modifier.fillMaxHeight(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            actions.forEach {
                ActionItem(
                    modifier = Modifier
                        .weight(1f)
                        .background(it.backgroundColor)
                        .fillMaxHeight(),
                    action = it,
                    onClicked = { item ->
                        isActionClicked()
                        coroutine.launch {
                            delay(Constants.SWIPE_ANIMATION_DURATION.toLong())
                            it.onClicked(item)
                        }
                    },
                    item = item
                )
            }
        }

    }

}
