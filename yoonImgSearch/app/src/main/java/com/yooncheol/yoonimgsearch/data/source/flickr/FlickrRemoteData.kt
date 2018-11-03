package com.yooncheol.yoonimgsearch.data.source.flickr

import android.graphics.Bitmap
import com.yooncheol.yoonimgsearch.data.PhotoInfo
import com.yooncheol.yoonimgsearch.data.PhotoResponse
import com.yooncheol.yoonimgsearch.network.FlickrServiceInterface
import com.yooncheol.yoonimgsearch.network.createRetrofit
import io.reactivex.Observable

class FlickrRemoteData : FlickrDataSource {
    companion object {
        const val FLICKR_URL = "https://api.flickr.com/services/rest/"
    }

    private val flickrServiceInterface = createRetrofit(FlickrServiceInterface::class.java, FLICKR_URL)

    override fun getSearchPhoto(keyword: String, page: Int, perPage: Int): Observable<PhotoResponse>
        = flickrServiceInterface.getFlickrSearchPhotos(
            keyword = keyword,
            page = page,
            perPage = perPage)

    override fun getDetailPhoto(photoId: String): Observable<PhotoInfo>
        = flickrServiceInterface.getFlickrDetailPhoto(
            photo_id = photoId
    )
}