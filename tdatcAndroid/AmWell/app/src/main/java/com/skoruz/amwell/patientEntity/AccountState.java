package com.skoruz.amwell.patientEntity;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by SKoruz-Keerthi on 11-12-2015.
 */
public class AccountState implements Parcelable {

    public static final Parcelable.Creator<AccountState> CREATOR=new Creator<AccountState>() {
        @Override
        public AccountState createFromParcel(Parcel source) {
            return new AccountState(source);
        }

        @Override
        public AccountState[] newArray(int size) {
            return new AccountState[0];
        }
    };

    private  int  account_id;
    private  String status;

    public AccountState() {
    }

    public AccountState(int account_id, String status) {
        this.account_id = account_id;
        this.status = status;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    protected AccountState(Parcel parcel){
        this.account_id=0;
        this.status="";
        this.account_id=parcel.readInt();
        this.status=parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.account_id);
        dest.writeString(this.status);
    }
}
