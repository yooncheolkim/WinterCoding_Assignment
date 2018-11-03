package com.yooncheol.yoonimgsearch.view.img.presenter

import android.util.Log
import com.yooncheol.yoonimgsearch.view.img.adapter.contract.ImageAdapterContract
import com.yooncheol.yoonimgsearch.data.source.flickr.FlickrRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers

class ImgPresenter(val view : ImgContract.View,
                   private val flickrRepository : FlickrRepository,
                   private val imageRecyclerModel : ImageAdapterContract.Model) : ImgContract.Presenter{
    val TAG = "ImgPresenter"
    var isLoading = false
    var prevKeyWord : String = "Seoul"

    private val perPage = 50
    private var page = 0

    init{
        imageRecyclerModel.onClickFunc = {position ->
            Log.d(TAG,"position num $position is clicked.")
            view.goToDetailView(imageRecyclerModel.getItem(position).id, imageRecyclerModel.getItem(position).getImageUrl())
        }
    }

    override fun loadFlickrImage(keyWord : String) {
        isLoading = true
        view.showProgress()
        //다른 검색어로 검색 했을때
        if(prevKeyWord != keyWord){
            prevKeyWord = keyWord
            page = 0
            imageRecyclerModel.clearItem()
            imageRecyclerModel.notifyAdapter()
        }

        flickrRepository.getSearchPhoto(keyWord,++page,perPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeBy(
                        onNext = {
                            page = it.photos.page
                            it.photos.photo.forEach{
                                imageRecyclerModel.addItems(it)
                            }
                            imageRecyclerModel.notifyAdapter()
                        },
                        onComplete = {
                            Log.d(TAG,"loadFlickrImage completed.")
                            view.hideProgress()
                            isLoading = false
                        },
                        onError = {
                            Log.d(TAG,"loadFlickrImage onError() :")
                            view.hideProgress()
                            view.showLoadFail()
                            isLoading = false
                        }
                )
    }
}