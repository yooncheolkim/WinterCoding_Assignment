package com.yooncheol.yoonimgsearch.view.detail.presenter

import android.util.Log
import com.yooncheol.yoonimgsearch.data.source.flickr.FlickrRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class DetailPresenter(val view : DetailContract.View,
                      private val repository : FlickrRepository) : DetailContract.Presenter {
    val TAG = "DetailPresenter"

    override fun loadDetailInfo(photoId: String) {
        repository.getDetailPhoto(photoId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            view.updateItem(it.photo.getImageUrl())
                        },
                        onComplete = {
                            Log.d(TAG, "onCompleted.")
                        },
                        onError = {
                            Log.d(TAG, "photo upload fail.")
                            view.showToast("upload fail.")
                            view.goToParentView()
                        }
                )
    }


}