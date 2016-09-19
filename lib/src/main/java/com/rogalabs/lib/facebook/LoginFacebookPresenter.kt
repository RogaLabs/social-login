package com.rogalabs.lib.facebook

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.rogalabs.lib.Callback
import com.rogalabs.lib.LoginContract
import com.rogalabs.lib.SocialUser
import org.json.JSONObject
import java.util.*

/**
 * Created by roga on 14/07/16.
 */
class LoginFacebookPresenter(val view: LoginContract.View) : LoginContract.FacebookPresenter,
        FacebookCallback<LoginResult>, GraphRequest.GraphJSONObjectCallback {

    private var callback: Callback? = null
    private var callbackManager: CallbackManager? = null
    private var activity: FragmentActivity? = null
    private val profileFields = "id, name, email, gender"

    override fun activityResult(requestCode: Int, resultCode: Int, data: Intent) {
        callbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    override fun create(activity: FragmentActivity?) {
        this.activity = activity
        callbackManager = com.facebook.CallbackManager.Factory.create()
        registerFacebookCallback(callbackManager)
    }

    override fun pause() {
    }

    override fun destroy() {
    }

    private fun registerFacebookCallback(callbackManager: CallbackManager?) {
        LoginManager.getInstance().registerCallback(callbackManager, this)
    }


    override fun onCancel() {
    }

    override fun onError(error: FacebookException?) {
        callback?.onError(error?.cause!!)
    }

    override fun onSuccess(result: LoginResult?) {
        sendGraphRequest()
    }

    override fun signIn(callback: Callback) {
        this.callback = callback
        LoginManager.getInstance().logInWithReadPermissions(activity, Arrays.asList("public_profile",
                "user_friends",
                "email"))
    }

    override fun signOut() {
        LoginManager.getInstance().logOut()
    }

    override fun onCompleted(jsonResponse: JSONObject?, response: GraphResponse?) {
        callback?.onSuccess(buildSocialUser(jsonResponse))
    }

    private fun sendGraphRequest() {
        val graphRequest: GraphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), this)

        val parameters = Bundle()
        parameters.putString("fields", profileFields)
        graphRequest.setParameters(parameters)
        graphRequest.executeAsync()
    }

    private fun buildSocialUser(jsonObject: JSONObject?): SocialUser {
        val user: SocialUser = SocialUser(jsonObject?.getString("id"),
                jsonObject?.getString("name"), jsonObject?.getString("email"),
                userPicture(jsonObject?.getString("id")),
                AccessToken.getCurrentAccessToken().token)
        return user
    }

    private fun userPicture(id: String?): String {
        return "https://graph.facebook.com/${id}/picture?type=large"
    }

}