package com.development.rossmooney.explore

import com.development.rossmooney.explore.viewModels.LocationsViewModel
import com.development.rossmooney.explore.viewModels.VenuesViewModel
import org.junit.Test

import org.junit.Assert.*

/**
 * ViewModel Tests
 */
class LocationsViewModelTest {
    //Purpose: Ensure all 5 cities are returned from viewmodel
    @Test
    fun locations_areCorrect() {
        val viewModel = LocationsViewModel()

        val cities = viewModel.locations
        assertEquals(cities.count(), 5)
        assertEquals(cities[0], "San Francisco, CA")
        assertEquals(cities[1], "Atlanta, GA")
        assertEquals(cities[2], "New York, NY")
        assertEquals(cities[3], "Miami, FL")
        assertEquals(cities[4], "Chicago, IL")
    }
}

class VenuesViewModelTest {
    //Purpose: Ensure all 5 cities are returned from viewmodel
    @Test
    fun venues_areCorrect() {
        val viewModel = VenuesViewModel()

    }
}


