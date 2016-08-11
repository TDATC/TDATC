package com.skoruz.amwell.utils;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RequestTickle;
import com.android.volley.VolleyLog;
import com.android.volley.cache.DiskLruBasedCache.ImageCacheParams;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.VolleyTickle;
import com.skoruz.amwell.R;
import com.skoruz.amwell.misc.SimpleImageLoader;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Skoruz-Ashish on 9/3/2015.
 */
public class AppController extends Application {
    public static final String ACCEPT = "Accept";
    public static final String ACCEPT_TYPE_JSON = "application/json";
    public static final String API_VERSION = "API-Version";
    public static String APP_VERSION = null;
    public static int APP_VERSION_CODE = 0;
    public static final String IMAGE_CACHE_DIR = "thumbs";
    public static final String IMAGE_PRACTICE_CACHE_DIR = "practice_image";
    public static final String IMAGE_PRACTICE_THUMB_CACHE_DIR = "practice_thumbs";
    public static final int IMAGE_SIZE = 50;
    public static final int IMAGE_SIZE_BIG = 100;
    public static final int SCALE_DOWN_SIZE = 1024;
    public static final int SCALE_DOWN_SIZE_SMALL = 512;
    public static final String USER_IMAGE_CACHE_DIR = "user_thumbs";
    public static final String TAG=AppController.class.getSimpleName();
    public static final String BASE_APPLICATION_ID = Utils.getBaseApplicationId();
    public static final String X_AUTH_TOKEN_HEADER = "X-AUTH-TOKEN";
    public static final String X_DROID_VERSION_HEADER = "X-DROID-VERSION";
    public static final String X_FABRIC_API_TOKEN_HEADER = "X-FABRIC-API-TOKEN";
    public static final String X_PROFILE_TOKEN_HEADER = "X-PROFILE-TOKEN";
    private static AppController mInstance;
    private Locale current;
    private SimpleImageLoader mImagePracticeFetcher;
    private SimpleImageLoader mImageUserFetcher;
    private RequestQueue mRequestQueue;
    private RequestTickle mRequestTickle;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance=this;
    }

    public Locale getLocale() {
        if (this.current == null) {
            this.current = Locale.US;
        }
        return this.current;
    }

    public static synchronized AppController getInstance() {
        AppController synapseApplication;
        synchronized (AppController.class) {
            synapseApplication = mInstance;
        }
        return synapseApplication;
    }

    public RequestTickle getRequestTickle() {
        if (this.mRequestTickle == null) {
            this.mRequestTickle = VolleyTickle.newRequestTickle(getApplicationContext());
        }
        return this.mRequestTickle;
    }

    public SimpleImageLoader getImageLoader() {
        if (this.mImageUserFetcher == null) {
            ImageCacheParams cacheParams = new ImageCacheParams(getApplicationContext(), USER_IMAGE_CACHE_DIR);
            cacheParams.setMemCacheSizePercent(0.5f);
            this.mImageUserFetcher = new SimpleImageLoader(getApplicationContext(), cacheParams);
            this.mImageUserFetcher.setDefaultDrawable(R.drawable.background_default_no_doctor);
            this.mImageUserFetcher.setMaxImageSize(Utils.hasMoreHeap() ? IMAGE_SIZE_BIG : IMAGE_SIZE);
            this.mImageUserFetcher.setFadeInImage(false);
        }
        return this.mImageUserFetcher;
    }

    public SimpleImageLoader getConsultImageLoader() {
        if (this.mImageUserFetcher == null) {
            ImageCacheParams cacheParams = new ImageCacheParams(getApplicationContext(), IMAGE_CACHE_DIR);
            cacheParams.setMemCacheSizePercent(0.5f);
            this.mImageUserFetcher = new SimpleImageLoader(getApplicationContext(), cacheParams);
            this.mImageUserFetcher.setDefaultDrawable(R.drawable.ic_photos);
            this.mImageUserFetcher.setMaxImageSize(Utils.hasMoreHeap() ? IMAGE_SIZE_BIG : IMAGE_SIZE);
            this.mImageUserFetcher.setFadeInImage(false);
        }
        return this.mImageUserFetcher;
    }

    public SimpleImageLoader getPracticeLoader() {
        if (this.mImagePracticeFetcher == null) {
            ImageCacheParams cacheParams = new ImageCacheParams(getApplicationContext(), IMAGE_PRACTICE_THUMB_CACHE_DIR);
            cacheParams.setMemCacheSizePercent(0.5f);
            ArrayList<Drawable> placeHolderDrawables = new ArrayList();
            placeHolderDrawables.add(ResourcesCompat.getDrawable(getResources(), R.drawable.background_default_no_practice, null));
            this.mImagePracticeFetcher = new SimpleImageLoader(getApplicationContext(), cacheParams);
            this.mImagePracticeFetcher.setDefaultDrawables(placeHolderDrawables);
            this.mImagePracticeFetcher.setMaxImageSize(SCALE_DOWN_SIZE_SMALL);
            this.mImagePracticeFetcher.setFadeInImage(false);
        }
        return this.mImagePracticeFetcher;
    }


    public RequestQueue getRequestQueue() {
        if (this.mRequestQueue == null) {
            this.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return this.mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        if (TextUtils.isEmpty(tag)) {
            tag = TAG;
        }
        req.setTag(tag);
        VolleyLog.d("Adding request to queue: %s", new Object[]{req.getUrl()});
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (this.mRequestQueue != null) {
            this.mRequestQueue.cancelAll(tag);
        }
    }

    public void onLowMemory() {
        super.onLowMemory();
    }
}
