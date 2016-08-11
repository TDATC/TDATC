package com.skoruz.amwell.patientEntity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Skoruz-Ashish on 8/25/2015.
 */
public class User implements Parcelable{

    public static final Parcelable.Creator<User> CREATOR=new Parcelable.Creator<User>(){

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int n) {
            return new User[n];
        }
    };

        private int user_id;
        private String firstName;
        private String lastName;
        private String emailAddress;
        private String password;
        private String phonePersonel;
        private String phoneBusiness;
        private Integer fax;
        private String photoPath;
        private String address1;
        private String address2;
        private String address3;
        private String country;
        private String state;
        private String city;
        private String lastLogin;
        private String dateOfBirth;
        private String resgistrationDate;
        private String user_type;
        private String gender;
        private String accountState;

    public User(int user_id, String firstName, String lastName, String emailAddress, String password, String phonePersonel,
                String phoneBusiness, Integer fax, String photoPath, String address1, String address2, String address3, String country,
                String state, String city, String lastLogin, String dateOfBirth, String resgistrationDate, String user_type, String gender, String accountState) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
        this.password = password;
        this.phonePersonel = phonePersonel;
        this.phoneBusiness = phoneBusiness;
        this.fax = fax;
        this.photoPath = photoPath;
        this.address1 = address1;
        this.address2 = address2;
        this.address3 = address3;
        this.country = country;
        this.state = state;
        this.city = city;
        this.lastLogin = lastLogin;
        this.dateOfBirth = dateOfBirth;
        this.resgistrationDate = resgistrationDate;
        this.user_type = user_type;
        this.gender = gender;
        this.accountState = accountState;
    }

    public User() {
        this.user_id=0;
        this.firstName="";
        this.lastName="";
        this.emailAddress="";
        this.password="";
        this.phonePersonel="";
        this.phoneBusiness="";
        this.fax=0;
        this.photoPath="";
        this.address1="";
        this.address2="";
        this.address3="";
        this.country="";
        this.state="";
        this.city="";
        this.lastLogin="";
        this.dateOfBirth="";
        this.resgistrationDate="";
        this.user_type="";
        this.gender="";
        this.accountState="";
    }

    protected User(Parcel parcel){
        this.user_id=0;
        this.firstName="";
        this.lastName="";
        this.emailAddress="";
        this.password="";
        this.phonePersonel="";
        this.phoneBusiness="";
        this.fax=0;
        this.photoPath="";
        this.address1="";
        this.address2="";
        this.address3="";
        this.country="";
        this.state="";
        this.city="";
        this.lastLogin="";
        this.dateOfBirth="";
        this.resgistrationDate="";
        this.user_type="";
        this.gender="";
        this.accountState="";
        this.user_id=parcel.readInt();
        this.firstName=parcel.readString();
        this.lastName=parcel.readString();
        this.emailAddress=parcel.readString();
        this.password=parcel.readString();
        this.phonePersonel=parcel.readString();
        this.phoneBusiness=parcel.readString();
        this.fax=parcel.readInt();
        this.photoPath=parcel.readString();
        this.address1=parcel.readString();
        this.address2=parcel.readString();
        this.address3=parcel.readString();
        this.country=parcel.readString();
        this.state=parcel.readString();
        this.city=parcel.readString();
        this.lastLogin=parcel.readString();
        this.dateOfBirth=parcel.readString();
        this.resgistrationDate=parcel.readString();
        this.user_type=parcel.readString();
        this.gender=parcel.readString();
        this.accountState= parcel.readString();
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhonePersonel() {
        return phonePersonel;
    }

    public void setPhonePersonel(String phonePersonel) {
        this.phonePersonel = phonePersonel;
    }

    public String getPhoneBusiness() {
        return phoneBusiness;
    }

    public void setPhoneBusiness(String phoneBusiness) {
        this.phoneBusiness = phoneBusiness;
    }

    public Integer getFax() {
        return fax;
    }

    public void setFax(Integer fax) {
        this.fax = fax;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress3() {
        return address3;
    }

    public void setAddress3(String address3) {
        this.address3 = address3;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(String lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getResgistrationDate() {
        return resgistrationDate;
    }

    public void setResgistrationDate(String resgistrationDate) {
        this.resgistrationDate = resgistrationDate;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAccountState() {
        return accountState;
    }

    public void setAccountState(String accountState) {
        this.accountState = accountState;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeInt(this.user_id);
        parcel.writeString(this.firstName);
        parcel.writeString(this.lastName);
        parcel.writeString(this.emailAddress);
        parcel.writeString(this.password);
        parcel.writeString(this.phonePersonel);
        parcel.writeString(this.phoneBusiness);
        parcel.writeInt(this.fax);
        parcel.writeString(this.photoPath);
        parcel.writeString(this.address1);
        parcel.writeString(this.address2);
        parcel.writeString(this.address3);
        parcel.writeString(this.country);
        parcel.writeString(this.state);
        parcel.writeString(this.city);
        parcel.writeString(this.lastLogin);
        parcel.writeString(this.dateOfBirth);
        parcel.writeString(this.resgistrationDate);
        parcel.writeString(this.user_type);
        parcel.writeString(this.gender);
        parcel.writeString(this.accountState);
    }
}
