package com.rogalabs.lib.common

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.rogalabs.lib.CommonCallback
import com.rogalabs.lib.LoginContract
import com.rogalabs.lib.server.ResponseHandler
import com.rogalabs.lib.server.restclient.CommonLoginClient
import org.json.JSONObject

/**
 * Created by cleylsonsouza on 20/09/16.
 * On MacBook Pro, Roga Labs.
 */
class CommonLoginPresenter(val view: LoginContract.View?) : LoginContract.CommonLoginPresenter {

    private var callback: CommonCallback? = null
    private var activity: FragmentActivity? = null

    override fun create(activity: FragmentActivity?) {
        this.activity = activity
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    override fun activityResult(requestCode: Int, resultCode: Int, data: Intent) {
    }

    override fun signIn(url: String, params: JSONObject, callback: CommonCallback) {
        this.callback = callback
        CommonLoginClient.getInstance(activity).createPOST(url, params,
                { result ->
                    callback.onSuccess(result)
                },
                { volleyError ->
                    val handler = ResponseHandler()
                    callback.onError(handler.errorHandler(volleyError))
                })
    }
}