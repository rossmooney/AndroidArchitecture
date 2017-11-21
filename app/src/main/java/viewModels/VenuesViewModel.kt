package com.development.rossmooney.explore.viewModels

import android.arch.lifecycle.ViewModel
import com.development.rossmooney.explore.activities.RepositoryProvider
import com.development.rossmooney.explore.models.VenueModel
import io.reactivex.Observable

/**
 * Created by rossmooney on 11/16/17.
 */
class VenuesViewModel : ViewModel() {
    private var venues = arrayOf<VenueModel>()

    fun getVenues(location:String, limit: Int = 25): Observable<List<VenueModel>> {
        val repository = RepositoryProvider.provideVenueRepository()
        return repository.getVenues(location, limit)
    }
}

