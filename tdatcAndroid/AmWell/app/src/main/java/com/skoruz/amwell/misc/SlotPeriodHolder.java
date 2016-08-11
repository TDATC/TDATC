package com.skoruz.amwell.misc;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.skoruz.amwell.patientEntity.TimeSlots.TimeSlot.Slot.TimeSlotsPerSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

/**
 * Created by SKoruz-Keerthi on 27-11-2015.
 */
public class SlotPeriodHolder implements Parcelable {
    public static final Creator<SlotPeriodHolder> CREATOR = new Creator<SlotPeriodHolder>() {
        public SlotPeriodHolder createFromParcel(Parcel in) {
            return new SlotPeriodHolder(in);
        }

        public SlotPeriodHolder[] newArray(int size) {
            return new SlotPeriodHolder[size];
        }
    };
    public boolean isExpanded;
    public boolean isOpenSlotAvailable = false;
    public String layoutId;
    public int mContentHeightPixel;
    public int mEndHour;
    public int mEndMin = 0;
    public int mExtraMargin;
    public int mSlotImage;
    public int mStartHour;
    public int mStartMin = 0;
    public HashMap<Integer, Integer> positionCache = new HashMap();
    public String primaryHeader;
    public String secondaryHeader;
    public ArrayList<ArrayList<TimeSlotsPerSlot>> timeslot = new ArrayList();

    public void checkAvailableSlots() {
        if (this.timeslot != null && this.timeslot.size() > 0) {
            for (int i = 0; i < this.timeslot.size(); i++) {
                ArrayList<TimeSlotsPerSlot> slots = (ArrayList) this.timeslot.get(i);
                if (slots != null && slots.size() > 0) {
                    for (int j = 0; j < slots.size(); j++) {
                        if (((TimeSlotsPerSlot) slots.get(j)).available) {
                            this.isOpenSlotAvailable = true;
                            return;
                        }
                    }
                    continue;
                }
            }
        }
    }

    public int getStartHour() {
        return this.mStartHour;
    }

    public int getEndHour() {
        return this.mEndHour;
    }

    public SlotPeriodHolder(int startHour, int endHour) {
        this.mStartHour = startHour;
        this.mEndHour = endHour;
    }

    protected SlotPeriodHolder(Parcel in) {
        boolean z;
        int i;
        boolean z2 = true;
        if (in.readByte() != (byte) 0) {
            z = true;
        } else {
            z = false;
        }
        this.isOpenSlotAvailable = z;
        this.mExtraMargin = in.readInt();
        this.mContentHeightPixel = in.readInt();
        this.mSlotImage = in.readInt();
        this.mStartHour = in.readInt();
        this.mEndHour = in.readInt();
        this.mStartMin = in.readInt();
        this.mEndMin = in.readInt();
        if (in.readByte() == (byte) 0) {
            z2 = false;
        }
        this.isExpanded = z2;
        this.layoutId = in.readString();
        this.primaryHeader = in.readString();
        this.secondaryHeader = in.readString();
        this.timeslot = new ArrayList();
        int size = in.readInt();
        if (size > 0) {
            for (i = 0; i < size; i++) {
                if (in.readByte() != (byte) 0) {
                    ArrayList<TimeSlotsPerSlot> slot = new ArrayList();
                    in.readList(slot, TimeSlotsPerSlot.class.getClassLoader());
                    this.timeslot.add(slot);
                }
            }
        }
        size = in.readInt();
        this.positionCache = new HashMap();
        if (size > 0) {
            for (i = 0; i < size; i++) {
                this.positionCache.put(Integer.valueOf(in.readInt()), Integer.valueOf(in.readInt()));
            }
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i;
        if (this.isOpenSlotAvailable) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeInt(this.mExtraMargin);
        dest.writeInt(this.mContentHeightPixel);
        dest.writeInt(this.mSlotImage);
        dest.writeInt(this.mStartHour);
        dest.writeInt(this.mEndHour);
        dest.writeInt(this.mStartMin);
        dest.writeInt(this.mEndMin);
        if (this.isExpanded) {
            i = 1;
        } else {
            i = 0;
        }
        dest.writeByte((byte) i);
        dest.writeString(this.layoutId);
        dest.writeString(this.primaryHeader);
        dest.writeString(this.secondaryHeader);
        if (this.timeslot == null || this.timeslot.isEmpty()) {
            dest.writeInt(0);
        } else {
            dest.writeInt(this.timeslot.size());
            for (int i2 = 0; i2 < this.timeslot.size(); i2++) {
                ArrayList<TimeSlotsPerSlot> slot = (ArrayList) this.timeslot.get(i2);
                if (slot == null) {
                    dest.writeByte((byte) 0);
                } else {
                    dest.writeByte((byte) 1);
                    dest.writeList(slot);
                }
            }
        }
        dest.writeInt(this.positionCache.size());
        for (Entry<Integer, Integer> entry : this.positionCache.entrySet()) {
            dest.writeInt(((Integer) entry.getKey()).intValue());
            dest.writeInt(((Integer) entry.getValue()).intValue());
        }
    }

    public int describeContents() {
        return 0;
    }
}

