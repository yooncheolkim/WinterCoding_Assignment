package com.yooncheol.yoonimgsearch.network

import com.yooncheol.yoonimgsearch.BuildConfig
import com.yooncheol.yoonimgsearch.data.PhotoInfo
import com.yooncheol.yoonimgsearch.data.PhotoResponse
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.Query

interface FlickrServiceInterface {
    @POST("?method=flickr.photos.search&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1")
    fun getFlickrSearchPhotos(
            @Query("text") keyword : String,
            @Query("safe_serach") safeSearch : Int = 1,
            @Query("page") page : Int,
            @Query("per_page") perPage : Int
    ) : Observable<PhotoResponse>


    @POST("?method=flickr.photos.getInfo&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1")
    fun getFlickrDetailPhoto(
            @Query("photo_id") photo_id : String
    ) : Observable<PhotoInfo>
}