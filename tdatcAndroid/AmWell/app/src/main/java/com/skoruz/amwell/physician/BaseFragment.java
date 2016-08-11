package com.skoruz.amwell.physician;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AlertDialog.Builder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.skoruz.amwell.BuildConfig;
import com.skoruz.amwell.R;
import com.skoruz.amwell.misc.FileUtils;
import com.skoruz.amwell.utils.AppController;
import com.skoruz.amwell.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


public class BaseFragment extends Fragment implements OnClickListener{
    private static final long MAX_FILE_SIZE_KB = 10485760;
    protected static final int REQUEST_CAPTURE_IMAGE = 5011;
    protected static final int REQUEST_SELECT_IMAGE = 5007;
    private AppCompatActivity mActivity;
    protected String mCurrentPhotoPath;
    private final BroadcastReceiver mNetworkStateReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            BaseFragment.this.isConnected();
        }
    };
    private Snackbar mNoInternetSnackbar;
    protected String mOriginalImagePathString;
    private AlertDialog mRequestPhotoDialog;
    protected File mUploadCacheFolder;
    protected String mUploadImagePathString;
    public static final String BUNDLE_USER_FILE_NAME = "bundle_user_file_name";
    public static final String IMAGE_CACHE_PROFILE_DIR = "upload_files";
    public static final int IMAGE_DIMENSION = 780;
    public static final int PROFILE_THUMBNAIL_LIMIT = 4;
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", AppController.getInstance().getLocale());
    public static SimpleDateFormat displayDateFormat = new SimpleDateFormat("dd MMMM yyyy", AppController.getInstance().getLocale());
    public static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", AppController.getInstance().getLocale());

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = (AppCompatActivity) activity;
        super.onAttach(activity);
    }

    public void onResume() {
        super.onResume();
        getParentActivity().registerReceiver(this.mNetworkStateReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    public void onPause() {
        super.onPause();
        if (this.mNetworkStateReceiver != null) {
            getParentActivity().unregisterReceiver(this.mNetworkStateReceiver);
        }
    }

    public void initLoader(int loaderId, boolean isReset, LoaderCallbacks<?> loaderCallbacks) {
        if (isReset) {
            getLoaderManager().restartLoader(loaderId, null, loaderCallbacks);
        } else {
            getLoaderManager().initLoader(loaderId, null, loaderCallbacks);
        }
    }

    public void initEditToolbar(Toolbar toolbar, String title, boolean showCloseIcon) {
        AppCompatActivity activity = getParentActivity();
        activity.setSupportActionBar(toolbar);
        activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        activity.setTitle(BuildConfig.FLAVOR);
        ((TextView) toolbar.findViewById(R.id.toolbar_title)).setText(title);
        if (showCloseIcon) {
            activity.getSupportActionBar().setHomeAsUpIndicator(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_navigation_close, null));
        }
    }

    public AppCompatActivity getParentActivity() {
        return this.mActivity;
    }

    protected void showAddPhotoDialog() {
        View dialogView = getParentActivity().getLayoutInflater().inflate(R.layout.dialog_add_photo, null);
        dialogView.findViewById(R.id.button_camera).setOnClickListener(this);
        dialogView.findViewById(R.id.button_gallery).setOnClickListener(this);
        this.mRequestPhotoDialog = new Builder(getParentActivity()).setView(dialogView).create();
        this.mRequestPhotoDialog.show();
    }

    protected void showAttachmentDialog() {
        showAddPhotoDialog();
    }

    protected Uri createImageFile() throws NullPointerException {
        createCacheFolder();
        File originalImagePath = getPath(this.mUploadCacheFolder.getPath(), true);
        File uploadImagePath = getPath(this.mUploadCacheFolder.getPath(), false);
        try {
            Uri uploadFileUri = Uri.fromFile(originalImagePath);
            this.mOriginalImagePathString = originalImagePath.getAbsolutePath();
            this.mUploadImagePathString = uploadImagePath.getAbsolutePath();
            this.mCurrentPhotoPath = "file:" + originalImagePath.getAbsolutePath();
            return uploadFileUri;
        } catch (NullPointerException e) {
            Utils.log("Cannot find a place to put photo");
            Toast.makeText(getParentActivity(), getString(R.string.can_not_capture_photo), Toast.LENGTH_LONG).show();
            return null;
        }
    }

    protected void createCacheFolder() {
        this.mUploadCacheFolder = com.android.volley.misc.Utils.getDiskCacheDir(getParentActivity(), IMAGE_CACHE_PROFILE_DIR + File.separator + getParentActivity().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE).getInt("user_id", 0));
        if (!this.mUploadCacheFolder.exists()) {
            this.mUploadCacheFolder.mkdirs();
        }
    }

    protected File getPath(String path, boolean original) {
        File imageFolder = new File(path + File.separator + BUNDLE_USER_FILE_NAME + File.separator + (original ? "original" : "upload"));
        if (!imageFolder.exists()) {
            imageFolder.mkdirs();
        }
        return new File(imageFolder.getPath() + File.separator + BUNDLE_USER_FILE_NAME + getTimeStamp() + ".jpg");
    }

    private String getTimeStamp() {
        return "_" + dateFormat.format(new Date()).replaceAll(" ", "_").replaceAll(":", "_");
    }

    protected void setPicture(Uri imageUri, ImageView destination) {
        try {
            File imageFile = FileUtils.getFile(getParentActivity(), imageUri);
            if (imageFile == null) {
                Toast.makeText(getParentActivity(), getString(R.string.edit_photo_error_failure), Toast.LENGTH_SHORT).show();
            } else if (imageFile.length() > MAX_FILE_SIZE_KB) {
                Toast.makeText(getParentActivity(), getString(R.string.edit_photo_error_size), Toast.LENGTH_SHORT).show();
            } else {
                String imagePath = FileUtils.getPath(getParentActivity(), imageUri);
                if (destination == null) {
                    throw new NullPointerException();
                }
                int targetW = destination.getWidth();
                int targetH = destination.getHeight();
                Options bmOptions = new Options();
                bmOptions.inJustDecodeBounds = true;
                BitmapFactory.decodeFile(imagePath, bmOptions);
                int photoW = bmOptions.outWidth;
                int photoH = bmOptions.outHeight;
                if (((photoW == 0 ? 1 : 0) | (((targetH == 0 ? 1 : 0) | (targetW == 0 ? 1 : 0)) | (photoH == 0 ? 1 : 0))) != 0) {
                    throw new ArithmeticException();
                }
                int scaleFactor = Math.min(photoW / targetW, photoH / targetH);
                bmOptions.inJustDecodeBounds = false;
                bmOptions.inSampleSize = scaleFactor;
                bmOptions.inPurgeable = true;
                destination.setImageBitmap(BitmapFactory.decodeFile(imagePath, bmOptions));
            }
        } catch (Exception e) {
            Toast.makeText(getParentActivity(), getString(R.string.edit_photo_error_failure), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_camera:
                this.mRequestPhotoDialog.dismiss();
                Activity activity = getActivity();
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(activity.getPackageManager()) != null) {
                    Uri uploadFileUri = createImageFile();
                    if (uploadFileUri != null) {
                        cameraIntent.putExtra("output", uploadFileUri);
                        startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
                        return;
                    }
                    Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.can_not_capture_photo), Snackbar.LENGTH_LONG)
                            .setAction(getString(R.string.ok), new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                    //new SnackBar.Builder(getActivity()).withMessageId(R.string.can_not_capture_photo).withActionMessageId(R.string.ok).show();
                    return;
                }
                Snackbar.make(getActivity().findViewById(android.R.id.content), getString(R.string.no_camera), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.ok), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                //new SnackBar.Builder(getActivity()).withMessageId(R.string.no_camera).withActionMessageId(R.string.ok).show();
                return;
            case R.id.button_gallery:
                this.mRequestPhotoDialog.dismiss();
                Intent intentSelect = new Intent(Intent.ACTION_GET_CONTENT);
                intentSelect.setType(FileUtils.MIME_TYPE_IMAGE);
                startActivityForResult(Intent.createChooser(intentSelect, getString(R.string.select_picture)), REQUEST_SELECT_IMAGE);
                return;
            default:
                return;
        }
    }

    protected void showNoNetworkSnackbar(View view) {
        if (view != null) {
            this.mNoInternetSnackbar = Snackbar.make(view, R.string.no_network_connection,Snackbar.LENGTH_INDEFINITE);
            this.mNoInternetSnackbar.show();
        }
    }

    protected void hideNoInternetSnackbar() {
        if (this.mNoInternetSnackbar != null) {
            this.mNoInternetSnackbar.dismiss();
        }
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = ((ConnectivityManager) getParentActivity().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected()) {
            showNoNetworkSnackbar(getView());
            return false;
        }
        hideNoInternetSnackbar();
        return true;
    }

    protected byte[] convertToByteArray(InputStream inputStream) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int next = inputStream.read();
        while (next > -1) {
            bos.write(next);
            next = inputStream.read();
        }

        bos.flush();

        return bos.toByteArray();
    }

    public boolean isSuccessfulResponse(NetworkResponse response) {
        switch (response.statusCode) {
            case 200:
            case 201:
            case 202:
                return true;
            default:
                return false;
        }
    }
}

