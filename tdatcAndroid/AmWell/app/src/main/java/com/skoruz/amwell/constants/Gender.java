package com.skoruz.amwell.constants;

/**
 * Created by Skoruz-Ashish on 8/25/2015.
 */
public enum Gender {
    Male("M"),FeMale("F"),Others("TG");

    private String value;

    private Gender(String value){
        this.value=value;
    }

    public String getValue(){
        return this.value;
    }

    //M,F,TG;
}
