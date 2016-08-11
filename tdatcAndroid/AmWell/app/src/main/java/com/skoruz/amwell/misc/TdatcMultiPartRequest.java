package com.skoruz.amwell.misc;

import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.ParseError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.MultiPartRequest;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.skoruz.amwell.utils.AppController;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Created by keerthi on 6/1/16.
 */
public class TdatcMultiPartRequest<T> extends MultiPartRequest<T> {
    public static final int TIMEOUT_MS = 30000;
    private final ArrayMap<String, String> authArray;
    private final Class<T> clazz;
    private final Gson gson = new Gson();
    private Map<String, String> headers;
    private final Listener<T> mListener;
    private final Map<String, String> params;
    private final String token;

    public TdatcMultiPartRequest(String url, Class<T> clazz, String token, Listener<T> listener, ErrorListener errorListener) {
        super(1, url, listener, errorListener);
        this.clazz = clazz;
        this.params = null;
        this.mListener = listener;
        this.token = token;
        this.authArray = null;
        setShouldCache(false);
        init();
    }

    public TdatcMultiPartRequest(int type, String url, Class<T> clazz, String token, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
        super(type, url, listener, errorListener);
        this.clazz = clazz;
        this.params = params;
        this.mListener = listener;
        this.token = token;
        this.authArray = null;
        init();
    }

    public TdatcMultiPartRequest(int type, String url, Class<T> clazz, ArrayMap<String, String> authArray, Map<String, String> params, Listener<T> listener, ErrorListener errorListener) {
        super(type, url, listener, errorListener);
        this.clazz = clazz;
        this.params = params;
        this.mListener = listener;
        this.token = null;
        this.authArray = authArray;
        init();
    }

    private void init() {
        if (this.headers == null) {
            this.headers = new ArrayMap();
        }
        if (!TextUtils.isEmpty(this.token)) {
            this.headers.put(AppController.X_PROFILE_TOKEN_HEADER, this.token);
        } else if (this.authArray != null) {
            for (Entry<String, String> authToken : this.authArray.entrySet()) {
                this.headers.put(authToken.getKey(), authToken.getValue());
            }
        }
        this.headers.put(AppController.X_DROID_VERSION_HEADER, AppController.APP_VERSION);
        this.headers.put(AppController.ACCEPT, AppController.ACCEPT_TYPE_JSON);
    }

    public Map<String, String> getHeaders() throws AuthFailureError {
        return this.headers != null ? this.headers : super.getHeaders();
    }

    protected Map<String, String> getParams() throws AuthFailureError {
        return this.params != null ? this.params : super.getParams();
    }

    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            Gson gson = this.gson;
            //Class cls = this.clazz;
            return Response.success(gson.fromJson(json, this.clazz), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JsonSyntaxException e2) {
            return Response.error(new ParseError(e2));
        }
    }

    protected void deliverResponse(T response) {
        if (this.mListener != null) {
            this.mListener.onResponse(response);
        }
    }

    public void deliverError(VolleyError error) {
        super.deliverError(error);
        try {
            //Utils.sendFailureLog(error, getUrl(), getMethod(), getParams());
            VolleyLog.d("Multi",error.getMessage());
        } catch (Exception e) {
           // Utils.sendFailureLog(error, getUrl(), getMethod(), null);
            VolleyLog.d("Multi",error.getMessage());
        }
    }
}
