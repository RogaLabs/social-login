package com.rogalabs.lib;

import com.rogalabs.lib.server.ResponseHandler

/**
 * Created by roga on 08/07/16.
 */
interface CommonCallback {
    fun onSuccess(success: String)
    fun onError(error: ResponseHandler.ErrorHandler)
}
