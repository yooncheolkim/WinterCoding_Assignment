package com.yooncheol.yoonimgsearch.view.detail

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.yooncheol.yoonimgsearch.R
import com.yooncheol.yoonimgsearch.data.source.flickr.FlickrRepository
import com.yooncheol.yoonimgsearch.view.detail.presenter.DetailContract
import com.yooncheol.yoonimgsearch.view.detail.presenter.DetailPresenter
import kotlinx.android.synthetic.main.activity_detail.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import android.os.Build



class DetailActivity : AppCompatActivity() , DetailContract.View{
    val TAG = "DetailActivity"
    lateinit var photoId : String
    private val detailPresenter : DetailPresenter by lazy{
        DetailPresenter(this@DetailActivity,
                FlickrRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var photoId = intent.getStringExtra("photoId")
        this.photoId = photoId
        detailPresenter.loadDetailInfo(photoId)

        save_button.setOnClickListener(clickListener)
    }

    override fun updateItem(imageUrl: String) {
        detail_image.loadImage(imageUrl)
    }

    override fun goToParentView() {
        onBackPressed()
    }

    override fun showToast(message: String) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }

    private var clickListener  = View.OnClickListener {
        var pref : SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var canWrite = pref.getBoolean("canWrite",false)
        if(!canWrite) showToast("permission denied.")
        else{
            var bitmap = (detail_image.drawable as BitmapDrawable).bitmap

            var storagePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString()
            var storeDir = File(storagePath)
            var filename = "$photoId.jpg"

            var file = File(storeDir,filename)
            if(file.exists()) file.delete()

            try{
                var outputStream = FileOutputStream(file)
                bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream)
                outputStream.flush()
                outputStream.close()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    val scanIntent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE)
                    val contentUri = Uri.fromFile(file)
                    scanIntent.data = contentUri
                    sendBroadcast(scanIntent)
                } else {
                    val intent = Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory()))
                    sendBroadcast(intent)
                }
                Log.d(TAG, "save successful.")
                showToast("save successful.")
            } catch (e : IOException){
                e.printStackTrace()
                Log.d(TAG,"save failed.")
                showToast("save failed.")
            }
        }
    }
}
