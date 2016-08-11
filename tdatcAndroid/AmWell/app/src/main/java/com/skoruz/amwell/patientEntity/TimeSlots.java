package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

import com.skoruz.amwell.physicianEntity.Clinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKoruz-Keerthi on 26-11-2015.
 */
public class TimeSlots implements Parcelable {
    public static final Parcelable.Creator<TimeSlots> CREATOR = new Parcelable.Creator<TimeSlots>(){

        public TimeSlots createFromParcel(Parcel parcel) {
            return new TimeSlots(parcel);
        }

        public TimeSlots[] newArray(int n) {
            return new TimeSlots[n];
        }
    };
    public String appointment_token;
    public Clinic relation;
    public ArrayList<TimeSlot> slots;

    public TimeSlots() {
        this.slots = new ArrayList();
        this.appointment_token = "";
        this.relation = new Clinic();
    }

    protected TimeSlots(Parcel parcel) {
        this.slots = new ArrayList();
        this.appointment_token = "";
        this.relation = new Clinic();
        this.appointment_token = parcel.readString();
        this.slots = new ArrayList();
        if (parcel.readByte() == 1) {
            parcel.readList(this.slots, TimeSlot.class.getClassLoader());
        }
        this.relation = (Clinic)parcel.readParcelable(Clinic.class.getClassLoader());
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.appointment_token);
        if (this.slots == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeList(this.slots);
        }
        parcel.writeParcelable((Parcelable)this.relation, n);
    }

    public static class TimeSlot
            implements Parcelable {
        public static final Parcelable.Creator<TimeSlot> CREATOR = new Parcelable.Creator<TimeSlot>(){

            public TimeSlot createFromParcel(Parcel parcel) {
                return new TimeSlot(parcel);
            }

            public TimeSlot[] newArray(int n) {
                return new TimeSlot[n];
            }
        };
        public String date;
        public String day;
        public int max_fee_range;
        public String relDay;
        public ArrayList<Slot> slots;

        public TimeSlot() {
            this.date = "";
            this.day = "";
            this.relDay = "";
            this.max_fee_range = 0;
            this.slots = new ArrayList();
        }

        protected TimeSlot(Parcel parcel) {
            this.date = "";
            this.day = "";
            this.relDay = "";
            this.max_fee_range = 0;
            this.slots = new ArrayList();
            this.date = parcel.readString();
            this.day = parcel.readString();
            this.relDay = parcel.readString();
            this.max_fee_range = parcel.readInt();
            this.slots = new ArrayList();
            if (parcel.readByte() == 1) {
                parcel.readList(this.slots, Slot.class.getClassLoader());
            }
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.date);
            parcel.writeString(this.day);
            parcel.writeString(this.relDay);
            parcel.writeInt(this.max_fee_range);
            if (this.slots == null) {
                parcel.writeByte((byte) 0);
                return;
            }
            parcel.writeByte((byte) 1);
            parcel.writeList(this.slots);
        }

        public static class Slot
                implements Parcelable {
            public static final Parcelable.Creator<Slot> CREATOR = new Parcelable.Creator<Slot>(){

                public Slot createFromParcel(Parcel parcel) {
                    return new Slot(parcel);
                }

                public Slot[] newArray(int n) {
                    return new Slot[n];
                }
            };
            public String time;
            public ArrayList<TimeSlotsPerSlot> timeslots;

            public Slot() {
                this.time = "";
                this.timeslots = new ArrayList();
            }

            protected Slot(Parcel parcel) {
                this.time = "";
                this.timeslots = new ArrayList();
                this.time = parcel.readString();
                this.timeslots = new ArrayList();
                if (parcel.readByte() != 0) {
                    parcel.readList(this.timeslots, TimeSlotsPerSlot.class.getClassLoader());
                }
            }

            public int describeContents() {
                return 0;
            }

            public void writeToParcel(Parcel parcel, int n) {
                parcel.writeString(this.time);
                if (this.timeslots == null) {
                    parcel.writeByte((byte) 0);
                    return;
                }
                parcel.writeByte((byte) 1);
                parcel.writeList(this.timeslots);
            }

            public static class TimeSlotsPerSlot
                    implements Parcelable {
                public static final Parcelable.Creator<TimeSlotsPerSlot> CREATOR = new Parcelable.Creator<TimeSlotsPerSlot>(){

                    public TimeSlotsPerSlot createFromParcel(Parcel parcel) {
                        return new TimeSlotsPerSlot(parcel);
                    }

                    public TimeSlotsPerSlot[] newArray(int n) {
                        return new TimeSlotsPerSlot[n];
                    }
                };
                public boolean available;
                public int index;
                public String ts;

                public TimeSlotsPerSlot() {
                    this.available = false;
                    this.ts = "";
                    this.index = 0;
                }

                protected TimeSlotsPerSlot(Parcel parcel) {
                    this.available = false;
                    this.ts = "";
                    this.index = 0;
                    byte by = parcel.readByte();
                    boolean bl = false;
                    if (by != 0) {
                        bl = true;
                    }
                    this.available = bl;
                    this.ts = parcel.readString();
                    this.index = parcel.readInt();
                }

                public int describeContents() {
                    return 0;
                }

                public void writeToParcel(Parcel parcel, int n) {
                    byte by = (byte) (this.available ? 1 : 0);
                    parcel.writeByte(by);
                    parcel.writeString(this.ts);
                    parcel.writeInt(this.index);
                }

            }

        }

    }

}
