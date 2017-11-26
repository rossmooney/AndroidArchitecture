package com.development.rossmooney.explore.viewModels

import android.arch.lifecycle.ViewModel
import android.content.Context
import com.development.rossmooney.explore.models.VenueDisplay
import com.development.rossmooney.explore.models.VenueModel
import com.development.rossmooney.explore.network.FourSquareApiService
import com.development.rossmooney.explore.network.FourSquareNetworkService
import com.development.rossmooney.explore.repository.VenueRepository
import io.reactivex.Observable

/**
 * Created by rossmooney on 11/16/17.
 */
class VenuesViewModel(val context: Context) : ViewModel() {
    private var venues = arrayOf<VenueModel>()
    private var repository:VenueRepository = VenueRepository(FourSquareNetworkService(FourSquareApiService.create()), context)

    fun getVenues(location:String, limit: Int = 25): Observable<List<VenueDisplay>> {
        val result = repository.getVenues(location, limit)
        val returnVal =  result.map { it.map { VenueDisplay(it, repository.isBookmarked(it.id)) } }

        return returnVal
    }
}

