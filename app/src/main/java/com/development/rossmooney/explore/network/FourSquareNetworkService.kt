package com.development.rossmooney.explore.network

import com.development.rossmooney.explore.models.VenueModel
import io.reactivex.Observable
import models.PhotoModel

/**
 * Created by rossmooney on 11/21/17.
 */
class FourSquareNetworkService(private val apiService: FourSquareApiService) {
    companion object Factory {
        fun create(apiService: FourSquareApiService) = FourSquareNetworkService(apiService)
    }

    //Venue transformation
    //Makes the API call and transforms the response to a VenueModel object
    fun venueModels(location: String, limit: Int): Observable<List<VenueModel>> {
        //Call API
        val response = apiService.venues(near = location, limit = limit)

        //Transform stream from API response to stream of list of VenueModels
        return response.map {
            it.response.groups.first().items.map { responseVenueToVenueModel(it.venue) }
        }
    }

    //Photo transformation
    //Makes the API call and transforms the response to a VenueModel object
    fun photoModels(venueId: String, limit: Int): Observable<List<PhotoModel>> {
        //Call API
        val response = apiService.photos(venueId = venueId, limit = limit)

        //Transform stream from API response to stream of list of PhotoModels
        return response.map {
            it.response.photos.items.map { responsePhotoToPhotoModel(it) }
        }
    }

    //Convert the API response item to a VenueModel
    private fun responseVenueToVenueModel(responseVenue: VenuesResponse.Venue): VenueModel {

        return VenueModel(id = responseVenue.id,
                name = responseVenue.name,
                thumbnailLocation = "",
                location = responseVenue.location.displayText(),
                categories = responseVenue.categories.map { it.name })
    }

    //Convert the API response item to a models.PhotoModel
    private fun responsePhotoToPhotoModel(responsePhoto: PhotosResponse.Photo): PhotoModel {
        return PhotoModel(id = responsePhoto.id,
                prefix = responsePhoto.prefix,
                suffix = responsePhoto.suffix)
    }
}