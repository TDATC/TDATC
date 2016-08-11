package com.skoruz.amwell.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.RecyclerViewAdapterMeasurementTool;
import com.skoruz.amwell.utils.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

public class ListOfAllMeasurements extends AppCompatActivity implements RecyclerViewAdapterMeasurementTool.OnItemClickListener {

    //String[] measurementTools;
    List<String> measurementTools;
    RecyclerView recyclerView;
    private RecyclerViewAdapterMeasurementTool adapter;
    private RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_all_measurements);
        measurementTools=new ArrayList<>();
        measurementTools=getIntent().getStringArrayListExtra("items");

        //measurementTools=getResources().getStringArray(R.array.measurementTools);

        recyclerView= (RecyclerView) findViewById(R.id.measurement_tools);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, null));
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new RecyclerViewAdapterMeasurementTool(measurementTools);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);

        /*((MyRecyclerViewAdapter)adapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent myIntent = null;
                Intent valueUpload=null;
                valueUpload=new Intent(ListOfAllMeasurements.this,UploadValue.class);
                Toast.makeText(getApplicationContext(), (String) v.getitemgetIte(position), Toast.LENGTH_SHORT).show();
                valueUpload.putExtra("toolName", (String) listView.getItemAtPosition(position));
                startActivity(valueUpload);
            }
        });*/
    }

    public void onItemClick(RecyclerViewAdapterMeasurementTool.ItemHolder item,int position){
        Intent valueUpload =new Intent(ListOfAllMeasurements.this,UploadValue.class);
        valueUpload.putExtra("toolName", (String) item.getItemName());
        valueUpload.putExtra("toolId",position);
        startActivity(valueUpload);
    }
}
