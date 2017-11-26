package com.development.rossmooney.explore.viewModels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.development.rossmooney.explore.network.FourSquareApiService
import com.development.rossmooney.explore.network.FourSquareNetworkService
import com.development.rossmooney.explore.repository.PhotoRepository
import com.development.rossmooney.explore.repository.VenueRepository
import models.PhotoModel

/**
 * Created by rossmooney on 11/20/17.
 */
class PhotosViewModel(private val context: Context) : ViewModel() {
    private var photos = arrayOf<PhotoModel>()

    private var venueRepository: VenueRepository = VenueRepository(FourSquareNetworkService(FourSquareApiService.create()), context)
    private var photoRepository: PhotoRepository = PhotoRepository(FourSquareNetworkService(FourSquareApiService.create()))

    fun getPhotos(venueId:String, limit: Int = 50) = photoRepository.getPhotos(venueId, limit)

    fun toggleVenueBookmark(venueId: String) {
        venueRepository.updateVenueBookmark(venueId, !venueIsBookmarked(venueId))
    }

    fun venueIsBookmarked(venueId: String) = venueRepository.isBookmarked(venueId)
}