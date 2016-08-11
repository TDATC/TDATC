package com.skoruz.amwell.utils;

import android.text.TextUtils;

import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.adapter.ClinicTimingsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SKoruz-Keerthi on 30-10-2015.
 */
public class TimeUtils {

    public static String csvTimings(JSONObject object) throws JSONException {
        return (((BuildConfig.FLAVOR + object.getString(ClinicTimingsAdapter.SESSION1_START_TIME) + ",") + object.getString(ClinicTimingsAdapter.SESSION1_END_TIME) + ",") + object.getString(ClinicTimingsAdapter.SESSION2_START_TIME) + ",") + object.getString(ClinicTimingsAdapter.SESSION2_END_TIME);
    }

    public static String csvTOAmPmString(String time) {
        String AM = "AM";
        String PM = "PM";
        String[] suffixes = new String[]{"-", ",", "-", BuildConfig.FLAVOR};
        String amPmString = BuildConfig.FLAVOR;
        String[] allTimes = time.split(",");
        int i = 0;
        while (i < allTimes.length && !TextUtils.isEmpty(allTimes[i])) {
            String hour1 = allTimes[i].substring(0, 2);
            String temp = BuildConfig.FLAVOR;
            if (Integer.parseInt(hour1) > 12) {
                temp = ((temp + (Integer.parseInt(hour1) - 12)) + allTimes[i].substring(2)) + PM;
            } else {
                temp = ((temp + hour1) + allTimes[i].substring(2)) + AM;
            }
            amPmString = amPmString + temp + suffixes[i];
            i++;
        }
        return amPmString;
    }
}
