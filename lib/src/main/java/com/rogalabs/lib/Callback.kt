package com.rogalabs.lib;

/**
 * Created by roga on 08/07/16.
 */
interface Callback {
    fun onSuccess(socialUser: SocialUser)
    fun onError(throwable: Throwable)
}
