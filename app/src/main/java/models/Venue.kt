package com.development.rossmooney.explore.models

/**
 * Created by rossmooney on 11/16/17.
 */
data class VenueModel(val id: String,
                      val name: String,
                      val thumbnailLocation: String,
                      val location: String,
                      val categories: List<String>) {
}
