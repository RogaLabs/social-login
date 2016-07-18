package com.rogalabs.lib

/**
 * Created by roga on 13/07/16.
 */
interface LoginContract {
    interface View {
        fun inject()
    }
    interface GooglePresenter : BasePresenter {
        fun signIn(callback: Callback)
        fun signOut()
    }

    interface FacebookPresenter : BasePresenter {
        fun signIn(callback: Callback)
        fun signOut()
    }
}