package com.development.rossmooney.explore.activities

import android.util.Log
import com.development.rossmooney.explore.models.VenueModel
import io.reactivex.Observable
import models.PhotoModel


/**
 * Created by rossmooney on 11/18/17.
 */

object RepositoryProvider {
    fun provideVenueRepository() = VenueRepository(apiService = FourSquareApiService.create())
    fun providePhotoRepository() = PhotoRepository(apiService = FourSquareApiService.create())

}

class VenueRepository(val apiService: FourSquareApiService) {

    fun getVenues(location: String, limit: Int): Observable<List<VenueModel>> {
        //Call API
        val response = apiService.venues(near = location, limit = limit)

        //Transform stream from API response to stream of list of VenueModels
        return response.map {
            it.response.groups.first().items.map { responseVenueToVenueModel(it.venue) }
        }
    }

    //Convert the API response item to a VenueModel
    private fun responseVenueToVenueModel(responseVenue: VenuesResponse.Venue): VenueModel {

        return VenueModel(id = responseVenue.id,
                name = responseVenue.name,
                thumbnailLocation = "",
                location = responseVenue.location.displayText(),
                categories = arrayOf("", ""))

    }
}

class PhotoRepository(val apiService: FourSquareApiService) {

    fun getPhotos(venueId: String, limit: Int): Observable<List<PhotoModel>> {
        //Call API
        val response = apiService.photos(venueId = venueId, limit = limit)

        //Transform stream from API response to stream of list of PhotoModels
        return response.map {
            it.response.photos.items.map { responsePhotoToPhotoModel(it) }
        }
    }

    //Convert the API response item to a PhotoModel
    private fun responsePhotoToPhotoModel(responsePhoto: PhotosResponse.Photo): PhotoModel {
        return PhotoModel(id = responsePhoto.id,
                prefix = responsePhoto.prefix,
                suffix = responsePhoto.suffix)

}

}