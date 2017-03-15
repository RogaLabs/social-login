package com.rogalabs.lib.google

import android.content.Intent
import android.support.v4.app.FragmentActivity
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rogalabs.lib.Callback
import com.rogalabs.lib.LoginContract
import com.rogalabs.lib.LoginContract.GooglePresenter
import com.rogalabs.lib.model.Hometown
import com.rogalabs.lib.model.SocialUser

/**
 * Created by roga on 13/07/16.
 */
class LoginGooglePresenter(val view: LoginContract.View?) : GooglePresenter, GoogleApiClient.OnConnectionFailedListener {

    private var mGoogleApiClient: GoogleApiClient? = null
    private var activity: FragmentActivity? = null
    private var callback: Callback? = null


    companion object {
        private val RC_SIGN_IN = 200
    }

    override fun create(activity: FragmentActivity?) {
        this.activity = activity
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity?.applicationContext!!)
                .enableAutoManage(activity!! /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build()
    }

    override fun pause() {
    }

    override fun destroy() {
        mGoogleApiClient?.stopAutoManage(this.activity!!)
        mGoogleApiClient?.disconnect()
    }

    override fun activityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == RC_SIGN_IN) {
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            handleSignInResult(result)
        }
    }

    private fun handleSignInResult(result: GoogleSignInResult?) {
        if (result?.isSuccess!!) {
            val acct = result?.signInAccount

            val hometown = Hometown()
            val user = SocialUser(acct?.id, acct?.displayName, acct?.email,
                    "", acct?.photoUrl.toString(), hometown, acct?.idToken)
            callback?.onSuccess(user)
        } else {
            callback?.onError(LoginGoogleException("Google login not succeed!"))
        }
    }

    override fun signIn(callback: Callback) {
        this.callback = callback
        val signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient)
        activity?.startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun signOut() {
        if (mGoogleApiClient?.isConnected!!) {
            Auth.GoogleSignInApi.revokeAccess(mGoogleApiClient)
            Auth.GoogleSignInApi.signOut(mGoogleApiClient)
        }
    }

    override fun onConnectionFailed(connectionResult: ConnectionResult) {
    }

}