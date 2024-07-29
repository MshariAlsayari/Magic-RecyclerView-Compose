package com.android.magicrecyclerview.model

import com.android.magic_recyclerview.SelectableItemBase


data class Anime (
    val anime_id:Int,
    val anime_name:String,
    val anime_img:String,
):SelectableItemBase(id = anime_id.toLong())