package com.yooncheol.yoonimgsearch.view.img.presenter

interface ImgContract {

    interface View{
        fun hideProgress()
        fun showProgress()

        fun showToast(message : String)

        fun showLoadFail()
        fun showLoadFail(message : String)

        fun getSearchText(searctText : String)

        fun goToDetailView(photoId : String, photoUrl : String)
    }

    interface Presenter{
        fun loadFlickrImage(keyWord : String)
    }
}