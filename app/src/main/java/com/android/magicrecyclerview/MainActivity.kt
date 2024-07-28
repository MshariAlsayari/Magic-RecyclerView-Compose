package com.android.magicrecyclerview

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import com.android.magic_recyclerview.component.list.GridEasyList
import com.android.magic_recyclerview.component.list.HorizontalEasyList
import com.android.magic_recyclerview.ListType
import com.android.magic_recyclerview.component.list.VerticalEasyList
import com.android.magic_recyclerview.model.Action
import com.android.magicrecyclerview.model.Anime
import com.android.magicrecyclerview.ui.AnimeCard
import com.android.magicrecyclerview.ui.AnimeGridCard
import com.android.magicrecyclerview.ui.emptyView
import com.android.magicrecyclerview.ui.theme.MagicRecyclerViewTheme


var DEFAULT_LIST = DataProvider.itemList

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val testList = mutableListOf<Anime>()
        for (i in 1..5){
            testList.addAll(DEFAULT_LIST.map { it })
        }

        setContent {
            MagicRecyclerViewTheme {
                var listType by remember { mutableStateOf(ListType.VERTICAL) }

                Scaffold(

                    topBar = {
                        TopAppBar(title = { Text(text = "Easy List") })

                    }

                ) {

                    Surface(
                        modifier = Modifier.padding(it),
                        color = MaterialTheme.colors.background) {

                        Column {

                            var tabIndex by remember { mutableIntStateOf(0) } // 1.
                            val tabTitles = listOf("Vertical", "Horizontal", "Grid")
                            Column { // 2.
                                TabRow(
                                    selectedTabIndex = tabIndex
                                ) { // 3.
                                    tabTitles.forEachIndexed { index, title ->
                                        Tab(
                                            modifier = Modifier.background(Color.White),
                                            selectedContentColor = colorResource(id = R.color.purple_200),
                                            selected = tabIndex == index, // 4.
                                            onClick = { tabIndex = index },
                                            text = { Text(text = title) }) // 5.
                                    }
                                }
                                when (tabIndex) { // 6.
                                    0 -> listType = ListType.VERTICAL
                                    1 -> listType = ListType.HORIZONTAL
                                    2 -> listType = ListType.GRID
                                }
                            }



                            when (listType) {
                                ListType.VERTICAL -> VerticalList(testList)
                                ListType.HORIZONTAL -> HorizontalList(testList)
                                ListType.GRID -> GridList(testList)
                            }
                        }

                    }

                }


            }
        }
    }
}

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun VerticalList(list: List<Anime>) {

    var listItem by remember { mutableStateOf(list) }
    var isLoading by remember { mutableStateOf(true) }
    var isRefreshing by remember { mutableStateOf(false) }



    val deleteAction1 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_4),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })


    val deleteAction2 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_5),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })


    val deleteAction3 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_6),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })

    val archiveAction1 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_1),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })

    Icons.Default.Delete

    val archiveAction2 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_2),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })

    val archiveAction3 = Action<Anime>(
        text ="Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_3),
        onClicked = { position, item ->
            listItem = listItem.filter {
                it.anime_id != item.anime_id
            }

        })


    VerticalEasyList(
        modifier = Modifier,
        list = listItem,
        onItemClicked = { item, position ->

        },
        onItemDoubleClicked = { item, position ->

        },
        view = { AnimeCard(anime = it) },
        emptyView = { emptyView() },
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        onRefresh={
                  isRefreshing = true
            Handler(Looper.getMainLooper()).postDelayed({
                isRefreshing = false
            }, 2000)
        },
        startActions = listOf(deleteAction1, deleteAction2, deleteAction3),
        endActions = listOf(archiveAction1, archiveAction2, archiveAction3),
    )

    Handler(Looper.getMainLooper()).postDelayed({
        isLoading = false
    }, 2000)
}

@Composable
fun HorizontalList(list: List<Anime>) {
    val listItem by remember { mutableStateOf(list) }
    var isLoading by remember { mutableStateOf(true) }
    HorizontalEasyList(
        list = listItem,
        view = { AnimeCard(anime = it) },
        emptyView = { emptyView() },
        isLoading = isLoading,
        paddingBetweenItems = 8f,
        paddingVertical = 8f,
        dividerView = {

        }
    )

    Handler(Looper.getMainLooper()).postDelayed({
        isLoading = false
    }, 1000)

}


@ExperimentalFoundationApi
@Composable
fun GridList(list: List<Anime>) {
    val listItem by remember { mutableStateOf(list) }
    var isLoading by remember { mutableStateOf(true) }

    GridEasyList(
        list = listItem,
        view = { AnimeGridCard(anime = it) },
        emptyView = { emptyView() },
        isLoading = isLoading,
        columnCount = 2,
        scrollTo = 0,
        onItemClicked = {item,position->

        },


    )

    Handler(Looper.getMainLooper()).postDelayed({
        isLoading = false
    }, 1000)
}
