package com.android.magic_recyclerview.component.magic_recyclerview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.model.Action
import com.android.magic_recyclerview.model.getIcon
import com.android.magic_recyclerview.model.getText


@Composable
fun <T> DropdownMenu(
    modifier: Modifier = Modifier,
    selectedItem: List<T> = listOf(),
    itemDropdowns: List<Action<T>> = listOf(),
    state: Boolean,
    onDismiss: () -> Unit,
    onActionClicked: (selectedItem: List<T>, action: Action<T>) -> Unit,
) {

    val isArabicLanguage = LocalLayoutDirection.current == LayoutDirection.Rtl
    val textAlign = if (isArabicLanguage) TextAlign.Right else TextAlign.Left

    val direction =
        if (isArabicLanguage) {
            LayoutDirection.Ltr
        } else {
            LayoutDirection.Rtl
        }

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(16.dp))) {
            DropdownMenu(
                expanded = state,
                onDismissRequest = { onDismiss() },
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .alpha(0.8f),
                offset = DpOffset(10.dp, 5.dp),
            ) {
                itemDropdowns.forEach { action ->
                    val text = action.getText()
                    val icon = action.getIcon()
                    DropdownMenuItem(
                        content = {
                            if (icon != null) {
                                if (icon is Int) {
                                    Icon(
                                        painter = painterResource(id = icon),
                                        contentDescription = null
                                    )
                                }

                                if (icon is ImageVector) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null
                                    )
                                }
                            }

                            if (text != null) {
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.button,
                                    color = Color.White,
                                    textAlign = textAlign
                                )
                            }
                        },
                        onClick = {
                            if (action.clickable)
                                onActionClicked(selectedItem, action).run { onDismiss() }
                        }
                    )


                }
            }
        }
    }
}