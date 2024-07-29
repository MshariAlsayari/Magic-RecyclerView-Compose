package com.android.magic_recyclerview.component.magic_recyclerview


import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.model.SelectableListStyle

@Composable
internal fun Selection(
    style: SelectableListStyle = SelectableListStyle.Default,
    isSelected: Boolean
) {

    val background = if(isSelected){
        style.selectedSelectionStyle.selectionBackground
    }else{
        style.unselectedSelectionStyle.selectionBackground
    }



    Box(
        modifier = Modifier
            .size(24.dp)
            .clip(CircleShape)
            .background(background)
            .border(1.dp, Color.LightGray, CircleShape)
            .padding(horizontal = 16.dp)
    ) {
        AnimatedVisibility(
            visible = isSelected,
            enter = scaleIn(),
            exit = scaleOut(),
        ) {
            Icon(
                style.selectedSelectionStyle.checkIcon,
                contentDescription = null,
                tint = style.selectedSelectionStyle.iconColor
            )
        }
    }

}