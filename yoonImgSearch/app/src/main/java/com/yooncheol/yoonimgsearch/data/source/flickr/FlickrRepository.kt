package com.yooncheol.yoonimgsearch.data.source.flickr

import com.yooncheol.yoonimgsearch.data.PhotoInfo
import com.yooncheol.yoonimgsearch.data.PhotoResponse
import io.reactivex.Observable

object FlickrRepository : FlickrDataSource {
    private val flickrRemoteData = FlickrRemoteData()

    override fun getSearchPhoto(keyword: String, page: Int, perPage: Int): Observable<PhotoResponse>
            = flickrRemoteData.getSearchPhoto(keyword, page, perPage)


    override fun getDetailPhoto(photoId: String): Observable<PhotoInfo>
            = flickrRemoteData.getDetailPhoto(photoId)
}