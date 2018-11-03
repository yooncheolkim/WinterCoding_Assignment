package com.yooncheol.yoonimgsearch.data.source.flickr

import android.graphics.Bitmap
import com.yooncheol.yoonimgsearch.data.PhotoInfo
import com.yooncheol.yoonimgsearch.data.PhotoResponse
import io.reactivex.Observable

interface FlickrDataSource {
    fun getSearchPhoto(keyword: String, page : Int, perPage : Int): Observable<PhotoResponse>

    fun getDetailPhoto(photoId : String) : Observable<PhotoInfo>
}