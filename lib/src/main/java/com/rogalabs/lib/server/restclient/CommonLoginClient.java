package com.rogalabs.lib.server.restclient;

import android.content.Context;

import com.rogalabs.lib.server.ApiFactory;


public class CommonLoginClient extends ApiFactory {

    private static CommonLoginClient api;

    private CommonLoginClient(Context context) {
        super(context);
    }

    public static CommonLoginClient getInstance(Context context) {
        if (api == null)
            api = new CommonLoginClient(context);
        return api;
    }
}
