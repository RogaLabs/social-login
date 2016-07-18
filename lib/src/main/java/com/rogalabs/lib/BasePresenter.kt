package com.rogalabs.lib

import android.content.Intent
import android.support.v4.app.FragmentActivity

/**
 * Created by roga on 13/07/16.
 */
interface BasePresenter{
    fun start(activity: FragmentActivity? = null)
    fun activityResult(requestCode: Int , resultCode: Int, data: Intent)
}