package com.skoruz.amwell.patientEntity;

import com.skoruz.amwell.BuildConfig;

import java.util.ArrayList;

/**
 * Created by SKoruz-Keerthi on 03-11-2015.
 */
public class GeoCode {
    public ArrayList<Result> results = new ArrayList<>();
    public String status = BuildConfig.FLAVOR;

    public static class Result {
        public ArrayList<Component> address_components = new ArrayList<>();
        public String formatted_address;

        public static class Component {
            public String long_name = BuildConfig.FLAVOR;
            public ArrayList<String> types = new ArrayList<>();
        }
    }
}
