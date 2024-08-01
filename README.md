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
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * view - the data view holder.
 * onItemClicked - callback when the swappable item's been clicked
 * onItemCollapsed - callback when the swappable item's been collapsed
 * onItemExpanded - callback when the swappable item's been expanded
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyView if the list is empty.
 * startActions - list of actions if it is empty no swipe .
 * endActions - list of actions if it is empty no swipe .
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * scrollTo - scroll to item default is 0.
 * style - style is class to add style on background of actions container
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
```


## VerticalSelect
```

 /***
 * modifier - the modifier to apply to this layout.
 * list -  list of data.
 * selectedItemsList - a list contains selected items
 * view - the data view holder.
 * onItemClicked - callback when a item's been clicked
 * onItemLongClicked - callback when a item's been clicked
 * isMultiSelectionMode
 * dividerView - (optional) divider between items.
 * emptyView - (optional) emptyView if the list is empty.
 * actions - list of actions.
 * isLoading - show loading content progress.
 * loadingProgress - (optional) if null will show CircularProgressIndicator().
 * isRefreshing - show progress of the swipeRefreshLayout.
 * onRefresh - (optional) callback when the swipeRefreshLayout swapped if null the list will wrapped without the swipeRefreshLayout .
 * scrollTo - scroll to item default is 0.
 * style - style is a class to add style all components in a list
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
    actions: List<MenuAction<T>> = listOf(),
    isMultiSelectionMode: Boolean = false,
    style: SelectableListStyle = SelectableListStyle.Default,
    scrollTo: Int = 0,
    isLoading: Boolean = false,
    isRefreshing: Boolean = false,
    onRefresh: (() -> Unit)? = null,
) 
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



