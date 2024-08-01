package com.android.magicrecyclerview.model

import com.android.magic_recyclerview.RowType
import com.android.magic_recyclerview.SelectableItemBase


data class Anime (
    val animeId:Int,
    val animeName:String,
    val anumeImg:String,
):SelectableItemBase(id = animeId.toLong(), rowType = RowType.ITEM)