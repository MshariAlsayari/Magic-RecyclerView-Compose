# Easy-List-Compose [![](https://jitpack.io/v/MshariAlsayari/Easy-List-Compose.svg)](https://jitpack.io/#MshariAlsayari/Easy-List-Compose)






## Table of contents
- [Introduction](#introduction)
- [Setup](#setup)
- [Examples](#examples)
   - [Vertical-swipe](#verticalSwipe)
   - [Vertical-select](#VerticalSelect)
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

In this example we should have a model let's say Anime and it must extend from base class SelectableItemBase

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
        startActions = listOf(/* Define swipable actions for the start */),
        endActions = listOf(/* Define swipable actions for the end */),
        isLoading = false,
        isRefreshing = false,
        onRefresh = { /* Handle refresh */ },
        scrollTo = 0
    )
}


```


## VerticalSelect
In this example we should have a model let's say Anime and it must extend from base class SelectableItemBase

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
fun ExampleUsage() {
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
        startActions = listOf(SwipableAction(...)),
        endActions = listOf(SwipableAction(...)),
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



