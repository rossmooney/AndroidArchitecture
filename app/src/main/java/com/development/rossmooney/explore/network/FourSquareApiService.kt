package com.development.rossmooney.explore.network

import io.reactivex.Observable
import okhttp3.OkHttpClient
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import okhttp3.Interceptor
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.http.Path

interface FourSquareApiService {

    //Explore endpoint
    //Returns an observable venue response
    @GET("venues/explore")
    fun venues(@Query("near") near: String,
               @Query("limit") limit: Int): Observable<VenuesResponse.Response>


    //Photos endpoint
    //Returns an observable of a list of photos for the specified venue
    @GET("venues/{venueId}/photos")
    fun photos(@Path("venueId") venueId:String,
               @Query("limit") limit: Int): Observable<PhotosResponse.Response>


    //Factory to setup Retrofit for FourSquareAPI
    companion object Factory {
        fun create(): FourSquareApiService {
            val retrofit = Retrofit.Builder()
                    .client(customHttpClient())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl("https://api.foursquare.com/v2/")
                    .build()

            return retrofit.create(FourSquareApiService::class.java);
        }

        //Creates a custom http client that handles api keys, versioning, and logging
        fun customHttpClient(): OkHttpClient {
            val httpClient = OkHttpClient.Builder()
            val interceptor = Interceptor { chain ->
                val original = chain.request()

                val url = original.url().newBuilder()
                        .addQueryParameter("client_id", "WVUURCLQ1SMRZONZ4JPOITMN2ZKYJODFAVSKITKWHJU5CCYM")
                        .addQueryParameter("client_secret", "YTZHMRRXUMCEX0NQTQITMZY0TO2TD2TCZPHLC1OZS3X0DNDO")
                        .addQueryParameter("v", "20170801")
                        .build()

                // Create new request with the updated URL
                val requestBuilder = original.newBuilder().url(url)
                chain.proceed(requestBuilder.build())
            }
            httpClient.addInterceptor(interceptor)

            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(loggingInterceptor)

            return httpClient.build()
        }
    }
}