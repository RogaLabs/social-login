package com.rogalabs.lib

import android.content.Intent
import android.support.v4.app.FragmentActivity

/**
 * Created by roga on 13/07/16.
 */
interface BasePresenter {
    fun create(activity: FragmentActivity?)
    fun pause()
    fun destroy()
    fun activityResult(requestCode: Int, resultCode: Int, data: Intent)
}