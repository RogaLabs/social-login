package com.rogalabs.lib.google

import android.content.Intent
import android.os.AsyncTask
import android.support.v4.app.FragmentActivity
import com.facebook.FacebookSdk.getApplicationContext
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.rogalabs.lib.Callback
import com.rogalabs.lib.LoginContract
import com.rogalabs.lib.LoginContract.GooglePresenter
import com.rogalabs.lib.R
import com.rogalabs.lib.model.Hometown
import com.rogalabs.lib.model.SocialUser
import java.io.IOException


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
                .requestIdToken(activity?.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        mGoogleApiClient = GoogleApiClient.Builder(activity?.applicationContext!!)
                .enableAutoManage(activity /* FragmentActivity */, this /* OnConnectionFailedListener */)
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
            val acct = result.signInAccount

            val task = object : AsyncTask<Void, Void, String>() {
                override fun doInBackground(vararg params: Void): String {
                    val SCOPES: String = "oauth2:profile email"
                    var token: String = ""
                    try {
                        token = GoogleAuthUtil.getToken(
                                getApplicationContext(),
                                acct?.account,
                                SCOPES)
                    } catch (e: IOException) {
                        e.printStackTrace()
                    } catch (e: GoogleAuthException) {
                        e.printStackTrace()
                    }

                    return token
                }

                override fun onPostExecute(token: String) {
                    val hometown = Hometown()
                    val user = SocialUser(acct?.id, acct?.displayName, acct?.email,
                            "", acct?.photoUrl.toString(), hometown, token)
                    callback?.onSuccess(user)
                }
            }
            task.execute()
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