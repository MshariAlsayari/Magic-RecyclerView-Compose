package com.android.magic_recyclerview

abstract class SelectableItemBase(
    open var id:Long,
    open var rowType: RowType,
    open var selected:Boolean = false,
    open var selectable:Boolean = true
)

enum class RowType{
    ITEM,HEADER
}