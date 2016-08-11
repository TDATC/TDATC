package com.skoruz.amwell.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;

/**
 * Created by Skoruz-Ashish on 8/31/2015.
 */
public class CustomGridAdapter extends BaseAdapter {
    private Context context;
    private final String[] names;
    private final int[] image_id;

    public CustomGridAdapter(String[] names, int[] image_id, Context context) {
        this.names = names;
        this.image_id = image_id;
        this.context = context;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return names[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItem viewHolderItem;
        if(convertView==null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.healthrecord_singlegrid,parent,false);

            viewHolderItem=new ViewHolderItem();

            viewHolderItem.itemName= (TextView) convertView.findViewById(R.id.grid_text);
            viewHolderItem.itemImage= (ImageView) convertView.findViewById(R.id.grid_image);

            convertView.setTag(viewHolderItem);
        }else {
            viewHolderItem= (ViewHolderItem) convertView.getTag();
        }

        viewHolderItem.itemImage.setImageResource(image_id[position]);
        viewHolderItem.itemName.setText(names[position]);

        return convertView;
    }

    static class ViewHolderItem{
        TextView itemName;
        ImageView itemImage;
    }
}
