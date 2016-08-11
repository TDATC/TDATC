package com.skoruz.amwell.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.skoruz.amwell.R;

/**
 * Created by SKoruz-Keerthi on 22-10-2015.
 */
public class PlayServicesUtils {

    public static boolean checkGooglePlaySevices(Context context) {
        int googlePlayServicesCheck = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        switch (googlePlayServicesCheck) {
            case 0:
                return true;
            case 2:
                try {
                    if (!(context instanceof Activity)) {
                        return false;
                    }
                    Activity activity = (Activity) context;
                    Dialog dialog = GooglePlayServicesUtil.getErrorDialog(googlePlayServicesCheck, activity, 0);
                    if (!Utils.isActivityAlive(activity) || dialog == null || dialog.isShowing()) {
                        return false;
                    }
                    dialog.show();
                    return false;
                } catch (WindowManager.BadTokenException exc) {
                    exc.printStackTrace();
                    return false;
                }
            default:
                return false;
        }
    }
}
