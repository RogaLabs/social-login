package com.rogalabs.lib;

import com.rogalabs.lib.server.ResponseHandler
import org.json.JSONObject

/**
 * Created by roga on 08/07/16.
 * On MacBook Pro, Roga Labs.
 */
interface CommonCallback {
    fun onSuccess(success: JSONObject)
    fun onError(error: ResponseHandler.ErrorHandler)
}
