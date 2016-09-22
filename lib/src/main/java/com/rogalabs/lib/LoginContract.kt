package com.rogalabs.lib

import com.rogalabs.lib.facebook.LoginFacebookPresenter
import com.rogalabs.lib.google.LoginGooglePresenter
import org.json.JSONObject

/**
 * Created by roga on 13/07/16.
 */
interface LoginContract {

    interface View : BaseView<LoginGooglePresenter,
            LoginFacebookPresenter,
            CommonLoginPresenter> {
        fun showProgress()
        fun hideProgress()
    }

    interface GooglePresenter : BasePresenter {
        fun signIn(callback: Callback)
        fun signOut()
    }

    interface FacebookPresenter : BasePresenter {
        fun signIn(callback: Callback)
        fun signOut()
    }

    interface CommonLoginPresenter : BasePresenter {
        fun signIn(url: String, params: JSONObject, callback: CommonCallback)
    }
}