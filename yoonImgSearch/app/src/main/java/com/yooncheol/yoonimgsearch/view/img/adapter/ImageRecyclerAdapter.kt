package com.yooncheol.yoonimgsearch.view.img.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.yooncheol.yoonimgsearch.view.img.adapter.contract.ImageAdapterContract
import com.yooncheol.yoonimgsearch.view.img.adapter.holder.ImageViewHolder
import com.yooncheol.yoonimgsearch.data.Photo

class ImageRecyclerAdapter(private val context : Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() , ImageAdapterContract.Model{

    private val list = mutableListOf<Photo>()

    override lateinit var onClickFunc: ((Int) -> Unit)

    override fun onBindViewHolder(holder : RecyclerView.ViewHolder, position: Int) {
        (holder as? ImageViewHolder)?.onBind(list[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ImageViewHolder(context,parent, onClickFunc)
    }

    override fun getItemCount() = list.size

    override fun addItems(imageData: Photo) {
        list.add(imageData)
    }

    override fun clearItem() {
        list.clear()
    }

    override fun getItem(position: Int): Photo = list[position]

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }


}