package com.skoruz.amwell.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.constants.Constants;
import com.skoruz.amwell.patient.LabDetails;
import com.skoruz.amwell.patientEntity.SavePdf;

import java.util.List;

/**
 * Created by SKoruz-Keerthi on 09-10-2015.
 */
public class LabPdfAdapter extends BaseAdapter {

    private Activity activity;
    private List<SavePdf> savePdfList;
    private LayoutInflater inflater;

    public LabPdfAdapter(Activity activity,List<SavePdf> savePdfList){
        this.activity=activity;
        this.savePdfList=savePdfList;
    }

    @Override
    public int getCount() {
        return savePdfList.size();
    }

    @Override
    public Object getItem(int position) {
        return savePdfList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PdfViewHolder pdfViewHolder;
        if(convertView==null){
            inflater= (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.labpdf_single_row,parent,false);
            pdfViewHolder=new PdfViewHolder();

            pdfViewHolder.fileType= (ImageView) convertView.findViewById(R.id.fileType);
            pdfViewHolder.fileName= (TextView) convertView.findViewById(R.id.fileName);
            pdfViewHolder.fileDate= (TextView) convertView.findViewById(R.id.fileDate);

            convertView.setTag(pdfViewHolder);
        }else{
            pdfViewHolder= (PdfViewHolder) convertView.getTag();
        }

        try {
            SavePdf savePdf = savePdfList.get(position);
            if (savePdf != null) {
                String extension = "";
                int i = savePdf.getFileName().lastIndexOf('.');
                if (i > 0) {
                    extension = savePdf.getFileName().substring(i + 1);
                    pdfViewHolder.fileType.setImageDrawable(activity.getResources().getDrawable(LabDetails.getFileTypeDrawable(extension)));
                }
                pdfViewHolder.fileName.setText(savePdf.getFileName());
                pdfViewHolder.fileDate.setText(Constants.dbDateTimeFormat.format(Constants.dbDateTimeFormat.parse(savePdf.getUploadDate())));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return convertView;
    }

    static class PdfViewHolder{
        ImageView fileType;
        TextView fileName;
        TextView fileDate;
    }
}
