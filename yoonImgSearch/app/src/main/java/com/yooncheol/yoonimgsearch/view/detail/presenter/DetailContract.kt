package com.yooncheol.yoonimgsearch.view.detail.presenter


interface DetailContract {

    interface View{
        fun updateItem(imageUrl : String)
        fun goToParentView()
        fun showToast(message : String)
    }

    interface Presenter{
        fun loadDetailInfo(photoId : String)
    }
}