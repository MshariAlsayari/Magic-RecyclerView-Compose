package com.android.magic_recyclerview.component.action_row

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.android.magic_recyclerview.R
import com.android.magic_recyclerview.model.Action
import com.android.magic_recyclerview.model.getIcon
import com.android.magic_recyclerview.model.getText


@Composable
fun <T> ActionItem(
    modifier: Modifier = Modifier,
    action: Action<T>,
    onClicked: (position: Int, item: T) -> Unit,
    item: T,
    position: Int
) {

    IconButton(
        modifier = modifier,
        onClick = {
            onClicked(position, item)
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
                            painter = painterResource(id = icon),
                            contentDescription = null
                        )
                    }

                    if(icon is ImageVector){
                        Icon(
                            imageVector = icon,
                            contentDescription = null
                        )
                    }
                }


                if(text != null){
                    Text(
                        text = text,
                        style = MaterialTheme.typography.button,
                        color = Color.White
                    )
                }
            }
        }
    )


}

@Preview(showBackground = true)
@Composable
fun ActionItemPreview(){

    Action<Int>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_2),
        onClicked = { position, item ->

        })
}
