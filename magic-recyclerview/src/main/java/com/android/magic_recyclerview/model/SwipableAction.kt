package com.android.magic_recyclerview.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.Constants.ACTION_ICON_SIZE

/**
 * @text: a text.
 * @icon: an icon.
 * @actionSize: action size default is 56.
 * @visible: callback to handle onClick.
 * @clickable: action size default is 56.
 * @onClicked - callback to handle onClick.
 */
data class SwipableAction<T>(
    val text: String? = null,
    val icon: ImageVector? = null,
    @StringRes val textRes: Int? = null,
    @DrawableRes val iconRes: Int? = null,
    val backgroundColor: Color = Color.Transparent,
    val actionTextStyle: TextStyle = TextStyle.Default,
    val actionIconStyle:ActionIconStyle = ActionIconStyle.Default,
    val onClicked: (item: T) -> Unit = {},
)

data class MenuAction<T>(
    val text: String? = null,
    val icon: ImageVector? = null,
    @StringRes val textRes: Int? = null,
    @DrawableRes val iconRes: Int? = null,
    val actionSize: Dp = ACTION_ICON_SIZE,
    val visible: Boolean= true,
    val clickable: Boolean= true,
    val onClicked: (item: List<T>) -> Unit = {},
)


@Composable
internal fun <T> SwipableAction<T>.getText() =  if(textRes != null) stringResource(id = textRes) else text

@Composable
internal fun <T> SwipableAction<T>.getIcon() = iconRes ?: icon


@Composable
internal fun <T> MenuAction<T>.getText() =  if(textRes != null) stringResource(id = textRes) else text

@Composable
internal fun <T> MenuAction<T>.getIcon() = iconRes ?: icon