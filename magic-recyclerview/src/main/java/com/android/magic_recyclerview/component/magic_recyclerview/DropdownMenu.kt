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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.model.SelectableAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.getIcon
import com.android.magic_recyclerview.model.getText


@Composable
internal fun <T> DropdownMenu(
    modifier: Modifier = Modifier,
    selectedItem: List<T> = listOf(),
    itemDropdowns: List<SelectableAction<T>> = listOf(),
    style: SelectableListStyle = SelectableListStyle.Default,
    state: Boolean,
    onDismiss: () -> Unit,
    onActionClicked: (selectedItem: List<T>, action: SelectableAction<T>) -> Unit,
) {

    val isArabicLanguage = LocalLayoutDirection.current == LayoutDirection.Rtl
    val textAlign = if (isArabicLanguage) TextAlign.Right else TextAlign.Left


    val direction =
        if (isArabicLanguage) {
            LayoutDirection.Ltr
        } else {
            LayoutDirection.Rtl
        }

    val menuItemDirection =if (isArabicLanguage) {
        LayoutDirection.Rtl
    } else {
        LayoutDirection.Ltr
    }

    CompositionLocalProvider(LocalLayoutDirection provides direction) {
        MaterialTheme(shapes = MaterialTheme.shapes.copy(medium = RoundedCornerShape(style.menuActionsContainerStyle.radiusCorner))) {
            DropdownMenu(
                expanded = state,
                onDismissRequest = { onDismiss() },
                modifier = modifier
                    .width(IntrinsicSize.Max)
                    .alpha(0.8f),
                offset = DpOffset(10.dp, 5.dp),
            ) {
                itemDropdowns.forEach { action ->
                    val iconStyle = if(action.clickable){
                        style.enabledActionIconStyle
                    }else{
                        style.disabledActionIconStyle
                    }

                    val textStyle = if (action.clickable) {
                        style.enabledActionTextStyle
                    } else {
                        style.disabledActionTextStyle
                    }
                    val text = action.getText()
                    val icon = action.getIcon()
                    CompositionLocalProvider(LocalLayoutDirection provides menuItemDirection) {
                        DropdownMenuItem(
                            enabled = action.clickable,
                            content = {
                                if (icon != null) {
                                    if (icon is Int) {
                                        Icon(
                                            modifier = Modifier.size(iconStyle.size).padding(end = 5.dp),
                                            tint = iconStyle.color,
                                            painter = painterResource(id = icon),
                                            contentDescription = null
                                        )
                                    }

                                    if (icon is ImageVector) {
                                        Icon(
                                            modifier = Modifier.size(iconStyle.size).padding(end = 5.dp),
                                            tint = iconStyle.color,
                                            imageVector = icon,
                                            contentDescription = null
                                        )
                                    }
                                }

                                if (text != null) {
                                    Text(
                                        text = text,
                                        style = textStyle.copy(textAlign = textAlign),
                                        textAlign = textAlign
                                    )
                                }
                            },
                            onClick = {
                                onActionClicked(selectedItem, action).run { onDismiss() }
                            }
                        )

                    }



                }
            }
        }
    }
}