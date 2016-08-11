package com.skoruz.amwell.misc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.text.TextUtils;
import java.util.ArrayList;

/**
 * Created by SKoruz-Keerthi on 27-11-2015.
 */
public class SlotDayHolder implements Parcelable {
    public static final Creator<SlotDayHolder> CREATOR = new Creator<SlotDayHolder>() {
        public SlotDayHolder createFromParcel(Parcel in) {
            return new SlotDayHolder(in);
        }

        public SlotDayHolder[] newArray(int size) {
            return new SlotDayHolder[size];
        }
    };
    public String date;
    public String day;
    public boolean isOpenSlotAvailable = false;
    private ArrayList<SlotPeriodHolder> mPeriodList = new ArrayList();
    public String relDay;

    public void addPeroid(SlotPeriodHolder period) {
        this.mPeriodList.add(period);
    }

    public SlotPeriodHolder getPeroid(int position) {
        return (SlotPeriodHolder) this.mPeriodList.get(position);
    }

    public int getSize() {
        return this.mPeriodList.size();
    }

    public int getOpenSlotPosition() {
        for (int i = 0; i < this.mPeriodList.size(); i++) {
            if (((SlotPeriodHolder) this.mPeriodList.get(i)).isOpenSlotAvailable) {
                return i;
            }
        }
        return -1;
    }

    public SlotPeriodHolder findPeriod(int hour) {
        for (int i = 0; i < this.mPeriodList.size(); i++) {
            SlotPeriodHolder holder = (SlotPeriodHolder) this.mPeriodList.get(i);
            if (hour < holder.getEndHour() && hour >= holder.getStartHour()) {
                return holder;
            }
        }
        return null;
    }

    public void checkAvailableSlots() {
        for (int i = 0; i < this.mPeriodList.size(); i++) {
            if (((SlotPeriodHolder) this.mPeriodList.get(i)).isOpenSlotAvailable) {
                this.isOpenSlotAvailable = true;
                return;
            }
        }
    }

    public String getDayAndDate() {
        return this.day + ", " + this.date;
    }

    public String getDate() {
        return this.date;
    }

    public boolean isToday() {
        return !TextUtils.isEmpty(this.relDay) && this.relDay.equalsIgnoreCase("today");
    }

    public boolean isTomorrow() {
        return !TextUtils.isEmpty(this.relDay) && this.relDay.equalsIgnoreCase("tom");
    }

    protected SlotDayHolder(Parcel in) {
        boolean z = false;
        if (in.readByte() != (byte) 0) {
            z = true;
        }
        this.isOpenSlotAvailable = z;
        this.relDay = in.readString();
        this.day = in.readString();
        this.date = in.readString();
        this.mPeriodList = new ArrayList();
        if (in.readByte() != (byte) 0) {
            in.readList(this.mPeriodList, SlotPeriodHolder.class.getClassLoader());
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (this.isOpenSlotAvailable ? 1 : 0));
        dest.writeString(this.relDay);
        dest.writeString(this.day);
        dest.writeString(this.date);
        if (this.mPeriodList == null) {
            dest.writeByte((byte) 0);
            return;
        }
        dest.writeByte((byte) 1);
        dest.writeList(this.mPeriodList);
    }

    public int describeContents() {
        return 0;
    }
}

