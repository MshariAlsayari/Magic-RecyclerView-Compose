# Easy-List-Compose [![](https://jitpack.io/v/MshariAlsayari/Easy-List-Compose.svg)](https://jitpack.io/#MshariAlsayari/Easy-List-Compose)






## Table of contents
- [Introduction](#introduction)
- [Setup](#setup)
- [Examples](#examples)
   - [Vertical-swipe](#verticalSwipe)
     - [Vertical-swipe-fixed](#verticalSwipeFixed) 
     - [Vertical-swipe-paged](#verticalSwipePaged)
   - [Vertical-select](#VerticalSelect)
     - [Vertical-select-fixed](#verticalSelectFixed)
     - [Vertical-select-paged](#verticalSelectPaged)
   - [Horizontal](#horizontal)
   - [Grid](#grid)


## Introduction
This is an Android Library that's implemented in compose to help you to build a List with some features.


![signal-2022-02-21-141730](https://user-images.githubusercontent.com/32165999/154946820-b11702c4-a144-47fd-9dc1-3736fa269718.gif)


## Setup
#### Step1: settings.gradle

```
   repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
```

#### Step2: add the dependency 

```
 dependencies {
     ...
 	  implementation 'com.github.MshariAlsayari:Easy-List-Compose:<last-version>'
 }
```


## Examples
There are four types of Lists ***VerticalSwipable, VerticalSelectable, Horizontal and Grid.***
In the VerticalSwipable list you can make items swappable by adding list of Action onStart or onEnd. In the VerticalSelectable list you can enable and disable a multi selection mode to make your list items selectable


```
/***
 * text - a composable text.
 * icon -  a composable icon c.
 * onClicked - calback to handle onClick.
 * actionSize - action size default is 56.
 */
 
data class Action<T>(
    val text: (@Composable () -> Unit)? = null,
    val icon: (@Composable () -> Unit)? = null,
    val backgroundColor: Color = Color.Transparent,
    val onClicked: ((position: Int, item: T) -> Unit)? = null,
    val actionSize: Dp = ACTION_ICON_SIZE.dp
)
```



## VerticalSwipe
We have two kine of Lists ***Fixed list and Pagination List***,
we will explain both of them but first we need to explain some classes that we probably need them.
let's say we have List of Anime.It must extend from the base class ***SelectableItemBase*** and if we need add actions on items like Delete, Edit or Archive ...etc we have the SwipableAction class

```
data class Anime (
    val animeId:Int,
    val animeName:String,
    val anumeImg:String,
):SelectableItemBase(id = animeId.toLong(), rowType = RowType.ITEM)
```


```
abstract class SelectableItemBase(
    open var id:Long,
    open var rowType: RowType,
    open var selected:Boolean = false,
    open var selectable:Boolean = true
)

enum class RowType{
    ITEM,HEADER
}
```

```
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


```

## verticalSwipeFixed
```
/***
 * modifier: Modifier for styling the list.
 * list: Data source for the items in the list.
 * view: Composable function to display each item.
 * dividerView: (Optional) Composable function to show between items.
 * emptyView: (Optional) Composable function for the empty state.
 * loadingProgress: (Optional) Composable function for loading state.
 * onItemClicked: Callback for item click action.
 * onItemDoubleClicked: Callback for item double click action.
 * startActions: Actions available when swiping from the start.
 * endActions: Actions available when swiping from the end.
 * style: Custom style for the swipable list.
 * isLoading: Boolean indicating loading state.
 * isRefreshing: Boolean indicating refresh state.
 * onRefresh: Callback for refreshing the list.
 * scrollTo: Index to scroll to on initialization.
*/
 
 
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun <T> VerticalSwipableList(
    modifier: Modifier = Modifier,
    list: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    startActions: List<SwipableAction<T>> = listOf(),
    endActions: List<SwipableAction<T>> = listOf(),
    style: SwipableListStyle = SwipableListStyle.Default,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    scrollTo: Int = 0,
)


@Composable
fun MyScreen() {
    VerticalSwipableList(
        list = myItemList,
        view = { item ->
            // Define how to display each item
        },
        dividerView = {
            // Define divider view if needed
        },
        emptyView = {
            // Define view to show when the list is empty
        },
        loadingProgress = {
            // Display loading indicator
        },
        onItemClicked = { item ->
            // Handle item click
        },
        onItemDoubleClicked = { item ->
            // Handle item double click
        },
        startActions = listOf(
            SwipableAction(
                text = "Edit",
                icon = Icons.Default.Edit,
                backgroundColor = Color.Blue,
                onClicked = { item ->
                    // Handle edit action
                }
            )
        ),
        endActions = listOf(
            SwipableAction(
                text = "Delete",
                icon = Icons.Default.Delete,
                backgroundColor = Color.Red,
                onClicked = { item ->
                    // Handle delete action
                }
            )
        ),
        isLoading = false,
        isRefreshing = false,
        onRefresh = { /* Handle refresh */ },
        scrollTo = 0
    )
}


```
## verticalSwipePaged
```

/***
 * modifier: Modifier for styling the list.
 * list: The LazyPagingItems list to display.
 * view: Composable function to display each item.
 * dividerView: (Optional) Composable function to show between items.
 * emptyView: (Optional) Composable function for the empty state.
 * loadingProgress: (Optional) Composable function for loading state.
 * onItemClicked: Callback for item click action.
 * onItemDoubleClicked: Callback for item double click action.
 * startActions: Actions available when swiping from the start.
 * endActions: Actions available when swiping from the end.
 * style: Custom style for the swipable list.
 * isLoading: Boolean indicating loading state.
 * isRefreshing: Boolean indicating refresh state.
 * onRefresh: Callback for refreshing the list.
 * scrollTo: Index to scroll to on initialization.
 */
@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun <T : SelectableItemBase> VerticalSwipablePagedList(
    modifier: Modifier = Modifier,
    list: LazyPagingItems<T>?,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T) -> Unit,
    onItemDoubleClicked: (item: T) -> Unit,
    startActions: List<SwipableAction<T>> = listOf(),
    endActions: List<SwipableAction<T>> = listOf(),
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    style: SwipableListStyle = SwipableListStyle.Default,
    scrollTo: Int = 0,
    onRefresh: (() -> Unit)? = null,
)

@ExperimentalComposeUiApi
@ExperimentalMaterialApi
@Composable
fun MyScreen() {
    val items: LazyPagingItems<MyItem> = // provide your LazyPagingItems here
    VerticalSwipablePagedList(
        list = items,
        view = { item ->
            Text(text = item.name)
        },
        onItemClicked = { item ->
            // Handle item click
        },
        onItemDoubleClicked = { item ->
            // Handle item double-click
        },
        startActions = listOf(
            SwipableAction(
                text = "Edit",
                icon = Icons.Default.Edit,
                backgroundColor = Color.Blue,
                onClicked = { item ->
                    // Handle edit action
                }
            )
        ),
        endActions = listOf(
            SwipableAction(
                text = "Delete",
                icon = Icons.Default.Delete,
                backgroundColor = Color.Red,
                onClicked = { item ->
                    // Handle delete action
                }
            )
        ),
        isLoading = false,
        isRefreshing = false,
        style = SwipableListStyle.Default,
        scrollTo = 0,
        onRefresh = {
            // Handle refresh
        }
    )
}


```


## VerticalSelect
As we said in the **Vertical Swipeable lists section** we have Anime and SelectableItemBase but we have a new class of Actions SelectableAction

```
data class Anime (
    val animeId:Int,
    val animeName:String,
    val anumeImg:String,
):SelectableItemBase(id = animeId.toLong(), rowType = RowType.ITEM)
```


```
abstract class SelectableItemBase(
    open var id:Long,
    open var rowType: RowType,
    open var selected:Boolean = false,
    open var selectable:Boolean = true
)

enum class RowType{
    ITEM,HEADER
}
```

```
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
```

## verticalSelectFixed
```
/**
 * @modifier: A Modifier for the composable.
 * @list: The list of items to display.
 * @selectedItemsList: The list of currently selected items.
 * @view: A composable function to display each item.
 * @dividerView: A composable function to display between items.
 * @selectedSelectionView: A composable function to display for selection view .
 * @unselectedSelectionView: A composable function to display for un-selection view.
 * @emptyView: A composable function to display when the list is empty.
 * @loadingProgress: A composable function to display while loading.
 * @onItemClicked: A callback for item click events.
 * @onItemLongClicked: A callback for item long-click events.
 * @actions: A list of actions that can be performed on items.
 * @isMultiSelectionMode: A flag to indicate if multi-selection mode is enabled.
 * @style: The style for the selectable list.
 * @scrollTo: The index to scroll to.
 * @isLoading: A flag to indicate if the list is loading.
 * @isRefreshing: A flag to indicate if the list is refreshing.
 * @onRefresh: A callback to trigger refresh.
 */
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun <T:SelectableItemBase> VerticalSelectableList(
    modifier: Modifier = Modifier,
    list: List<T>,
    selectedItemsList: List<T>,
    view: @Composable (T) -> Unit,
    dividerView: (@Composable () -> Unit)? = null,
    selectedSelectionView: (@Composable () -> Unit)? = null,
    unselectedSelectionView: (@Composable () -> Unit)? = null,
    emptyView: (@Composable () -> Unit)? = null,
    loadingProgress: (@Composable () -> Unit)? = null,
    onItemClicked: (item: T, position: Int) -> Unit,
    onItemLongClicked: (item: T, position: Int) -> Unit,
    actions: List<SelectableAction<T>> = listOf(),
    isMultiSelectionMode: Boolean = false,
    style: SelectableListStyle = SelectableListStyle.Default,
    scrollTo: Int = 0,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
)

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterialApi::class)
@Composable
fun MyScreenUsage() {
    val items: List<MyItem> = // provide your list of items here
    val selectedItems: List<MyItem> = // provide your list of selected items here
    VerticalSelectableList(
        list = items,
        selectedItemsList = selectedItems,
        view = { item ->
            Text(text = item.name)
        },
        onItemClicked = { item, position ->
            // Handle item click
        },
        onItemLongClicked = { item, position ->
            // Handle item long-click
        },
        actions = listOf(
            SelectableAction(
                text = "Delete",
                icon = Icons.Default.Delete,
                onClicked = { selectedItems ->
                    // Handle delete action
                }
            )
        ),
        isMultiSelectionMode = true,
        isLoading = false,
        isRefreshing = false,
        onRefresh = {
            // Handle refresh
        }
    )
}

```

## verticalSelectPaged
```
```



## Horizontal
```

/***
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * views - the data view holder.
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyview if the list is empty.
 * paddingBetweenItems - padding between items default is 8f.
 * paddingVertical - padding on top and bottom of the whole list default is 0.
 * paddingHorizontal - padding on left and right of the whole list default is 0.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * scrollTo - scroll to item default is 0.
 */
@Composable
fun <T> HorizontalEasyList(
    modifier: Modifier = Modifier,
    list: List<T>,
    views: @Composable LazyItemScope.(item: T) -> Unit,
    emptyView: (@Composable () -> Unit)? = null,
    dividerView: (@Composable () -> Unit)? = null,
    isLoading: Boolean = false,
    loadingProgress: (@Composable () -> Unit)? = null,
    paddingBetweenItems: Float = PADDING_BETWEEN_ITEMS,
    paddingVertical: Float = PADDING_VERTICAL,
    paddingHorizontal: Float = PADDING_HORIZONTAL,
    scrollTo: Int = 0,
)

```


## Grid
```

/***
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * views - the data view holder.
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyview if the list is empty.
 * actionBackgroundHeight - height of the actions background.
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * paddingBetweenItems - padding between items default is 8f.
 * paddingVertical - padding on top and bottom of the whole list default is 0.
 * paddingHorizontal - padding on left and right of the whole list default is 0.
 * scrollTo - scroll to item default is 0.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * columnCount - number of columns default is 2
 */
@ExperimentalFoundationApi
@Composable
fun <T> GridEasyList(
    modifier: Modifier = Modifier,
    list: List<T>,
    views: @Composable (LazyItemScope.(item: T) -> Unit),
    emptyView: @Composable (() -> Unit)? = null,
    paddingBetweenItems: Float = PADDING_BETWEEN_ITEMS,
    paddingVertical: Float = PADDING_VERTICAL,
    paddingHorizontal: Float = PADDING_HORIZONTAL,
    columnCount: Int = COLUMN_COUNT,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
    isLoading: Boolean = false,
    loadingProgress: (@Composable () -> Unit)? = null,
    scrollTo: Int = 0,
)
```



