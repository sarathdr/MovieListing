package com.sarathdr.movies.ui;

import android.app.Application;
import android.graphics.Bitmap;
import android.util.LruCache;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.ImageLoader;
import com.google.gson.Gson;

/**
 * @author sdevadas on 11/06/16.
 */
public class MoviesApplication extends Application {

    private static final int MAX_NUMBER_OF_CACHED_IMAGES = 100;

    private static MoviesApplication sInstance;
    private RequestQueue mVolleyRequestQueue;
    private ImageLoader mImageLoader;
    private Gson mGson;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static MoviesApplication getInstance() {
        return sInstance;
    }

    /**
     * Creates and returns the Volley request queue.
     * This method is not always called on the main thread.
     * However, all calls on secondary threads are separated by a synchronization point from the initial calls on the
     * main thread. Thus no explicit synchronization is needed here.
     *
     * @return volley request queue
     */
    private RequestQueue getVolleyRequestQueue() {
        if (mVolleyRequestQueue == null) {
            // Volley request queue.
            mVolleyRequestQueue = new RequestQueue(new DiskBasedCache(getCacheDir()), new BasicNetwork(new HurlStack()));
            mVolleyRequestQueue.start();

        }
        return mVolleyRequestQueue;
    }


    /**
     * Adds a request to the volley queue, which will fire it automatically in the background
     *
     * @param request the request to add
     */
    public void addRequestToQueue(Request request) {
        addRequestToQueue(request, 10000);
    }

    /**
     * Adds a request to the volley queue, which will fire it automatically in the background.
     * Same as the standard method with the same name, but allows to set a custom timeout
     *
     * @param request the request to add
     * @param timeout the timeout in milliseconds, which will be used instead of the default timeout (usually 10000 ms)
     */
    public void addRequestToQueue(Request request, int timeout) {
        request.setRetryPolicy(new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        getVolleyRequestQueue().add(request);
    }

    public void clearVolleyCache() {
        if (mVolleyRequestQueue != null) {
            mVolleyRequestQueue.getCache().clear();
        }
    }

    public void clearAllVolleyRequests() {
        if (mVolleyRequestQueue != null) {
            mVolleyRequestQueue.cancelAll(new RequestQueue.RequestFilter() {
                @Override
                public boolean apply(Request<?> request) {
                    return true;
                }
            });
        }
    }

    public ImageLoader getImageLoader() {
        if (mImageLoader == null) {
            // Create Volley ImageLoader
            mImageLoader = new ImageLoader(getVolleyRequestQueue(),
                    new ImageLoader.ImageCache() {
                        private final LruCache<String, Bitmap> cache = new LruCache<>(MAX_NUMBER_OF_CACHED_IMAGES);

                        @Override
                        public Bitmap getBitmap(String url) {
                            return cache.get(url);
                        }

                        @Override
                        public void putBitmap(String url, Bitmap bitmap) {
                            cache.put(url, bitmap);
                        }
                    });
        }

        return mImageLoader;
    }

    public Gson getGson() {
        if (mGson == null) {
            mGson = new Gson();
        }

        return mGson;
    }
}
