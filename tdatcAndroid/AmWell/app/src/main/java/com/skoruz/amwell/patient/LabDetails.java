package com.skoruz.amwell.patient;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonArrayRequest;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.LabPdfAdapter;
import com.skoruz.amwell.constants.BaseURL;
import com.skoruz.amwell.constants.MediaSRO;
import com.skoruz.amwell.patientEntity.SavePdf;
import com.skoruz.amwell.utils.AppController;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LabDetails extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = LabDetails.class.getSimpleName();
    static final int PICKFILE_REQUEST_CODE = 107;
    static final int PICKGALLERY_REQUEST_CODE = 108;
    static final int PICKCAMERA_REQUEST_CODE = 109;
    String selectedPath = "";
    Button attachButton,sendButton;
    private ArrayList<MediaSRO> mUploadedMediaList = new ArrayList();
    LinearLayout lnrLyt_attachedFiles,labDocuments;
    private ProgressDialog pDialog;
    MediaSRO mediaSRO;
    SharedPreferences sharedPreferences;
    int patientId;
    private ListView listView;
    private LabPdfAdapter labPdfAdapter;
    ArrayList<SavePdf> savePdfList=new ArrayList<SavePdf>();
    Gson gson=new Gson();
    View localView;
    private ProgressDialog mProgressDialog;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lab_details);
        sharedPreferences=getApplicationContext().getSharedPreferences(getString(R.string.amWellPreference), Context.MODE_PRIVATE);
        patientId=sharedPreferences.getInt("user_id", 0);
        lnrLyt_attachedFiles= (LinearLayout) findViewById(R.id.lnrLyt_attachedFiles);
        labDocuments= (LinearLayout) findViewById(R.id.labDocuments);
        listView= (ListView) findViewById(R.id.pdfList);
        labDocuments.setVisibility(View.GONE);
        attachButton= (Button) findViewById(R.id.attach_button);
        sendButton= (Button) findViewById(R.id.send_button);
        attachButton.setOnClickListener(this);
        sendButton.setOnClickListener(this);

        getLabDocuments(BaseURL.getLabPdfFiles, patientId);
        labPdfAdapter=new LabPdfAdapter(LabDetails.this,savePdfList);
        listView.setAdapter(labPdfAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final int location=position;
                /*String extStorageDirectory = Environment.getExternalStorageDirectory().toString();
                File folder = new File(extStorageDirectory, "pdf");
                folder.mkdir();
                final File file = new File(folder, savePdfList.get(location).getFileName());
                try {
                    file.createNewFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }*/
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                        //Downloader.DownloadFile(BaseURL.getFileDownload + savePdfList.get(location).getFileName(), file);
                        File outFile = new File(Environment.getExternalStorageDirectory() + "/" + savePdfList.get(location).getFileName());
                        downloadFile(BaseURL.getFileDownload + savePdfList.get(location).getFileName(),outFile);
                    }
                };
                thread.start();
                //showPdf();
                //new DownloadFileAsync().execute(BaseURL.getFileDownload + savePdfList.get(location).getFileName());
            }
        });
    }

    private static void downloadFile(String url, File outputFile) {
        try {
            URL u = new URL(url);
            URLConnection conn = u.openConnection();
            int contentLength = conn.getContentLength();

            DataInputStream stream = new DataInputStream(u.openStream());

            byte[] buffer = new byte[contentLength];
            stream.readFully(buffer);
            stream.close();

            DataOutputStream fos = new DataOutputStream(new FileOutputStream(outputFile));
            fos.write(buffer);
            fos.flush();
            fos.close();
        } catch(FileNotFoundException e) {
            e.printStackTrace();
            return; // swallow a 404
        } catch (IOException e) {
            e.printStackTrace();
            return; // swallow a 404
        }
    }


    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_DOWNLOAD_PROGRESS:
                mProgressDialog = new ProgressDialog(this);
                mProgressDialog.setMessage("Downloading file..");
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                mProgressDialog.setCancelable(false);
                mProgressDialog.show();
                return mProgressDialog;
            default:
                return null;
        }
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conn = url.openConnection();
                //conn.connect();

                int lastPoint=url.toString().lastIndexOf('/');
                String fileName="";
                if(lastPoint>0){
                    fileName = url.toString().substring(lastPoint + 1);
                }

                BufferedInputStream inStream = new BufferedInputStream(conn.getInputStream());
                File outFile = new File(Environment.getExternalStorageDirectory() + "/" + fileName);
                FileOutputStream fileStream = new FileOutputStream(outFile);
                BufferedOutputStream outStream = new BufferedOutputStream(fileStream, 1024);
                byte[] data = new byte[1024];
                int bytesRead = 0, totalRead = 0;

                /*int lenghtOfFile = conn.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/some_photo_from_gdansk_poland.jpg");

                byte data[] = new byte[1024];*/

                /*long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    publishProgress(""+(int)((total*100)/lenghtOfFile));
                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();*/

                while((bytesRead = inStream.read(data, 0, data.length)) >= 0)
                {
                    outStream.write(data, 0, bytesRead);

                    // update progress bar
                    //totalRead += bytesRead;
                    //int totalReadInKB = totalRead / 1024;
                }

                outStream.close();
                fileStream.close();
                inStream.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;

        }
        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC",progress[0]);
            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

        @Override
        protected void onPostExecute(String unused) {
            dismissDialog(DIALOG_DOWNLOAD_PROGRESS);
        }
    }

    public void showPdf()
    {
        /*File file = new File(Environment.getExternalStorageDirectory()+"/pdf/Read.pdf");
        PackageManager packageManager = getPackageManager();
        Intent testIntent = new Intent(Intent.ACTION_VIEW);
        testIntent.setType("application/pdf");
        List list = packageManager.queryIntentActivities(testIntent, PackageManager.MATCH_DEFAULT_ONLY);
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);*/

        File file = new File(Environment.getExternalStorageDirectory()+"/pdf/temp.pdf");
        Intent intent = new Intent(Intent.ACTION_VIEW,Uri.fromFile(file));
        intent.setType("application/pdf");
        PackageManager pm = getPackageManager();
        List<ResolveInfo> activities = pm.queryIntentActivities(intent, 0);
        if (activities.size() > 0) {
            startActivity(intent);
        } else {
        }

        /*File file = new File(Environment.getExternalStorageDirectory()+"/pdf/temp.pdf");
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(file);
        intent.setDataAndType(uri, "application/pdf");
        startActivity(intent);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.attach_button:
                //showUploadMediaPicker();
                Intent localIntent = new Intent(Intent.ACTION_GET_CONTENT);
                localIntent.setType("file/*");
                LabDetails.this.startActivityForResult(localIntent, PICKFILE_REQUEST_CODE);
                break;
            case R.id.send_button:
                sendFileToServer(BaseURL.uploadPdf,patientId);
            default:
                break;
        }
    }


    public void getLabDocuments(String url,int userId){
        // Tag used to cancel the request
        String TAB_PDF_GET = "pdfGet";

        savePdfList.clear();
        JsonArrayRequest getToolValue=new JsonArrayRequest(url + userId, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonArray) {
                for (int i=0;i<jsonArray.length();i++){
                    try {
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        SavePdf savePdf=gson.fromJson(jsonObject.toString(),SavePdf.class);
                        savePdfList.add(savePdf);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
                if(savePdfList.size()>0){
                    labDocuments.setVisibility(View.VISIBLE);}else{labDocuments.setVisibility(View.GONE);}
                labPdfAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d(TAG, "Error: " + volleyError.getMessage());
                /*Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();*/
                /*Need to handle the error...custom implementation should be done. */
                if(savePdfList.size()>0){
                    labDocuments.setVisibility(View.VISIBLE);}else{labDocuments.setVisibility(View.GONE);}
                labPdfAdapter.notifyDataSetChanged();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(getToolValue, TAB_PDF_GET);
    }

    public void sendFileToServer(String url, int userId) {
        // Tag used to cancel the request
        String TAB_FILE_UPLOAD = "fileUpload";
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Uploading...");
        pDialog.show();

        MediaSRO mediaSRO=getMediaSRO();

        Gson gson = new Gson();

        SavePdf savePdf=new SavePdf();
        savePdf.setPatientId(userId);
        savePdf.setFileName(mediaSRO.getName());
        savePdf.setFilePath(mediaSRO.getMediaPath());
        savePdf.setFileData(mediaSRO.getFileData());
        savePdf.setUploadDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));

        String jsonString = gson.toJson(savePdf);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(jsonString.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest uploadToolValue = new JsonObjectRequest(Request.Method.POST,
                url, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(AppController.TAG, response.toString());
                        pDialog.dismiss();
                        try {
                            if (response.getString("status").equalsIgnoreCase("success")) {
                                Toast.makeText(getApplicationContext(),"Uploaded Successfully",Toast.LENGTH_SHORT).show();
                                LabDetails.this.lnrLyt_attachedFiles.removeView(localView);
                                getLabDocuments(BaseURL.getLabPdfFiles, patientId);
                            } else if (response.getString("status").equalsIgnoreCase("failure")) {
                                Toast.makeText(getApplicationContext(),"Cannot connect to AmWell. Please try later..",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AppController.TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Cannot connect to AmWell. Please try later..",
                        Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            }
        }) {

            /**
             * Passing some request headers
             */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Content-Type", "application/json");
                return headers;
            }

        };

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(uploadToolValue, TAB_FILE_UPLOAD);
    }

    private void showUploadMediaPicker()
    {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setTitle("Select Image");
        localBuilder.setItems(new CharSequence[] { "Take Picture", "Open Gallery", "Choose File" }, new DialogInterface.OnClickListener()
        {
            private void callCamera()
            {
                try
                {
                    Intent localIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    LabDetails.this.startActivityForResult(localIntent, PICKCAMERA_REQUEST_CODE);
                    return;
                }
                catch (Exception localException) {}
            }

            private void callGallery()
            {
                try
                {
                    Intent localIntent = new Intent();
                    localIntent.setType("image/*");
                    localIntent.setAction(Intent.ACTION_PICK);
                    LabDetails.this.startActivityForResult(Intent.createChooser(localIntent, "Open Gallery"), PICKGALLERY_REQUEST_CODE);
                    return;
                }
                catch (Exception localException) {}
            }

            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
                if (paramAnonymousInt == 0) {
                    callCamera();
                }
                for (;;)
                {
                    paramAnonymousDialogInterface.dismiss();
                    if (paramAnonymousInt == 1) {
                        callGallery();
                    } else if (paramAnonymousInt == 2) {
                        try
                        {
                            Intent localIntent = new Intent("android.intent.action.GET_CONTENT");
                            localIntent.setType("file/*");
                            LabDetails.this.startActivityForResult(localIntent, 107);
                        }
                        catch (Exception localException) {
                            localException.printStackTrace();
                        }
                    }
                    return;
                }
            }
        });
        localBuilder.show();
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        MediaSRO localMediaSRO = null;
        if (resultCode == RESULT_OK) {
            localMediaSRO = new MediaSRO();
            mediaSRO=new MediaSRO();}
            switch (requestCode){
                case PICKFILE_REQUEST_CODE:
                    if(data!=null) {
                        try {
                            Uri localUri = data.getData();
                            getContentResolver().getType(localUri);
                            String str4 = getFilePath(this, localUri);
                            InputStream inputStream = new FileInputStream(new File(str4));
                            byte[] fileData = this.convertToByteArray(inputStream);
                            if (Integer.parseInt(String.valueOf(new File(str4).length() / 1048576L)) > 5) {
                                Toast.makeText(this, "Maximum file upload size limit is 5 MB. Please upload file of size lesser than 5MB.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            localMediaSRO.setMediaPath(str4);
                            localMediaSRO.setMediaType("message_file_type");
                            localMediaSRO.setName(str4.substring(1 + str4.lastIndexOf("/")));
                            localMediaSRO.setFileData(fileData);
                            mediaSRO = localMediaSRO;
                            this.mUploadedMediaList.add(localMediaSRO);
                            addAttachmentView(localMediaSRO);
                            if (this.mUploadedMediaList.size() == 1) {
                                this.attachButton.setEnabled(false);
                                this.attachButton.setVisibility(View.GONE);
                                this.sendButton.setVisibility(View.VISIBLE);
                                return;
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                break;
                case PICKGALLERY_REQUEST_CODE:
                    if (data!=null){
                        String str3=getSelectedFilePath(data,this);
                        File localFile= new File(str3);
                        if (Integer.parseInt(String.valueOf(localFile.length() / 1048576L)) > 5)
                        {
                            Toast.makeText(this, "Maximum file upload size limit is 5 MB. Please upload file of size lesser than 5MB.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        localMediaSRO.setMediaPath(str3);
                        localMediaSRO.setMediaType("message_image_type");
                        localMediaSRO.setName(str3.substring(1 + str3.lastIndexOf("/")));
                        mediaSRO=localMediaSRO;
                        this.mUploadedMediaList.add(localMediaSRO);
                        addAttachmentView(localMediaSRO);
                        if (this.mUploadedMediaList.size() == 1) {
                            this.attachButton.setEnabled(false);
                            this.attachButton.setVisibility(View.GONE);
                            this.sendButton.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    break;
                case PICKCAMERA_REQUEST_CODE:
                    if (data!=null){
                        String str2=getCameraImagePath(data);
                        File localFile= new File(str2);
                        if (Integer.parseInt(String.valueOf(localFile.length() / 1048576L)) > 5)
                        {
                            Toast.makeText(this, "Maximum file upload size limit is 5 MB. Please upload file of size lesser than 5MB.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        localMediaSRO.setMediaPath(str2);
                        localMediaSRO.setMediaType("message_image_type");
                        localMediaSRO.setName(str2.substring(1 + str2.lastIndexOf("/")));
                        mediaSRO=localMediaSRO;
                        this.mUploadedMediaList.add(localMediaSRO);
                        addAttachmentView(localMediaSRO);
                        if (this.mUploadedMediaList.size() == 1) {
                            this.attachButton.setEnabled(false);
                            this.attachButton.setVisibility(View.GONE);
                            this.sendButton.setVisibility(View.VISIBLE);
                            return;
                        }
                    }
                    break;
                default:
                    break;
            }
        }

    private byte[] convertToByteArray(InputStream inputStream) throws IOException {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();

        int next = inputStream.read();
        while (next > -1) {
            bos.write(next);
            next = inputStream.read();
        }

        bos.flush();

        return bos.toByteArray();
    }

    public MediaSRO getMediaSRO(){
        return mediaSRO;
    }

    public static String getSelectedFilePath(Intent paramIntent, Context paramContext)
    {
        Uri localUri = paramIntent.getData();
        ContentResolver localContentResolver = paramContext.getContentResolver();
        String[] arrayOfString = { "_data" };
        Cursor localCursor = localContentResolver.query(localUri, arrayOfString, null, null, null);
        localCursor.moveToFirst();
        String str = localCursor.getString(localCursor.getColumnIndex(arrayOfString[0]));
        localCursor.close();
        return str;
    }

    public static String getCameraImagePath(Intent paramIntent)
    {
        try
        {
            Bitmap localBitmap = (Bitmap)paramIntent.getExtras().get("data");
            File localFile1 = new File(Environment.getExternalStorageDirectory().toString() + "/Tdatc");
            localFile1.mkdirs();
            int i = new Random().nextInt(10000);
            File localFile2 = new File(localFile1, "Image-" + i + ".jpg");
            if (localFile2.exists()) {
                localFile2.delete();
            }
            FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
            localBitmap.compress(Bitmap.CompressFormat.JPEG, 90, localFileOutputStream);
            localFileOutputStream.flush();
            localFileOutputStream.close();
            String str = localFile2.getAbsolutePath();
            return str;
        }
        catch (Exception localException)
        {
            localException.printStackTrace();
            return "";
        }
    }

    public static String getFilePath(Context paramContext, Uri paramUri)
    {
        try
        {
            //this is for camera and gallery
            if ("content".equalsIgnoreCase(paramUri.getScheme()))
            {
                String[] arrayOfString = { "_data" };
                Cursor localCursor = paramContext.getContentResolver().query(paramUri, arrayOfString, null, null, null);
                int i = localCursor.getColumnIndexOrThrow("_data");
                if (localCursor.moveToFirst()) {
                    return localCursor.getString(i);
                }
            }
            else if ("file".equalsIgnoreCase(paramUri.getScheme()))
            {
                String str = paramUri.getPath();
                return str;
            }
        }
        catch (Exception localException) {}
        return null;
    }

    private void addAttachmentView(final MediaSRO paramMediaSRO)
    {
        localView = ((LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE)).inflate(R.layout.row_attachment, null, true);
        TextView localCustomFontTextView = (TextView)localView.findViewById(R.id.txtVw_mediaName);
        ImageView localImageView1 = (ImageView)localView.findViewById(R.id.imgVw_removeAttachment);
        ImageView localImageView2 = (ImageView)localView.findViewById(R.id.imgVw_attachemntThumb);
        localView.findViewById(R.id.vw_divider);
        if (paramMediaSRO.getMediaType().equals("message_file_type")) {
            localImageView2.setImageDrawable(getResources().getDrawable(getFileTypeDrawable(paramMediaSRO.getMediaPath().substring(1 + paramMediaSRO.getMediaPath().lastIndexOf(".")))));
        }
        for (;;)
        {
            localCustomFontTextView.setText(paramMediaSRO.getName());
            localImageView1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    LabDetails.this.lnrLyt_attachedFiles.removeView(localView);
                    LabDetails.this.mUploadedMediaList.remove(paramMediaSRO);
                    if (LabDetails.this.mUploadedMediaList.size() < 1) {
                        LabDetails.this.attachButton.setVisibility(View.VISIBLE);
                        LabDetails.this.attachButton.setEnabled(true);
                        LabDetails.this.sendButton.setVisibility(View.GONE);
                    }
                }
            });
            this.lnrLyt_attachedFiles.addView(localView);
            File localFile = new File(paramMediaSRO.getMediaPath());
            if (localFile.exists()) {
                localImageView2.setImageBitmap(BitmapFactory.decodeFile(localFile.getAbsolutePath()));
            }
            break;
        }
    }

    public static int getFileTypeDrawable(String paramString)
    {
        int i = R.drawable.ic_file_other;
        if (paramString.contains("pdf")) {
            i = R.drawable.ic_file_pdf;
        }
        /*do
        {
            return i;
            if ((paramString.contains("doc")) || (paramString.contains("docx"))) {
                return R.drawable.ic_file_word;
            }
        } while ((!paramString.contains("xls")) && (!paramString.contains("xlsx")));
        return R.drawable.ic_file_excel;*/
        return i;
    }
}
