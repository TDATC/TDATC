package com.skoruz.amwell.physicianEntity;

import android.text.TextUtils;

import com.skoruz.amwell.adapter.ClinicTimingsAdapter;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by SKoruz-Keerthi on 28-10-2015.
 */
public class TimingsViewModel {
    private static final int MAXIMUM_TIME = 2400;
    //private static final int MINIMUM_TIME;
    private String mDay;
    private String mSlot1From;
    private String mSlot1To;
    private String mSlot2From;
    private String mSlot2To;

    public TimingsViewModel(TimingsViewModel paramTimingsViewModel)
    {
        setDay(paramTimingsViewModel.getDay());
        setSlot1From(paramTimingsViewModel.getSlot1From());
        setSlot1To(paramTimingsViewModel.getSlot1To());
        setSlot2From(paramTimingsViewModel.getSlot2From());
        setSlot2To(paramTimingsViewModel.getSlot2To());
    }

    public TimingsViewModel(String paramString)
    {
        this.mDay = paramString;
        this.mSlot1From = "";
        this.mSlot1To = "";
        this.mSlot2From = "";
        this.mSlot2To = "";
    }

    public TimingsViewModel(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5)
    {
        this.mSlot1From = paramString1;
        this.mSlot1To = paramString2;
        this.mSlot2From = paramString3;
        this.mSlot2To = paramString4;
        this.mDay = paramString5;
    }

    public String getDay()
    {
        return this.mDay;
    }

    public String getSlot1From()
    {
        return this.mSlot1From;
    }

    public String getSlot1To()
    {
        return this.mSlot1To;
    }

    public String getSlot2From()
    {
        return this.mSlot2From;
    }

    public String getSlot2To()
    {
        return this.mSlot2To;
    }

    public void setDay(String paramString)
    {
        this.mDay = paramString;
    }

    public void setSlot1From(String paramString)
    {
        this.mSlot1From = paramString;
    }

    public void setSlot1To(String paramString)
    {
        this.mSlot1To = paramString;
    }

    public void setSlot2From(String paramString)
    {
        this.mSlot2From = paramString;
    }

    public void setSlot2To(String paramString)
    {
        this.mSlot2To = paramString;
    }

    public JSONObject toJSON()
            throws JSONException
    {
        JSONObject localJSONObject = new JSONObject();
        int i = 2400;
        boolean bool = TextUtils.isEmpty(this.mSlot1From);
        int j = 0;
        if (!bool) {
            j = Integer.parseInt(TextUtils.join("", this.mSlot1From.split(":")));
        }
        if (!TextUtils.isEmpty(this.mSlot2From)) {
            i = Integer.parseInt(TextUtils.join("", this.mSlot2From.split(":")));
        }
        if (j > i)
        {
            localJSONObject.put(ClinicTimingsAdapter.SESSION1_START_TIME, this.mSlot2From);
            localJSONObject.put(ClinicTimingsAdapter.SESSION1_END_TIME,this.mSlot2To);
            localJSONObject.put(ClinicTimingsAdapter.SESSION2_START_TIME, this.mSlot1From);
            localJSONObject.put(ClinicTimingsAdapter.SESSION2_END_TIME, this.mSlot1To);
            return localJSONObject;
        }
        localJSONObject.put(ClinicTimingsAdapter.SESSION1_START_TIME, this.mSlot1From);
        localJSONObject.put(ClinicTimingsAdapter.SESSION1_END_TIME, this.mSlot1To);
        localJSONObject.put(ClinicTimingsAdapter.SESSION2_START_TIME, this.mSlot2From);
        localJSONObject.put(ClinicTimingsAdapter.SESSION2_END_TIME, this.mSlot2To);
        return localJSONObject;
    }
}
