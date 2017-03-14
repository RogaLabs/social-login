package com.rogalabs.lib

/**
 * Created by roga on 19/07/16.
 */
interface BaseView<T, E, C> {
    fun injectPresenter(googlePresenter: T, facebookPresenter: E, commonLoginPresenter: C)
}