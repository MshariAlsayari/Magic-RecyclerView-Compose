package com.android.magic_recyclerview

abstract class SelectableItemBase(
    open var id:Long,
    open var isSelected:Boolean = false,
    open var isSelectable:Boolean = true
)