package com.yooncheol.yoonimgsearch.view.img

import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import com.yooncheol.yoonimgsearch.R
import com.yooncheol.yoonimgsearch.util.replace
import kotlinx.android.synthetic.main.activity_img.*


class ImgActivity : AppCompatActivity() {
    val TAG = "imgActivity"
    private val imgFragment : ImgFragment by lazy {
        ImgFragment()
    }

    companion object {
        const val REQUEST_PERMISSION = 1
    }

    override fun onStart() {
        super.onStart()
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_PERMISSION)
        } else{
            savePrefPermission()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when(requestCode){
            REQUEST_PERMISSION -> if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                savePrefPermission()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_img)

        replace(R.id.container,imgFragment)

        search_button.setOnClickListener{
            imgFragment.getSearchText(search_src_text.text.toString())
        }
    }

    private fun savePrefPermission(){
        var pref : SharedPreferences = getSharedPreferences("pref", Context.MODE_PRIVATE)
        var editor : SharedPreferences.Editor = pref.edit()
        editor.putBoolean("canWrite",true)
        editor.commit()
    }
}
