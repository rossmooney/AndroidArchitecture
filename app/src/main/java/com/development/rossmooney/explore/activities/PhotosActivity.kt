package com.development.rossmooney.explore.activities

import android.content.Context
import android.os.Bundle

import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.development.rossmooney.explore.R

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.development.rossmooney.explore.viewModels.PhotosViewModel
import android.support.v7.widget.GridLayoutManager
import android.view.Menu
import android.view.MenuItem


class PhotosActivity : AppCompatActivity() {
    private val viewModel: PhotosViewModel by lazy { PhotosViewModel(this) }
    private val disposable = CompositeDisposable()
    private val lv: RecyclerView by lazy { findViewById<RecyclerView>(R.id.photosList) }
    private var venueId: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photos)

        //Setup recycler view
        val lv = findViewById<RecyclerView>(R.id.photosList)
        lv.layoutManager = GridLayoutManager(this, Utility.calculateNoOfColumns(this))
        lv.adapter = PhotosAdapter() { item, index ->
            //Photo clicked
            Log.d("PhotosActivity", "$item at $index clicked")
        }


    }

    override fun onResume() {
        super.onResume()

        //Get passed in value for location name
        val intentVenueId = intent.extras?.get("venueId")
        if (intentVenueId != null && intentVenueId is String) {
            venueId = intentVenueId
        }

        val venueName = intent.extras?.get("venueName")
        if (venueName != null) {
            title = "Photos of $venueName"
        }

        //Update data
        requestPhotos()
    }

    override fun onPause() {
        super.onPause()

        disposable.clear()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_photos, menu)

        updateBookmarkIcon(menu.findItem(R.id.action_bookmark))
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem):Boolean {
        when (item.itemId) {
            R.id.action_bookmark -> toggleBookmark(item)
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun toggleBookmark(item: MenuItem) {
        val venue = venueId
        if (venue != null) {
            viewModel.toggleVenueBookmark(venue)
            updateBookmarkIcon(item)
        }
    }

    private fun updateBookmarkIcon(item: MenuItem) {
        val venue = venueId
        if (venue != null) {
            if (viewModel.venueIsBookmarked(venue)) {
                item.setIcon(R.drawable.ic_bookmark_white_24dp)
            } else {
                item.setIcon(R.drawable.ic_bookmark_border_white_24dp)
            }
        }
    }

    private fun requestPhotos() {
        //Ensure location isn't null, otherwise return
        val venueId = venueId?.let { it } ?: return

        //Tell viewmodel the venueId and observe photos response
        disposable.add(viewModel.getPhotos(venueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ photos -> (lv.adapter as PhotosAdapter).updateData(photos) },
                        { throwable -> Log.e("PhotosActivity", "Unable to update photos", throwable) }))
    }
}

object Utility {
    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.getResources().getDisplayMetrics()
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 90).toInt()
    }
}