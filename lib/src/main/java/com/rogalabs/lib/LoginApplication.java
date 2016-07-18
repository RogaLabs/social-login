package com.rogalabs.lib;

import android.app.Application;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;

/**
 * Created by roga on 18/07/16.
 */
public final class LoginApplication{

    public static void startSocialLogin(Application application) {
        FacebookSdk.sdkInitialize(application.getApplicationContext());
        AppEventsLogger.activateApp(application);
    }
}
