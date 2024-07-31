package com.android.magicrecyclerview

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import com.android.magic_recyclerview.component.list.GridEasyList
import com.android.magic_recyclerview.component.list.HorizontalEasyList
import com.android.magic_recyclerview.component.list.verical.menu.VerticalSelectableList
import com.android.magic_recyclerview.component.list.verical.swapable.VerticalSwipableList
import com.android.magic_recyclerview.model.ActionIconStyle
import com.android.magic_recyclerview.model.MenuAction
import com.android.magic_recyclerview.model.SelectableListStyle
import com.android.magic_recyclerview.model.SelectionStyle
import com.android.magic_recyclerview.model.SwipableAction
import com.android.magic_recyclerview.model.SwipableListStyle
import com.android.magic_recyclerview.model.SwipeBackgroundStyle
import com.android.magicrecyclerview.model.Anime
import com.android.magicrecyclerview.ui.AnimeCard
import com.android.magicrecyclerview.ui.AnimeGridCard
import com.android.magicrecyclerview.ui.emptyView
import com.android.magicrecyclerview.ui.theme.MagicRecyclerViewTheme
import kotlinx.coroutines.delay


var DEFAULT_LIST = DataProvider.itemList

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@ExperimentalFoundationApi
class MainActivity : ComponentActivity() {

