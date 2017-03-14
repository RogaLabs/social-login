package com.rogalabs.lib.server;

import android.content.Context;
import android.support.annotation.NonNull;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

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
     * @param jsonParams       params values
     * @param responseListener success actions listener
     * @param errorListener    error actions listener
     */
    public void createPOST(String endpoint, final JSONObject jsonParams,
                           Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        queue.add(new JsonObjectRequest(Request.Method.POST, endpoint, jsonParams, responseListener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;
                    if (jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        }).setRetryPolicy(getDefaultRetryPolicy());
    }

    /**
     * VOLLEY GET API
     *
     * @param endpoint         url for request
     * @param jsonParams       params values
     * @param responseListener success actions listener
     * @param errorListener    error actions listener
     */
    public void createGET(String endpoint, final JSONObject jsonParams,
                          Response.Listener<JSONObject> responseListener, Response.ErrorListener errorListener) {
        queue.add(new JsonObjectRequest(Request.Method.GET, endpoint, jsonParams, responseListener, errorListener) {
            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                try {
                    String jsonString = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));

                    JSONObject result = null;
                    if (jsonString.length() > 0)
                        result = new JSONObject(jsonString);

                    return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
                } catch (UnsupportedEncodingException e) {
                    return Response.error(new ParseError(e));
                } catch (JSONException je) {
                    return Response.error(new ParseError(je));
                }
            }
        }).setRetryPolicy(getDefaultRetryPolicy());
    }
}
