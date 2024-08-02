package com.android.magic_recyclerview.component.action_row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.R
import com.android.magic_recyclerview.model.SelectableAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.SwipableAction
import com.android.magic_recyclerview.model.getIcon
import com.android.magic_recyclerview.model.getText


@Composable
fun <T> ActionItem(
    modifier: Modifier = Modifier,
    item: T,
    action: SwipableAction<T>,
    onClicked: (item: T) -> Unit,
) {

    IconButton(
        modifier = modifier,
        onClick = {
            onClicked(item)
        },
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val icon = action.getIcon()
                val text =  action.getText()
                if(icon != null){
                    if(icon is Int){
                        Icon(
                            modifier = Modifier.size(action.actionIconStyle.size),
                            tint = action.actionIconStyle.color,
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                    }

                    if(icon is ImageVector){
                        Icon(
                            modifier = Modifier.size(action.actionIconStyle.size),
                            tint = action.actionIconStyle.color,
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                }


                if(text != null){
                    Text(
                        text = text,
                        style = action.actionTextStyle,
                    )
                }
            }
        }
    )


}


@Composable
fun <T> MenuActionItem(
    modifier: Modifier = Modifier,
    items: List<T>,
    action: SelectableAction<T>,
    style: SelectableListStyle = SelectableListStyle.Default,
    onClicked: (item: List<T>) -> Unit,
) {

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

    IconButton(
        modifier = modifier,
        enabled = action.clickable,
        onClick = {
            onClicked(items)
        },
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                val icon = action.getIcon()
                val text =  action.getText()
                if(icon != null){
                    if(icon is Int){
                        Icon(
                            modifier = Modifier.size(iconStyle.size),
                            tint = iconStyle.color,
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                    }

                    if(icon is ImageVector){
                        Icon(
                            modifier = Modifier.size(iconStyle.size),
                            tint = iconStyle.color,
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                }


                if(text != null){
                    Text(
                        text = text,
                        style = textStyle,
                    )
                }
            }
        }
    )


}

@Preview(showBackground = true)
@Composable
fun ActionItemPreview(){

    SwipableAction<Int>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_2),
        onClicked = {  item ->

        })
}
