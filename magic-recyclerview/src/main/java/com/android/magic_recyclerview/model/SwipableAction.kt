package com.android.magic_recyclerview.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import com.android.magic_recyclerview.Constants.ACTION_ICON_SIZE

/**
* @text: The text to display for the action.
* @icon: The icon to display for the action.
* @textRes: A string resource for the action text.
* @iconRes: A drawable resource for the action icon.
* @backgroundColor: The background color for the action.
* @actionTextStyle: The text style for the action text.
* @actionIconStyle: The icon style for the action icon.
* @onClicked: The callback to invoke when the action is clicked.
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

/**
 * @text: The text to display for the action.
 * @icon: The icon to display for the action.
 * @textRes: A string resource for the action text.
 * @iconRes: A drawable resource for the action icon.
 * @actionSize: The size of the action icon.
 * @visible: A boolean flag to control the visibility of the action.
 * @clickable: A boolean flag to control the click ability of the action.
 * @onClicked: The callback to invoke when the action is clicked.
 */

data class SelectableAction<T>(
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
internal fun <T> SelectableAction<T>.getText() =  if(textRes != null) stringResource(id = textRes) else text

@Composable
internal fun <T> SelectableAction<T>.getIcon() = iconRes ?: icon