    private val mainViewModel by viewModels<MainViewModel> { MainViewModel.Factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MagicRecyclerViewTheme {
                val uiState by mainViewModel.uiState.collectAsState()
                val tabIndex = uiState.selectedTabIndex
                Scaffold(
                    topBar = {
                        TopAppBar(title = { Text(text = "Easy List") })
                    }

                ) {
                    Surface(
                        modifier = Modifier.padding(it),
                        color = MaterialTheme.colors.background
                    ) {

                        Column {
                            val tabTitles = listOf("Vertical Swipe", "Vertical Select", "Horizontal", "Grid")
                            Column {
                                TabRow(
                                    selectedTabIndex = tabIndex
                                ) {
                                    tabTitles.forEachIndexed { index, title ->
                                        Tab(
                                            modifier = Modifier.background(Color.White),
                                            selectedContentColor = colorResource(id = R.color.purple_200),
                                            selected = tabIndex == index,
                                            onClick = { mainViewModel.onSelectTab(index) },
                                            text = { Text(text = title) })
                                    }
                                }
                            }

                            when (tabIndex) {
                                ListType.VERTICAL_SWIPE.index -> VerticalList(DEFAULT_LIST)
                                ListType.VERTICAL_SELECT.index -> VerticalSelectList(DEFAULT_LIST)
                                ListType.HORIZONTAL.index -> HorizontalList(DEFAULT_LIST)
                                ListType.GRID.index -> GridList(DEFAULT_LIST)
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

    val context = LocalContext.current
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isRefreshing by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) {
            delay(2000)
            isLoading = false
        }
    }

    LaunchedEffect(key1 = isRefreshing) {
        if (isRefreshing) {
            delay(2000)
            isRefreshing = false
        }
    }


    val startArchive = SwipableAction<Anime>(
        text = "Archive",
        iconRes = R.drawable.ic_archive,
        actionIconStyle = ActionIconStyle.Default.copy(
            color = Color.White
        ),
        actionTextStyle = TextStyle().copy(
            color = Color.White
        ),
        onClicked = { item ->
            //implement a function
            Toast.makeText(context, "The start Archive action was clicked", Toast.LENGTH_SHORT)
                .show()
        })


    val startDelete = SwipableAction<Anime>(
        text = "Delete",
        iconRes = R.drawable.ic_delete,
        actionIconStyle = ActionIconStyle.Default.copy(
            color = Color.White
        ),
        actionTextStyle = TextStyle().copy(
            color = Color.White
        ),
        onClicked = { item ->
            //implement a function
            Toast.makeText(context, "The start Delete action was clicked", Toast.LENGTH_SHORT)
                .show()
        })


    val startEdit = SwipableAction<Anime>(
        text = "Edit",
        iconRes = R.drawable.ic_edit,
        actionIconStyle = ActionIconStyle.Default.copy(
            color = Color.White
        ),
        actionTextStyle = TextStyle().copy(
            color = Color.White
        ),
        onClicked = { item ->
            //implement a function
            Toast.makeText(context, "The start Edit action was clicked", Toast.LENGTH_SHORT).show()
        })

    val endArchive = SwipableAction<Anime>(
        text = "Archive",
        iconRes = R.drawable.ic_archive,
        backgroundColor = colorResource(R.color.color_action_4),
        actionIconStyle = ActionIconStyle.Default.copy(
            color = Color.Black
        ),
        actionTextStyle = TextStyle().copy(
            color = Color.Black
        ),
        onClicked = { item ->
            Toast.makeText(context, "The end Archive action was clicked", Toast.LENGTH_SHORT).show()
        })


    val endDelete = SwipableAction<Anime>(
        text = "Delete",
        iconRes = R.drawable.ic_delete,
        backgroundColor = colorResource(R.color.color_action_5),
        actionIconStyle = ActionIconStyle.Default.copy(
            color = Color.White
        ),
        actionTextStyle = TextStyle().copy(
            color = Color.White
        ),
        onClicked = { item ->
            //implement a function
            Toast.makeText(context, "The end Delete action was clicked", Toast.LENGTH_SHORT).show()
        })


    VerticalSwipableList(
        modifier = Modifier,
        list = list,
        onItemClicked = { item ->

        },
        onItemDoubleClicked = { item ->

        },
        view = { AnimeCard(anime = it) },
        emptyView = { emptyView() },
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        startActions = listOf(startArchive, startDelete, startEdit),
        endActions = listOf(endArchive, endDelete),
        style = SwipableListStyle.Default.copy(
            swipeBackgroundStyle = SwipeBackgroundStyle.Default.copy(color = Color.Blue)
        ),
        onRefresh = {
            isRefreshing = true
        },
    )
}


@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun VerticalSelectList(list: List<Anime>) {

    val context = LocalContext.current
    val selectedItems = remember { mutableStateListOf<Anime>() }
    var isLoading by rememberSaveable { mutableStateOf(true) }
    var isRefreshing by rememberSaveable { mutableStateOf(false) }
    var isMultiSelectionMode by remember { mutableStateOf(false) }

    val reset = {
        selectedItems.clear()
    }

    val onItemSelected: (Anime)->Unit = { item->
            if(selectedItems.contains(item)){
                selectedItems.remove(item)
            }else{
                selectedItems.add(item)
            }
    }

    LaunchedEffect(isMultiSelectionMode) {
        if(!isMultiSelectionMode){
            reset()
        }

    }

    LaunchedEffect(selectedItems.size) {
        isMultiSelectionMode = selectedItems.isNotEmpty()
    }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) {
            delay(1000)
            isLoading = false
        }
    }

    LaunchedEffect(key1 = isRefreshing) {
        if (isRefreshing) {
            delay(1000)
            isRefreshing = false
        }
    }


    val startArchive = MenuAction<Anime>(
        text = "Archive",
        iconRes = R.drawable.ic_archive,
        onClicked = { items ->
            //implement a function
            reset()
            Toast.makeText(context, "The start Archive action was clicked", Toast.LENGTH_SHORT)
                .show()
        })


    val startDelete = MenuAction<Anime>(
        text = "Delete",
        iconRes = R.drawable.ic_delete,
        onClicked = { items ->
            //implement a function
            reset()
            Toast.makeText(context, "The start Delete action was clicked", Toast.LENGTH_SHORT)
                .show()
        })


    val startEdit = MenuAction<Anime>(
        text = "Edit",
        clickable = false,
        iconRes = R.drawable.ic_edit,
        onClicked = { items ->
            //implement a function
            reset()
            Toast.makeText(context, "The start Edit action was clicked", Toast.LENGTH_SHORT).show()
        })


    VerticalSelectableList(
        modifier = Modifier,
        list = list,
        selectedItemsList = selectedItems,
        onItemClicked = { item, p ->
            if(isMultiSelectionMode){
                onItemSelected(item)
            }

        },
        onItemLongClicked = { item, po ->
            if(isMultiSelectionMode){
                reset()
            }else{
                onItemSelected(item)
            }


        },
        view = { AnimeCard(anime = it) },
        emptyView = { emptyView() },
        isMultiSelectionMode = isMultiSelectionMode,
        isLoading = isLoading,
        isRefreshing = isRefreshing,
        actions = listOf(startArchive, startDelete, startEdit),
        style = SelectableListStyle.Default.copy(
            enabledActionTextStyle = TextStyle().copy(
                textAlign = TextAlign.Center,
                color = Color.Magenta
            ),
            enabledActionIconStyle = ActionIconStyle.Default.copy(
                color = Color.Magenta
            ),
            disabledActionTextStyle = TextStyle().copy(
                textAlign = TextAlign.Center,
                color = Color.LightGray
            ),
            disabledActionIconStyle = ActionIconStyle.Default.copy(
                color = Color.LightGray
            ),

        ),
        onRefresh = {
            isRefreshing = true
        },
    )
}

@Composable
fun HorizontalList(list: List<Anime>) {
    val listItem by remember { mutableStateOf(list) }
    var isLoading by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) {
            delay(1000)
            isLoading = false
        }
    }

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
}


@ExperimentalFoundationApi
@Composable
fun GridList(list: List<Anime>) {
    val listItem by remember { mutableStateOf(list) }
    var isLoading by rememberSaveable { mutableStateOf(true) }

    LaunchedEffect(key1 = isLoading) {
        if (isLoading) {
            delay(1000)
            isLoading = false
        }
    }

    GridEasyList(
        list = listItem,
        view = { AnimeGridCard(anime = it) },
        emptyView = { emptyView() },
        isLoading = isLoading,
        columnCount = 3,
        scrollTo = 0,
        onItemClicked = { item, position ->

        },


        )
}
