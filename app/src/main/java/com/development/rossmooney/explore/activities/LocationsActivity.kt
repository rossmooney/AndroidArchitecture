package com.development.rossmooney.explore.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.AdapterView
import com.development.rossmooney.explore.R
import android.widget.ListView
import com.development.rossmooney.explore.viewModels.LocationsViewModel


class LocationsActivity : AppCompatActivity() {
    private val viewModel by lazy { LocationsViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_locations)

        val lv = findViewById<RecyclerView>(R.id.locationList)
        lv.layoutManager = LinearLayoutManager(this)
        lv.adapter = LocationsAdapter(viewModel.locations) { item, index ->
            Log.d("LocationsActivity", "$item at $index clicked")

            showVenuesScreen(item)
        }
    }

    //Displays the venues activity for the selected location
    private fun showVenuesScreen(location:String) {
        val intent = Intent(this, VenuesActivity::class.java)
        intent.putExtra("location", location)
        startActivity(intent)
    }

}





