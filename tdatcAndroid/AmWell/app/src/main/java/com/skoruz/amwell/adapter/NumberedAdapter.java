package com.skoruz.amwell.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.skoruz.amwell.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SKoruz-Keerthi on 03-12-2015.
 */
public class NumberedAdapter extends RecyclerView.Adapter<NumberedAdapter.TextViewHolder> {
    private List<String> labels;

    public NumberedAdapter(int count) {
        labels=new ArrayList<>(count);
        for (int i=0;i<count;i++){
            labels.add(String.valueOf(i));
        }
    }

    public class TextViewHolder extends RecyclerView.ViewHolder{
        public TextView textView;
        public TextViewHolder(View itemView) {
            super(itemView);
            textView= (TextView) itemView.findViewById(R.id.textvalue);
        }
    }


    @Override
    public TextViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        return new TextViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TextViewHolder holder, int position) {
        final String label = labels.get(position);
        holder.textView.setText(label);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(
                        holder.textView.getContext(), label, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return labels.size();
    }
}
