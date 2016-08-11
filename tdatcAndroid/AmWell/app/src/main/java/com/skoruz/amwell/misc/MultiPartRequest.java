package com.skoruz.amwell.misc;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by keerthi on 5/1/16.
 */
    public abstract class MultiPartRequest<T> extends Request<T>{

        private static final String PROTOCOL_CHARSET = "utf-8";
        private Listener<T> mListener;
        private Map<String, MultiPartParam> mMultipartParams = null;
        private Map<String, String> mFileUploads = null;
        public static final int TIMEOUT_MS = 30000;
        private boolean isFixedStreamingMode;

        public MultiPartRequest(int method, String url, Listener<T> listener, ErrorListener errorListener) {

            super(method, url,errorListener);
            mListener = listener;
            mMultipartParams = new HashMap<String, MultiPartRequest.MultiPartParam>();
            mFileUploads = new HashMap<String, String>();

        }

        public MultiPartRequest<T> addMultipartParam(String name, String contentType, String value) {
            mMultipartParams.put(name, new MultiPartParam(contentType, value));
            return this;
        }

        public MultiPartRequest<T> addFile(String name, String filePath) {

            mFileUploads.put(name, filePath);
            return this;
        }

        @Override
        abstract protected Response<T> parseNetworkResponse(NetworkResponse response);

        @Override
        protected void deliverResponse(T response) {
            if(null != mListener){
                mListener.onResponse(response);
            }
        }

        public static final class MultiPartParam {

            public String contentType;
            public String value;

            public MultiPartParam(String contentType, String value) {
                this.contentType = contentType;
                this.value = value;
            }
        }

        public Map<String, MultiPartParam> getMultipartParams() {
            return mMultipartParams;
        }

        public Map<String, String> getFilesToUpload() {
            return mFileUploads;
        }

        public String getProtocolCharset() {
            return PROTOCOL_CHARSET;
        }

        public boolean isFixedStreamingMode() {
            return isFixedStreamingMode;
        }

        public void setFixedStreamingMode(boolean isFixedStreamingMode) {
            this.isFixedStreamingMode = isFixedStreamingMode;
        }
}
