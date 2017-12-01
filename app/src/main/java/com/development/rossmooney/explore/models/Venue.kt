package com.development.rossmooney.explore.models

data class VenueModel(val id: String,
                      val name: String,
                      val thumbnailLocation: String?,
                      val location: String,
                      val categories: List<String>) {
}

data class VenueDisplay(val venue: VenueModel, val bookmarked: Boolean)