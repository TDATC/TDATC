package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.skoruz.amwell.R;

/**
 * Created by Skoruz-Ashish on 9/2/2015.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.DataObjectHolder> {

    private String[] names;
    private int[] image_id;
    private static MyClickListener myClickListener;
    @Override
    public DataObjectHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.myhealth_singleitem,parent,false);
        DataObjectHolder dataObjectHolder=new DataObjectHolder(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(DataObjectHolder holder, int position) {
        holder.itemName.setText(names[position]);
        holder.itemImage.setImageResource(image_id[position]);
    }

    @Override
    public int getItemCount() {
        return names.length;
    }

    public static class DataObjectHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView itemName;
        ImageView itemImage;

        public DataObjectHolder(View itemView) {
            super(itemView);
            itemName= (TextView) itemView.findViewById(R.id.item_name);
            itemImage= (ImageView) itemView.findViewById(R.id.item_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            myClickListener.onItemClick(getAdapterPosition(),v);
        }
    }

    public void setOnItemClickListener(MyClickListener myClickListener){
        this.myClickListener=myClickListener;
    }

    public MyRecyclerViewAdapter(String[] names, int[] image_id){
        this.names=names;
        this.image_id=image_id;
    }

    public interface MyClickListener {
        public void onItemClick(int position, View v);
    }
}
