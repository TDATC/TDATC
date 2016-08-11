package com.skoruz.amwell.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Skoruz-Ashish on 8/26/2015.
 */
public class CheckConnectivity {

    private static final String LOG_TAG = "CheckNetworkStatus";

    Context context;
    public CheckConnectivity(Context c){
        this.context = c;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity == null) {
            System.out.println("no connection");
            return false;
        } else {
            /*NetworkInfo[] info = connectivity.getAllNetworkInfo();*/
            NetworkInfo info=connectivity.getActiveNetworkInfo();
            /*if (info != null ) {*/
            if(info!=null && info.isConnected()){
                /*for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }*/
                return true;
            }
        }
        return false;
    }
}
