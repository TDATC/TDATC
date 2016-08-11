package com.skoruz.amwell.misc;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView.ScaleType;
import com.android.volley.Request;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.cache.DiskLruBasedCache.ImageCacheParams;
import com.android.volley.error.VolleyError;

/**
 * Created by keerthi on 19/1/16.
 */
public class SimpleImageLoader extends com.android.volley.cache.SimpleImageLoader {
    public SimpleImageLoader(Context context, ImageCacheParams imageCacheParams) {
        super(context, imageCacheParams);
    }

    protected Request<Bitmap> makeImageRequest(String requestUrl, int maxWidth, int maxHeight, ScaleType scaleType, final String cacheKey) {
        return new TdatcImageRequest(requestUrl, getResources(), getContentResolver(), new Listener<Bitmap>() {
            public void onResponse(Bitmap response) {
                SimpleImageLoader.this.onGetImageSuccess(cacheKey, response);
            }
        }, maxWidth, maxHeight, scaleType, Bitmap.Config.RGB_565, new ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                SimpleImageLoader.this.onGetImageError(cacheKey, error);
            }
        });
    }

}
