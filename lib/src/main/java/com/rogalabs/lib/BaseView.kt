package com.rogalabs.lib

/**
 * Created by roga on 19/07/16.
 */
interface BaseView<T , E>{
    fun injectPresenter(googlePresenter: T, facebookPresenter: E)
}