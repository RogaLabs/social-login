package com.rogalabs.lib

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rogalabs.lib.LoginContract.GooglePresenter

/**
 * Created by roga on 13/07/16.
 */
class LoginGooglePresenter(val view: LoginContract.View?) : GooglePresenter , GoogleApiClient.OnConnectionFailedListener{

    private var mGoogleApiClient: GoogleApiClient? = null
    private var activity: FragmentActivity? = null
    private var callback: Callback? = null


    companion object {
        private val RC_SIGN_IN = 200
    }

    override fun start(activity: FragmentActivity?) {
        this.activity = activity
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build()
        mGoogleApiClient = GoogleApiClient.Builder(activity?.applicationContext!!).enableAutoManage(activity!! /* FragmentActivity */, this /* OnConnectionFailedListener */).addApi(Auth.GOOGLE_SIGN_IN_API, gso).build()
    }

    override fun activityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun  handleSignInResult(result: GoogleSignInResult?) {
        if (result?.isSuccess!!) {
            val acct = result?.signInAccount
            val user = SocialUser(acct?.id, acct?.displayName, acct?.email, acct?.photoUrl)
            callback?.onSuccess(user)
        }
    }

    override fun signIn(callback: Callback) {
        this.callback = callback
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        activity?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient)
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

}