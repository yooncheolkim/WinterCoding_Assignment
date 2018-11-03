package com.yooncheol.yoonimgsearch.util

import android.support.annotation.IdRes
import android.support.v7.app.AppCompatActivity

fun AppCompatActivity.replace(@IdRes frameId: Int, fragment: android.support.v4.app.Fragment, tag: String? = null) {
    supportFragmentManager.beginTransaction().replace(frameId, fragment, tag).commit()
}