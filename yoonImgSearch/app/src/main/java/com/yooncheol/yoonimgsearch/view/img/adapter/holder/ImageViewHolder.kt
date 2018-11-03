package com.yooncheol.yoonimgsearch.view.img.adapter.holder

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.yooncheol.yoonimgsearch.R
import com.yooncheol.yoonimgsearch.data.Photo
import kotlinx.android.synthetic.main.item_image.view.*

class ImageViewHolder(context : Context , parent : ViewGroup?, onClickFunc : (Int) -> Unit)
    : RecyclerView.ViewHolder (LayoutInflater.from(context).inflate(R.layout.item_image,parent,false)){
    init{
        itemView.setOnClickListener {
            onClickFunc(adapterPosition)
        }
    }

    fun onBind(item : Photo){
        itemView.onBind(item)
    }

    private fun View.onBind(item : Photo){
        img_view.loadImage(item.getImageUrl())
    }
}