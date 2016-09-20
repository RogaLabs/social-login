package com.rogalabs.lib.server;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Map;

public abstract class ApiFactory {

    private RequestQueue queue;

    public ApiFactory(Context context) {
        this.queue = Volley.newRequestQueue(context);
    }

    @NonNull
    private DefaultRetryPolicy getDefaultRetryPolicy() {
        return new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    /**
     * VOLLEY POST API
     *
     * @param endpoint         url for request
     * @param stringParam      params values
     * @param stringHeader     headers values
     * @param responseListener success actions listener
     * @param errorListener    error actions listener
     */
    public void createPOST(String endpoint, final Map<String, String> stringParam, final Map<String, String> stringHeader,
                           Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        queue.add(new StringRequest(Request.Method.POST, endpoint, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return stringHeader != null ? stringHeader : super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return stringParam != null ? stringParam : super.getParams();
            }
        }).setRetryPolicy(getDefaultRetryPolicy());
    }

    /**
     * VOLLEY GET API
     *
     * @param endpoint         url for request
     * @param stringParam      params values
     * @param stringHeader     headers values
     * @param responseListener success actions listener
     * @param errorListener    error actions listener
     */
    public void createGET(String endpoint, final Map<String, String> stringParam, final Map<String, String> stringHeader,
                          Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        queue.add(new StringRequest(Request.Method.GET, endpoint, responseListener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return stringHeader != null ? stringHeader : super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return stringParam != null ? stringParam : super.getParams();
            }
        }).setRetryPolicy(getDefaultRetryPolicy());
    }
}
