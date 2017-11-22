package com.development.rossmooney.explore

import com.development.rossmooney.explore.network.FourSquareNetworkService
import com.development.rossmooney.explore.repository.VenueRepository
import com.development.rossmooney.explore.models.VenueModel
import io.reactivex.Observable
import org.junit.Before
import org.junit.Test
import org.junit.Assert.*
import org.mockito.Mockito.*

/**
 * Created by rossmooney on 11/21/17.
 */
class VenueRepositoryTest {

    lateinit var venueRepository: VenueRepository
    lateinit var fourSquareNetwork: FourSquareNetworkService

    @Before
    fun setup() {
        fourSquareNetwork = mock(FourSquareNetworkService::class.java)

        venueRepository = VenueRepository(fourSquareNetwork)
    }

    @Test
    fun testHandleEmptyResponse() {
        `when`(fourSquareNetwork.venueModels("Atlanta, GA", 25)).thenReturn(Observable.just(emptyList<VenueModel>()))

        venueRepository.getVenues("Atlanta, GA", limit = 25).test()
                .assertValue { it.isEmpty() }
    }

    @Test
    fun testTestHandleAPIReturnWithNoCache() {
        `when`(fourSquareNetwork.venueModels("Atlanta, GA", 25)).thenReturn(Observable.just(listOf(testVenue())))

        venueRepository.getVenues("Atlanta, GA", limit = 25).test()
                .assertValueCount(1)
                .assertValue { it.size == 1 }
    }

    @Test
    fun testHandleAPIReturnWithCache() {
        val cachedData = listOf(testVenue())
        val apiData = listOf(testVenue(), testVenue())
        `when`(fourSquareNetwork.venueModels("Atlanta, GA", 25)).thenReturn(Observable.just(apiData))
        venueRepository.cachedVenues = cachedData

        venueRepository.getVenues("Atlanta, GA", 25).test()
                //Both cached & API data delivered
                .assertValueCount(2)
                //First cache data delivered
                .assertValueAt(0, { it == cachedData })
                //Secondly api data delivered
                .assertValueAt(1, { it == apiData })
    }

    @Test
    fun testCacheMatchesAPIReturn() {
        val apiData = listOf(testVenue(), testVenue())
        `when`(fourSquareNetwork.venueModels("Atlanta, GA", 25)).thenReturn(Observable.just(apiData))

        venueRepository.getVenues("Atlanta, GA", 25).test()

        assertEquals(venueRepository.cachedVenues, apiData)
    }

    fun testVenue() = VenueModel(UUID.randomUUID().toString(),
            "Test Venue",
            "?",
            "1234 Fake St.",
            listOf("Category1", "Category2"))
}