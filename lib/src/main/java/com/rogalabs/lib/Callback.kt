package com.rogalabs.lib;

import com.rogalabs.lib.model.SocialUser

/**
 * Created by roga on 08/07/16.
 */
interface Callback {
    fun onSuccess(socialUser: SocialUser)
    fun onError(throwable: Throwable)
}
