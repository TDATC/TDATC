package com.skoruz.amwell.misc;

import android.content.ContentResolver;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.widget.ImageView.ScaleType;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.error.VolleyError;
import com.android.volley.request.ImageRequest;

/**
 * Created by keerthi on 19/1/16.
 */
public class TdatcImageRequest extends ImageRequest {
    public TdatcImageRequest(String url, Resources resources, ContentResolver contentResolver, Listener<Bitmap> listener, int maxWidth, int maxHeight, ScaleType scaleType, Config decodeConfig, ErrorListener errorListener) {
        super(url, resources, contentResolver, listener, maxWidth, maxHeight, scaleType, decodeConfig, errorListener);
    }

    public void deliverError(VolleyError error) {
        super.deliverError(error);
        try {
            //Utils.sendFailureLog(error, getUrl(), getMethod(), getParams());
        } catch (Exception e) {
            //Utils.sendFailureLog(error, getUrl(), getMethod(), null);
        }
    }
}
