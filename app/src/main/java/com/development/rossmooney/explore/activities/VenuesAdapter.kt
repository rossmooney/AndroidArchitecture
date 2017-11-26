package com.development.rossmooney.explore.activities

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.development.rossmooney.explore.R
import com.development.rossmooney.explore.models.VenueDisplay
import com.development.rossmooney.explore.models.VenueModel
import kotlinx.android.synthetic.main.venue_row.view.*

/**
 * Created by rossmooney on 11/18/17.
 */

class VenuesAdapter constructor(private val clickListener: (VenueDisplay, Int) -> Unit) : RecyclerView.Adapter<VenuesAdapter.ViewHolder>() {
    var venues:List<VenueDisplay> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.venue_row, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = venues[position]

        //Set text on cell
        with(holder.itemView) {
            row_venueName.text = item.venue.name
            row_venueLocation.text = item.venue.location
            row_venueCategories.text = item.venue.categories.joinToString("," )
            row_venueBookmarked.visibility = if (item.bookmarked) View.VISIBLE else View.INVISIBLE
            if (item.bookmarked) {
                Log.d("","BOOKMARKED: ${item.venue.name}")
            } else {
                Log.d("","NOT BOOKMARKED: ${item.venue.name}")

            }
        }

        //Handle clicks with listener passed in to constructor
        holder.itemView.setOnClickListener { clickListener(item, position) }
    }

    override fun getItemCount() = venues.size

    fun updateData(venues: List<VenueDisplay>) {
        this.venues = venues
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

}