package com.skoruz.amwell.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.physicianEntity.PhysicianDetails;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Created by SKoruz-Keerthi on 03-11-2015.
 */
public class Utils {

    private static Map<String, String> countries;
    public static final String APP_TAG="TDATC";
    public static final int MEDIA_TYPE_IMAGE=1;
    public static final int RESPONSE_BODY_LIMIT = 1024;

    public static void log(String t, String s) {
    }

    public static void log(String s) {
        log(null, s);
    }

    public static boolean isActivityAlive(Activity activity) {
        return (activity == null || activity.isFinishing()) ? false : true;
    }

    public static boolean isEmptyString(String value) {
        return value == null || value.trim().isEmpty();
    }

    public static String getBaseApplicationId() {
        return BuildConfig.APPLICATION_ID.replace(".production", BuildConfig.FLAVOR) + ".";
    }

    public static String getISOCountryCode(String country) {
        if (countries == null) {
            countries = new HashMap();
            for (String iso : Locale.getISOCountries()) {
                countries.put(new Locale(null, iso).getDisplayCountry().toLowerCase(), iso);
            }
        }
        return (String) countries.get(country.toLowerCase());
    }

    public static boolean isValidEmail(String target) {
        if (target == null) {
            return false;
        }
        return Constants.EMAIL_ADDRESS.matcher(target).matches();
    }

