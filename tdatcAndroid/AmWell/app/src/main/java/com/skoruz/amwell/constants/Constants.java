package com.skoruz.amwell.constants;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Skoruz-Ashish on 8/26/2015.
 */
public class Constants {

    public static String[] userTypes={"Patient","Physician"};
    public static String[] userType={"PT", "A","PHS","HA","PHA", "PCP"};
    public static String[] genderType={"M","F","TG"};
    public static String[] accountType={"ACTIVE","INACTIVE"};
    public static String[] healthPlan={"Plan A","Plan B"};
    public static String[] options={"Delete","Download","Line Graph"};
    //public static DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
    //public static final SimpleDateFormat dobDateFormat = new SimpleDateFormat("MM/dd/yyyy");
    public static final SimpleDateFormat dbDateTimeFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.US);
    public static final SimpleDateFormat dbDateFormat=new SimpleDateFormat("yyyy-MM-dd",Locale.US);
    public static final SimpleDateFormat dobDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
    public static final SimpleDateFormat allergyDate=new SimpleDateFormat("MMM dd, yyyy hh:mm aa",Locale.US);
    public static final Pattern EMAIL_ADDRESS = Pattern.compile("^[a-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+(\\.[a-z0-9,!#\\$%&'\\*\\+/=\\?\\^_`\\{\\|}~-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*\\.([a-z]{2,})$");
}
