package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.skoruz.amwell.R;

import java.util.List;

/**
 * Created by Skoruz-Ashish on 9/4/2015.
 */
public class RecyclerViewAdapterMeasurementTool extends RecyclerView.Adapter<RecyclerViewAdapterMeasurementTool.ItemHolder> {

    private List<String> toolNames;
    private OnItemClickListener onItemClickListener;

    public RecyclerViewAdapterMeasurementTool(List<String> toolNames){
        this.toolNames=toolNames;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_measurement_tool,parent,false);
        ItemHolder itemHolder=new ItemHolder(view,this);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.toolName.setText(toolNames.get(position));
    }

    @Override
    public int getItemCount() {
        return toolNames.size();
    }

    public static class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private RecyclerViewAdapterMeasurementTool parent;
        TextView toolName;

        public ItemHolder(View itemView,RecyclerViewAdapterMeasurementTool parent) {
            super(itemView);
            toolName= (TextView) itemView.findViewById(R.id.tool_name);
            this.parent=parent;
            itemView.setOnClickListener(this);
        }

        public void setItemName(CharSequence name){
            toolName.setText(name);
        }

        public CharSequence getItemName(){
            return toolName.getText();
        }

        @Override
        public void onClick(View v) {
            final OnItemClickListener listener = parent.getOnItemClickListener();
            if(listener != null){
                listener.onItemClick(this, getPosition());
            }
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener(){
        return onItemClickListener;
    }

    public interface OnItemClickListener{
        public void onItemClick(ItemHolder item, int position);
    }
}
