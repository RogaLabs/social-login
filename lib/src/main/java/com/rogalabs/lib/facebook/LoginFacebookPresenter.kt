package com.rogalabs.lib.facebook

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.rogalabs.lib.Callback
import com.rogalabs.lib.LoginContract
import com.rogalabs.lib.model.Hometown
import com.rogalabs.lib.model.SocialUser
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
    private val profileFields = "id, name, email, birthday, hometown"

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
        LoginManager.getInstance().logInWithReadPermissions(activity,
                Arrays.asList(
                        "public_profile",
                        "email",
                        "user_birthday",
                        "user_hometown"))
    }

    override fun signIn(callback: Callback, readPermissions: List<String>) {
        this.callback = callback
        LoginManager.getInstance().logInWithReadPermissions(activity, readPermissions)
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
        var hometown: Hometown = Hometown()
        var birthday: String = ""
        try {
            val hometownObj = jsonObject?.getJSONObject("hometown")
            birthday = jsonObject?.getString("birthday") as String
            hometown = Hometown(hometownObj?.getString("id"), hometownObj?.getString("name"))
        } finally {
            val user: SocialUser = SocialUser(jsonObject?.getString("id"),
                    jsonObject?.getString("name"), jsonObject?.getString("email"),
                    birthday, userPicture(jsonObject?.getString("id")),
                    hometown, AccessToken.getCurrentAccessToken().token)
            return user
        }
    }

    private fun userPicture(id: String?): String {
        return "https://graph.facebook.com/${id}/picture?type=large"
    }

}