package com.development.rossmooney.explore.repository

import android.content.Context
import android.util.Log
import android.util.Log.i
import com.development.rossmooney.explore.models.VenueModel
import com.development.rossmooney.explore.network.FourSquareApiService
import com.development.rossmooney.explore.network.FourSquareNetworkService
import io.reactivex.Observable
import models.PhotoModel


/**
 * Created by rossmooney on 11/18/17.
 */
//
//object RepositoryProvider {
//    fun provideVenueRepository() = VenueRepository(networkService = FourSquareNetworkService.create(FourSquareApiService.create()))
//    fun providePhotoRepository() = PhotoRepository(networkService = FourSquareNetworkService.create(FourSquareApiService.create()))
//
//}

class VenueRepository(private val networkService: FourSquareNetworkService, val context: Context) {
    private var bookmarkedVenues:MutableSet<String> = mutableSetOf()
    var cachedVenues = emptyList<VenueModel>()

    init {
        loadBookmarks()
    }

    fun getVenues(location: String, limit: Int): Observable<List<VenueModel>> {

        if (cachedVenues.isEmpty()) {
            //Return server data
            return networkService.venueModels(location, limit).doOnNext { cachedVenues = it; Log.d("Cached","CachedVenues.count = ${cachedVenues.count()}") }
        } else {
            //Use cached data, grab server data and merge
            return Observable.just(cachedVenues)
                    .mergeWith(networkService.venueModels(location, limit))
                    .doOnNext { cachedVenues = it }
        }
    }

    fun updateVenueBookmark(venueId: String, isBookmarked: Boolean) {
        if (isBookmarked) {
            if (!bookmarkedVenues.contains(venueId)) {
                bookmarkedVenues.add(venueId)
            }
        } else {
            if (bookmarkedVenues.contains(venueId)) {
                bookmarkedVenues.remove(venueId)
            }
        }
        saveBookmarks()
    }

    fun isBookmarked(venueId: String): Boolean = bookmarkedVenues.contains(venueId)

    fun loadBookmarks() {
        val prefs = context.getSharedPreferences("bookmarks.prefs", 0)
        bookmarkedVenues = prefs.getStringSet("venues", HashSet<String>())
    }

    fun saveBookmarks() {
        val prefs = context.getSharedPreferences("bookmarks.prefs", 0)
        val editor = prefs!!.edit()
        editor.putStringSet("venues", bookmarkedVenues)
        editor.apply()
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