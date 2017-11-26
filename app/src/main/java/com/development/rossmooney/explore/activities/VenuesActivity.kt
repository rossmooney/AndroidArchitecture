package com.development.rossmooney.explore.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.development.rossmooney.explore.R
import com.development.rossmooney.explore.viewModels.VenuesViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers



class VenuesActivity : AppCompatActivity() {
    private val viewModel: VenuesViewModel by lazy { VenuesViewModel(this) }
    private val disposable = CompositeDisposable()
    private val lv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.venuesList) }
    private var location: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_venues)

        //Setup recycler view
        lv.layoutManager = LinearLayoutManager(this)
        lv.adapter = VenuesAdapter() { item, index ->
            //Venue clicked
            Log.d("VenuesActivity", "$item at $index clicked")

            showPhotosScreen(item.venue.id, item.venue.name)
        }


    }


    override fun onResume() {
        super.onResume()

        //Get passed in value for location name
        val intentLocation = intent.extras?.get("location")
        if (intentLocation != null && intentLocation is String) {
            location = intentLocation
        }

        //Update data
        requestVenues()
    }

    override fun onPause() {
        super.onPause()

        disposable.clear()
    }


    private fun requestVenues() {
        //Ensure location isn't null, otherwise return
        val location = location?.let { it } ?: return

        title = "Exploring $location"

        //Tell viewmodel the location and observe venues response
        disposable.add(viewModel.getVenues(location)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ venues -> (lv.adapter as VenuesAdapter).updateData(venues) },
                        { throwable -> Log.e("VenuesActivity", "Unable to update venues", throwable) }))
    }

    private fun showPhotosScreen(venueId:String, venueName:String) {
        val intent = Intent(this, PhotosActivity::class.java)
        intent.putExtra("venueId", venueId)
        intent.putExtra("venueName", venueName)
        startActivity(intent)
    }
}
