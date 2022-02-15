package com.android.magicrecyclerview.ui.component.action_cards

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.android.magicrecyclerview.R


@Composable
fun DeleteCard() {
    Card(modifier = Modifier.fillMaxWidth(), backgroundColor = Color.Red) {
        Column(
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {


            Image(
                painter = painterResource(id = R.drawable.ic_edit),
                contentDescription = "Delete"
            )



            Text(
                modifier = Modifier.padding(16.dp, 0.dp),
                text = "Delete",
                style = MaterialTheme.typography.h6,
                color = Color.White
            )
        }
    }
}