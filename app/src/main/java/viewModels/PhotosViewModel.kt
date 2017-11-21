package viewModels

import android.arch.lifecycle.ViewModel
import com.development.rossmooney.explore.activities.RepositoryProvider
import com.development.rossmooney.explore.models.VenueModel
import io.reactivex.Observable
import models.PhotoModel

/**
 * Created by rossmooney on 11/20/17.
 */
class PhotosViewModel : ViewModel() {
    private var photos = arrayOf<PhotoModel>()

    fun getPhotos(venueId:String, limit: Int = 50): Observable<List<PhotoModel>> {
        val repository = RepositoryProvider.providePhotoRepository()
        return repository.getPhotos(venueId, limit)
    }
}