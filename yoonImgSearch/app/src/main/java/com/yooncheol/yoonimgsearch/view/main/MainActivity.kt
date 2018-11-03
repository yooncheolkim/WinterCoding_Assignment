package com.yooncheol.yoonimgsearch.view.main

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.Window
import android.view.WindowManager
import com.yooncheol.yoonimgsearch.R
import com.yooncheol.yoonimgsearch.view.img.ImgActivity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.SingleSubject
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    val TAG = "MainActivity"
    private var mDisposables : CompositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_main)
        waitTime()
    }


    private fun waitTime(){
        var result : SingleSubject<Boolean> = SingleSubject.create()
        result.onSuccess(true)

        var disposable : Disposable = result.delay(1300L,TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { it : Boolean ->
                    if(it) gotoImgActivity() else failGotoImgActivity()
                }
        mDisposables.add(disposable)
    }

    private fun gotoImgActivity(){
        Log.d(TAG,"gotoImgActivity")

        var intent = Intent(this, ImgActivity::class.java)
        ContextCompat.startActivity(this,intent,null)
        finish()
    }

    private fun failGotoImgActivity(){
        Log.d(TAG,"fail go to next Acitivty.")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() : ")
        mDisposables.dispose()
    }
}
