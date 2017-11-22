package com.development.rossmooney.explore.activities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.rossmooney.explore.R
import kotlinx.android.synthetic.main.text_row.view.*

/**
 * Created by rossmooney on 11/16/17.
 */

class LocationsAdapter constructor(private val locations: Array<String>, private val clickListener: (String, Int) -> Unit) : RecyclerView.Adapter<LocationsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.text_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : String = locations[position]

        //Set text on cell
        holder.itemView.row_text.text = item

        //Handle clicks with listener passed in to constructor
        holder.itemView.setOnClickListener { clickListener(item, position) }
    }

    override fun getItemCount() = locations.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}
