package com.sarathdr.movies.network;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.sarathdr.movies.BuildConfig;

import java.io.UnsupportedEncodingException;

/**
 * @author sdevadas on 11/06/16.
 */
public class GsonRequest<T> extends Request<T> {
    private final Gson mGson;
    private final Class<T> mClass;

    private final Response.Listener<T> mListener;

    public GsonRequest(String url, Class<T> clazz, Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(Method.GET, url, errorListener);
        mClass = clazz;
        mGson = new GsonBuilder().create();
        mListener = listener;
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(
                    response.data,
                    HttpHeaderParser.parseCharset(response.headers));

            if (BuildConfig.DEBUG) {
                Log.d("GsonRequest", "Parsing request" + json);
            }

            return Response.success(
                    mGson.fromJson(json, mClass),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }
}
