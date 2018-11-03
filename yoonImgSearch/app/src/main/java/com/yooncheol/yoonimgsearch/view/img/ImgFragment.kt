package com.yooncheol.yoonimgsearch.view.img

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.yooncheol.yoonimgsearch.R
import com.yooncheol.yoonimgsearch.view.img.adapter.ImageRecyclerAdapter
import com.yooncheol.yoonimgsearch.data.source.flickr.FlickrRepository
import com.yooncheol.yoonimgsearch.view.detail.DetailActivity
import com.yooncheol.yoonimgsearch.view.img.presenter.ImgContract
import com.yooncheol.yoonimgsearch.view.img.presenter.ImgPresenter
import kotlinx.android.synthetic.main.fragment_img.*

class ImgFragment : Fragment() , ImgContract.View{
    val TAG = "ImgFragment"
    var searchText : String = "Seoul"
    private val imgPresenter : ImgPresenter by lazy{
        ImgPresenter(this@ImgFragment,
                FlickrRepository,
                imageRecyclerAdapter)
    }

    private val imageRecyclerAdapter : ImageRecyclerAdapter by lazy {
        ImageRecyclerAdapter(this@ImgFragment.context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
        = inflater?.inflate(R.layout.fragment_img,container,false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        imgPresenter.loadFlickrImage(searchText)

        recycler_view.run{
            adapter = imageRecyclerAdapter
            layoutManager = GridLayoutManager(this@ImgFragment.context,3)
            addOnScrollListener(recyclerViewOnScrollListener)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        recycler_view?.removeOnScrollListener(recyclerViewOnScrollListener)
    }

    override fun hideProgress() {
        progressBar.visibility = View.GONE
    }

    override fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    override fun showToast(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }

    override fun showLoadFail() {
        if(isDetached) return
        showToast("load fail.")
    }

    override fun showLoadFail(message: String) {
        if(isDetached) return
        showToast(message)
    }

    override fun getSearchText(searchText: String) {
        Log.d(TAG,"getSearchText : " + this.searchText)
        this.searchText = searchText
        imgPresenter.loadFlickrImage(searchText)
    }

    override fun goToDetailView(photoId: String, photoUrl : String) {
        if(isDetached) return
        var intent : Intent = Intent(context,DetailActivity::class.java)
        intent.putExtra("photoId", photoId)
        intent.putExtra("photoUrl" , photoUrl)
        startActivity(intent)
    }

    private val recyclerViewOnScrollListener = object :RecyclerView.OnScrollListener(){
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            //밑으로 스크롤 되면, 더 가져오기..
            val visibleItemCount = recyclerView?.childCount as Int
            val totalItemCount = imageRecyclerAdapter.itemCount
            val firstVisibleItem = (recyclerView.layoutManager as? GridLayoutManager)?.findFirstVisibleItemPosition() ?: 0

            if(!imgPresenter.isLoading && (firstVisibleItem + visibleItemCount) >= totalItemCount - 3){
                imgPresenter.loadFlickrImage(searchText)
            }
        }
    }
}