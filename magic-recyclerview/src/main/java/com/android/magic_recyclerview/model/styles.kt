package com.android.magic_recyclerview.model

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.Constants.ACTION_ICON_SIZE
import com.android.magic_recyclerview.Constants.BORDER_WIDTH
import com.android.magic_recyclerview.Constants.ELEVATION
import com.android.magic_recyclerview.Constants.HEIGHT
import com.android.magic_recyclerview.Constants.RADIUS_CORNER
import com.android.magic_recyclerview.Constants.SELECTION_SIZE


data class ActionIconStyle(
    val size: Dp = ACTION_ICON_SIZE,
    val color: Color = Color.Unspecified
){
    companion object {
        @Stable
        val Default = ActionIconStyle()
    }
}


data class SwipeBackgroundStyle(
    val radiusCorner: Float = 0f,
    val color: Color = Color.Unspecified
){
    companion object {
        @Stable
        val Default = SwipeBackgroundStyle()
    }
}

data class MenuActionsContainerStyle(
    val radiusCorner: Dp = RADIUS_CORNER,
    val elevation: Dp = ELEVATION,
    val height: Dp = HEIGHT,
    val color: Color = Color.Unspecified
){
    companion object {
        @Stable
        val Default = MenuActionsContainerStyle()
    }
}

data class SelectionStyle(
    val size: Dp = SELECTION_SIZE,
    val shape: Shape = CircleShape,
    val checkIcon: ImageVector = Icons.Default.Check,
    val iconColor:Color = Color.Unspecified,
    val selectionBackground:Color = Color.Unspecified,
    val borderColor:Color = Color.Unspecified,
    val borderWidth:Dp = BORDER_WIDTH,
){
    companion object {
        @Stable
        fun Default(isSelected:Boolean = false):SelectionStyle {
            return if(isSelected){
                SelectionStyle(
                    iconColor = Color.White,
                    selectionBackground = Color.Black,
                    borderColor = Color.Transparent,
                )
            }else{
                SelectionStyle(
                    iconColor = Color.Transparent,
                    selectionBackground = Color.Transparent,
                    borderColor = Color.LightGray,
                )
            }
        }
    }
}

data class SelectableListStyle(
    val enabledActionTextStyle:TextStyle = TextStyle.Default,
    val disabledActionTextStyle:TextStyle = TextStyle.Default,
    val enabledActionIconStyle:ActionIconStyle = ActionIconStyle.Default,
    val disabledActionIconStyle:ActionIconStyle = ActionIconStyle.Default,
    val menuActionsContainerStyle: MenuActionsContainerStyle = MenuActionsContainerStyle.Default,
    val selectedSelectionStyle:SelectionStyle = SelectionStyle.Default(true),
    val unselectedSelectionStyle:SelectionStyle = SelectionStyle.Default(false),
){
    companion object {
        @Stable
        val Default = SelectableListStyle()
    }
}


data class SwipableListStyle(
    val swipeBackgroundStyle: SwipeBackgroundStyle = SwipeBackgroundStyle.Default,
){
    companion object {
        @Stable
        val Default = SwipableListStyle()
    }
}