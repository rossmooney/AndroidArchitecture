package com.development.rossmooney.explore.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.rossmooney.explore.R
import com.development.rossmooney.explore.utility.loadUrl
import kotlinx.android.synthetic.main.photo_item.view.*
import models.PhotoModel

/**
 * Created by rossmooney on 11/18/17.
 */

class PhotosAdapter constructor(private val clickListener: (PhotoModel, Int) -> Unit) : RecyclerView.Adapter<PhotosAdapter.ViewHolder>() {
    var photos:List<PhotoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.photo_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = photos[position]

        //Set text on cell
        holder.itemView.photo_imageView.loadUrl("${item.prefix}300x300${item.suffix}")

        //Handle clicks with listener passed in to constructor
        holder.itemView.setOnClickListener { clickListener(item, position) }
    }

    override fun getItemCount() = photos.size

    fun updateData(photos: List<PhotoModel>) {
        this.photos = photos
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
