package com.rogalabs.lib

import android.content.Intent
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import com.rogalabs.lib.common.CommonLoginPresenter
import com.rogalabs.lib.facebook.LoginFacebookPresenter
import com.rogalabs.lib.google.LoginGooglePresenter
import java.util.*

/**
 * Created by roga on 05/07/16.
 */
open class LoginView : AppCompatActivity(), LoginContract.View {

    private var mainLayout: FrameLayout? = null
    private var googlePresenter: LoginContract.GooglePresenter? = null
    private var facebookPresenter: LoginContract.FacebookPresenter? = null
    private var commonLoginPresenter: LoginContract.CommonLoginPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        injectPresenter(LoginGooglePresenter(this),
                LoginFacebookPresenter(this),
                CommonLoginPresenter(this))

        googlePresenter?.create(this)
        facebookPresenter?.create(this)
        commonLoginPresenter?.create(this)
    }

    override fun onPause() {
        super.onPause()
        googlePresenter?.pause()
    }

    override fun onDestroy() {
        super.onDestroy()
        googlePresenter?.destroy()
    }

    override fun setContentView(@LayoutRes layoutResID: Int) {
        mainLayout = layoutInflater.inflate(R.layout.login_view, null) as FrameLayout
        val activityContent = mainLayout!!.findViewById(R.id.activity_content) as FrameLayout
        layoutInflater.inflate(layoutResID, activityContent, true)
        super.setContentView(mainLayout)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        googlePresenter?.activityResult(requestCode, resultCode, data)
        facebookPresenter?.activityResult(requestCode, resultCode, data)
    }

    override fun injectPresenter(googlePresenter: LoginGooglePresenter,
                                 facebookPresenter: LoginFacebookPresenter,
                                 commonLoginPresenter: LoginContract.CommonLoginPresenter) {
        this.googlePresenter = googlePresenter
        this.facebookPresenter = facebookPresenter
        this.commonLoginPresenter = commonLoginPresenter
    }

    override fun hideProgress() {
    }

    override fun showProgress() {
    }

    protected fun loginWithGoogle(callback: Callback) {
        googlePresenter?.signIn(callback)
    }

    protected fun logoffWithGoogle() {
        googlePresenter?.signOut()
    }

    protected fun loginWithFacebook(callback: Callback) {
        facebookPresenter?.signIn(callback)
    }

    protected fun logoffWithFacebook() {
        facebookPresenter?.signOut()
    }

    protected fun loginWithCommonCredentials(url: String, params: HashMap<String, String>, callback: CommonCallback) {
        commonLoginPresenter?.signIn(url, params, callback)
    }
}
