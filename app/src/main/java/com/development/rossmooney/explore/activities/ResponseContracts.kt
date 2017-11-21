import android.location.Location

//package com.development.rossmooney.explore.activities
//
///**
// * Created by rossmooney on 11/18/17.
// */
//

object VenuesResponse {
    data class Response(val response:ResponseData)
    data class ResponseData(val groups:Array<Groups>, val headerFullLocation:String)
    data class Groups(val type:String, val items:Array<Item>)
    data class Item(val venue:Venue)
    data class Venue(val id:String, val name:String, val location: Location, val categories:Array<Category>, val photos:ThumbnailPhotos.Photos)

    data class Location(val address:String?, val lat:Double?, val lng:Double?, val city:String, val state:String) {
        //Return address, if available, or lat/long coordinates
        fun displayText():String {
            if (address != null) {
                return address
            } else if (lat != null && lng != null) {
                return "($lat, $lng)"
            }
            return ""
        }
    }


    data class Category(val id:String, val name:String)
}

object ThumbnailPhotos {
    data class Photos(val groups:Array<Group>)
    data class Group(val type:String, val items:Array<Photo>)
    data class Photo(val id:String, val prefix:String, val suffix:String)
}

object PhotosResponse {
    data class Response(val response:ResponseData)
    data class ResponseData(val photos:PhotosResponseData)
    data class PhotosResponseData(val items:Array<Photo>)
    data class Photo(val id:String, val prefix:String, val suffix: String)
}