    public static boolean isValidNumber(String mobileStr, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(mobileStr, countryCode);
            if (phoneUtil.isValidNumber(phoneNumber)) {
                PhoneNumberUtil.PhoneNumberType numberType = phoneUtil.getNumberType(phoneNumber);
                if (numberType == PhoneNumberUtil.PhoneNumberType.FIXED_LINE_OR_MOBILE || numberType == PhoneNumberUtil.PhoneNumberType.FIXED_LINE || numberType == PhoneNumberUtil.PhoneNumberType.MOBILE) {
                    return true;
                }
            }
            if (phoneNumber.getCountryCode() == 91 && String.valueOf(phoneNumber.getNationalNumber()).matches("[789]\\d{9}")) {
                return true;
            }
            return false;
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static boolean isValidMobile(String mobileStr, String countryCode) {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        try {
            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(mobileStr, countryCode);
            if (phoneUtil.isValidNumber(phoneNumber) && phoneUtil.getNumberType(phoneNumber) == PhoneNumberUtil.PhoneNumberType.MOBILE) {
                return true;
            }
            if (phoneNumber.getCountryCode() == 91 && String.valueOf(phoneNumber.getNationalNumber()).matches("[789]\\d{9}")) {
                return true;
            }
            return false;
        } catch (NumberParseException e) {
            return false;
        }
    }

    public static String getCountryCode(String countryName) {
        if (countryName.equalsIgnoreCase("india")) {
            return "IN";
        }else if (countryName.equalsIgnoreCase("usa")){
            return "US";
        }
        return null;
    }

    public static String getPhoneNumberWithoutCountryCode(String number) {
        try {
            return PhoneNumberUtil.getInstance().parse(number, BuildConfig.FLAVOR).getNationalNumber() + BuildConfig.FLAVOR;
        } catch (NumberParseException e) {
            e.printStackTrace();
            return BuildConfig.FLAVOR;
        }
    }

    public static PhoneNumber parsePhoneNumber(String mobile, String country) {
        try {
            return PhoneNumberUtil.getInstance().parse(mobile, country);
        } catch (NumberParseException e) {
            return null;
        }
    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager mInputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (!(mInputMethodManager == null || view == null)) {
            mInputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
        if (view != null) {
            view.clearFocus();
        }
    }

    public static boolean isEmptyMap(HashMap map) {
        return map == null || map.size() <= 0;
    }

    public static boolean isNetConnected(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting()) {
            return false;
        }
        return true;
    }
    public static abstract interface OnFragmentInteractionListener{
        public abstract void onFragmentInteraction(Bundle paramBundle);
    }

    public static int getDoctorDefaultImageIndex(PhysicianDetails doctor) {
        if (isFemaleDoctor(doctor)) {
            return 1;
        }
        return 0;
    }

    public static boolean isFemaleDoctor(PhysicianDetails doctor) {
        if (doctor == null || !isFemaleDoctor(doctor.getUser().getGender())) {
            return false;
        }
        return true;
    }

    private static boolean isFemaleDoctor(String gender) {
        return gender != null && gender.toLowerCase(AppController.getInstance().getLocale()).startsWith("f");
    }

    public static int pxToDp(Context context, int px) {
        return (int) (((float) px) / context.getResources().getDisplayMetrics().density);
    }

    public static int dpToPx(Context context, float dp) {
        return (int) TypedValue.applyDimension(1, dp, context.getResources().getDisplayMetrics());
    }

    public static Uri getOutputMediaFileUri(int type){
        return Uri.fromFile(getOutputMediaFile(type));
    }

    public static File getOutputMediaFile(int type){
        String timeStamp=new SimpleDateFormat("yyyyMMdd_HHmmss",Locale.US).format(new Date());
        File mediaFile = null;
        Boolean isSDAvailable= Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        if(isSDAvailable){
            File sdCardDirectory=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),APP_TAG);
            mediaFile=new File(sdCardDirectory.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
        }else{
            File mediaStorageDir= new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),APP_TAG);
            if (!mediaStorageDir.exists()){
                if (!mediaStorageDir.mkdirs()){
                    Log.d(APP_TAG, "Failed to create directory");
                    return null;
                }
            }
            if (type==MEDIA_TYPE_IMAGE){
                mediaFile=new File(mediaStorageDir.getPath()+File.separator+"IMG_"+timeStamp+".jpg");
            }
            else {
                return null;
            }
        }
        return mediaFile;
    }

    public Uri getPhotoFileUri(String fileName){
        if (isExternalStorageAvailable()){
            File mediaStorageDir=new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),APP_TAG);
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG,"failed to create directory");
            }

            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator+ fileName));
        }
        return null;
    }

    public Bitmap rotateBitmapOrientation(String photoFilePath){
        BitmapFactory.Options bounds=new BitmapFactory.Options();
        bounds.inJustDecodeBounds=true;
        BitmapFactory.decodeFile(photoFilePath,bounds);
        BitmapFactory.Options opts=new BitmapFactory.Options();
        Bitmap bm=BitmapFactory.decodeFile(photoFilePath,opts);
        ExifInterface exif=null;
        try {
            exif=new ExifInterface(photoFilePath);
        }catch (Exception e){
            e.printStackTrace();
        }
        String orientString=exif.getAttribute(ExifInterface.TAG_ORIENTATION);
        int orientation=orientString!=null?Integer.parseInt(orientString):ExifInterface.ORIENTATION_NORMAL;
        int rotationAngle=0;
        if (orientation==ExifInterface.ORIENTATION_ROTATE_90) rotationAngle=90;
        if (orientation==ExifInterface.ORIENTATION_ROTATE_180) rotationAngle=180;
        if (orientation==ExifInterface.ORIENTATION_ROTATE_270) rotationAngle=270;

        Matrix matrix=new Matrix();
        matrix.setRotate(rotationAngle,(float)bm.getWidth()/2,(float)bm.getHeight()/2);
        Bitmap rotatedBitmap=Bitmap.createBitmap(bm,0,0,bounds.outWidth,bounds.outHeight,matrix,true);
        return rotatedBitmap;
    }

    public boolean isExternalStorageAvailable(){
        String state=Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }
        return false;
    }

    public static Bitmap cameraRequest(String filePath){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inSampleSize=8;
        final Bitmap bitmap=BitmapFactory.decodeFile(filePath, options);
        final Bitmap rotatedBitmap=rotateImage(bitmap, filePath);
        return rotatedBitmap;
    }

    public static Bitmap galleryRequest(String selectedImagePath,Context context){
        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);
        final Bitmap rotatedBitmap=rotateImage(bm, selectedImagePath);
        return rotatedBitmap;
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }


    public static String decodeSampledBitmap(String fromFilename, String toFilename, int reqWidth, int reqHeight, boolean fixOrientation) {
        try {
            FileInputStream inputStream = new FileInputStream(new File(fromFilename));
            FileOutputStream out;
            if (fixOrientation) {
                out = new FileOutputStream(toFilename);
                getOrientationFixedBitmap(fromFilename, decodeSampledBitmapFromResourceMemOpt(inputStream, reqWidth, reqHeight)).compress(Bitmap.CompressFormat.JPEG, 70, out);
                out.close();
                return toFilename;
            }
            out = new FileOutputStream(toFilename);
            decodeSampledBitmapFromResourceMemOpt(inputStream, reqWidth, reqHeight).compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.close();
            return toFilename;
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("Utils", "error");
            return null;
        }
    }

    public static Bitmap decodeSampledBitmap(String filename, int reqWidth, int reqHeight) {
        Log.d("Utils", "path" + filename);
        try {
            return decodeSampledBitmapFromResourceMemOpt(new FileInputStream(new File(filename)), reqWidth, reqHeight);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Bitmap getOrientationFixedBitmap(String filename, Bitmap bitmap) {
        try {
            int angle = 0;
            switch (new ExifInterface(filename).getAttributeInt("Orientation", 1)) {
                case 3:
                    angle = 180;
                    break;
                case 6:
                    angle = 90;
                    break;
                case 8:
                    angle = 270;
                    break;
            }
            new Matrix().postRotate((float) angle);
            bitmap = rotateImage(bitmap, (float) angle);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    private static Bitmap rotateImage(Bitmap source, float angle) {
        Bitmap bitmap = null;
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        try {
            bitmap = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
            source.recycle();
            return bitmap;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            try {
                Bitmap tempBitmap = source.copy(Bitmap.Config.RGB_565, false);
                bitmap = Bitmap.createBitmap(tempBitmap, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
                source.recycle();
                tempBitmap.recycle();
                return bitmap;
            } catch (Exception e2) {
                return bitmap;
            }
        }
    }

    public static Bitmap decodeSampledBitmapFromResourceMemOpt(InputStream inputStream, int reqWidth, int reqHeight) {
        byte[] byteArr = new byte[0];
        byte[] buffer = new byte[RESPONSE_BODY_LIMIT];
        int count = 0;
        while (true) {
            try {
                int len = inputStream.read(buffer);
                if (len <= -1) {
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    BitmapFactory.decodeByteArray(byteArr, 0, count, options);
                    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
                    options.inPurgeable = true;
                    options.inInputShareable = true;
                    options.inJustDecodeBounds = false;
                    options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                    return BitmapFactory.decodeByteArray(byteArr, 0, count, options);
                } else if (len != 0) {
                    if (count + len > byteArr.length) {
                        byte[] newbuf = new byte[((count + len) * 2)];
                        System.arraycopy(byteArr, 0, newbuf, 0, count);
                        byteArr = newbuf;
                    }
                    System.arraycopy(buffer, 0, byteArr, count, len);
                    count += len;
                }
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int height = options.outHeight;
        int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            int heightRatio = Math.round(((float) height) / ((float) reqHeight));
            int widthRatio = Math.round(((float) width) / ((float) reqWidth));
            if (heightRatio < widthRatio) {
                inSampleSize = heightRatio;
            } else {
                inSampleSize = widthRatio;
            }
            while (((float) (width * height)) / ((float) (inSampleSize * inSampleSize)) > ((float) ((reqWidth * reqHeight) * 2))) {
                inSampleSize++;
            }
        }
        return inSampleSize;
    }

    private static Bitmap rotateImage(Bitmap bitmap, String filePath)
    {
        try {
            int orientation = getExifOrientation(filePath);

            if (orientation == 1) {
                return bitmap;
            }

            Matrix matrix = new Matrix();
            switch (orientation) {
                case 2:
                    matrix.setScale(-1, 1);
                    break;
                case 3:
                    matrix.setRotate(180);
                    break;
                case 4:
                    matrix.setRotate(180);
                    matrix.postScale(-1, 1);
                    break;
                case 5:
                    matrix.setRotate(90);
                    matrix.postScale(-1, 1);
                    break;
                case 6:
                    matrix.setRotate(90);
                    break;
                case 7:
                    matrix.setRotate(-90);
                    matrix.postScale(-1, 1);
                    break;
                case 8:
                    matrix.setRotate(-90);
                    break;
                default:
                    return bitmap;
            }

            try {
                Bitmap oriented = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
                bitmap.recycle();
                return oriented;
            } catch (OutOfMemoryError e) {
                e.printStackTrace();
                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private static int getExifOrientation(String src) throws IOException {
        int orientation = 1;

        try {
            /**
             * if your are targeting only api level >= 5
             * ExifInterface exif = new ExifInterface(src);
             * orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, 1);
             */
            if (Build.VERSION.SDK_INT >= 5) {
                Class<?> exifClass = Class.forName("android.media.ExifInterface");
                Constructor<?> exifConstructor = exifClass.getConstructor(new Class[] { String.class });
                Object exifInstance = exifConstructor.newInstance(new Object[] { src });
                Method getAttributeInt = exifClass.getMethod("getAttributeInt", new Class[] { String.class, int.class });
                Field tagOrientationField = exifClass.getField("TAG_ORIENTATION");
                String tagOrientation = (String) tagOrientationField.get(null);
                orientation = (Integer) getAttributeInt.invoke(exifInstance, new Object[] { tagOrientation, 1});
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        return orientation;
    }

    public static boolean checkLocationPermission(Context context) {
        boolean available = false;
        if (context != null) {
            String coarseLocPermission = "android.permission.ACCESS_COARSE_LOCATION";
            int fine = -1;
            int coarse = -1;
            try {
                fine = context.checkCallingOrSelfPermission("android.permission.ACCESS_FINE_LOCATION");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                coarse = context.checkCallingOrSelfPermission(coarseLocPermission);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (fine == 0 || coarse == 0) {
                available = true;
            }
        }
        log("location permission", "permission :" + available);
        return available;
    }

    public static String getTimeStamp() {
        return "_" + Constants.dbDateTimeFormat.format(new Date()).replaceAll(" ", "_").replaceAll(":", "_");
    }

    public static boolean hasMoreHeap() {
        return Runtime.getRuntime().maxMemory() > 20971520;
    }
}
