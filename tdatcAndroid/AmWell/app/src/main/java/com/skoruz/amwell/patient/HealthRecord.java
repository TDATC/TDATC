package com.skoruz.amwell.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.CustomGridAdapter;
import com.skoruz.amwell.utils.ExpandableHeightGridView;

import java.util.ArrayList;
import java.util.Arrays;

public class HealthRecord extends AppCompatActivity {

    ExpandableHeightGridView healthRecordList,health_history;
    /*String[] names={"Blood Pressure","Temperature","Weight","Blood Glucose","Dietary Calcium","Dietary Carbohydrates"
            ,"Dietary Cholesterol","Vitamin A","Vitamin C","Vitamin D","Vitamin E","more"};*/
    int[]image_id={R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,
            R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,
            R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp};
    //String[] names;
    ArrayList<String> names;
    String[] toolName;
    ArrayList<String> toolNames=new ArrayList<String>();
    String[] history_names={"Medication","Allergies","Procedures"};
    int[] history_image_id={R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp};
    CustomGridAdapter customGridAdapter,historyAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_record);
        toolNames.clear();
        names= new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.measurementTools)));

        for (int i=0;i<11;i++){
            toolNames.add(names.get(i));
        }

        toolNames.add("more");

        toolName=new String[toolNames.size()];
        toolName=toolNames.toArray(toolName);

        final ArrayList<String> remainingElements=new ArrayList<String>(names.subList(11,names.size()));

        healthRecordList= (ExpandableHeightGridView) findViewById(R.id.grid);
        healthRecordList.setExpanded(true);
        customGridAdapter=new CustomGridAdapter(toolName,image_id,HealthRecord.this);
        healthRecordList.setAdapter(customGridAdapter);
        customGridAdapter.notifyDataSetChanged();

        health_history= (ExpandableHeightGridView) findViewById(R.id.personal_grid);
        health_history.setExpanded(true);
        historyAdapter=new CustomGridAdapter(history_names,history_image_id,HealthRecord.this);
        health_history.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();

        healthRecordList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent valueUpload = null;
                switch (position) {
                    case 11:
                        valueUpload = new Intent(HealthRecord.this, ListOfAllMeasurements.class);
                        valueUpload.putStringArrayListExtra("items",remainingElements);
                        startActivity(valueUpload);
                        break;
                    default:
                        valueUpload = new Intent(HealthRecord.this, UploadValue.class);
                        valueUpload.putExtra("toolName", (String) healthRecordList.getItemAtPosition(position));
                        valueUpload.putExtra("toolId", position);
                        startActivity(valueUpload);
                        break;
                }
            }
        });

        health_history.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent personalHistory=null;
                switch (position){
                    case 2:
                        break;
                    default:
                        personalHistory=new Intent(HealthRecord.this,PersonalHistory.class);
                        personalHistory.putExtra("historyName",(String) health_history.getItemAtPosition(position));
                        personalHistory.putExtra("historyId",position);
                        startActivity(personalHistory);
                        break;
                }
            }
        });
    }
}
