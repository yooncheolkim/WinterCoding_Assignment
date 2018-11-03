package com.yooncheol.yoonimgsearch.view.img.adapter.contract

import com.yooncheol.yoonimgsearch.data.Photo

interface ImageAdapterContract {
    interface Model{
        var onClickFunc : (Int) -> Unit

        fun notifyAdapter()

        fun addItems(imageData : Photo)

        fun clearItem()

        fun getItem(position : Int) : Photo
    }
}