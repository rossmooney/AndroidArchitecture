package com.development.rossmooney.explore.repository

import com.development.rossmooney.explore.models.VenueModel
import com.development.rossmooney.explore.network.FourSquareApiService
import com.development.rossmooney.explore.network.FourSquareNetworkService
import io.reactivex.Observable
import models.PhotoModel


/**
 * Created by rossmooney on 11/18/17.
 */

object RepositoryProvider {
    fun provideVenueRepository() = VenueRepository(networkService = FourSquareNetworkService.create(FourSquareApiService.create()))
    fun providePhotoRepository() = PhotoRepository(networkService = FourSquareNetworkService.create(FourSquareApiService.create()))

}

class VenueRepository(val networkService: FourSquareNetworkService) {
    var cachedVenues = emptyList<VenueModel>()

    fun getVenues(location: String, limit: Int): Observable<List<VenueModel>> {

        if (cachedVenues.isEmpty()) {
            //Return server data
            return networkService.venueModels(location, limit).doOnNext { cachedVenues = it }
        } else {
            //Use cached data, grab server data and merge
            return Observable.just(cachedVenues)
                    .mergeWith(networkService.venueModels(location, limit))
                    .doOnNext { cachedVenues = it }
        }

    }
}

class PhotoRepository(val networkService: FourSquareNetworkService) {
    var cachedPhotos = emptyList<PhotoModel>()

    fun getPhotos(venueId: String, limit: Int): Observable<List<PhotoModel>> {
        if (cachedPhotos.isEmpty()) {
            //Return server data
            return networkService.photoModels(venueId, limit).doOnNext { cachedPhotos = it }
        } else {
            //Use cached data, grab server data and merge
            return Observable.just(cachedPhotos)
                    .mergeWith(networkService.photoModels(venueId, limit))
                    .doOnNext { cachedPhotos = it }
        }
    }

}