package com.skoruz.amwell.patient;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.skoruz.amwell.R;
import com.skoruz.amwell.patientEntity.MeasurementToolOldData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class LineGraph extends AppCompatActivity {

    ArrayList<MeasurementToolOldData> dataArrayList;
    String toolName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line_graph);

        dataArrayList=getIntent().getParcelableArrayListExtra("toolList");
        toolName=getIntent().getStringExtra("toolName");

        GraphView graph = (GraphView) findViewById(R.id.graph);
        graph.getGridLabelRenderer().setVerticalAxisTitle(toolName);
        //graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        /*LineGraphSeries<DataPoint> series=new LineGraphSeries<DataPoint>(generateData());*/

        /*Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d4 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d5 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d6 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d7 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d8 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d9 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d10 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d11 = calendar.getTime();*/



        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1=null,d2 = null;
        if(dataArrayList!=null && !dataArrayList.isEmpty()){
            try {
                calendar.setTime(new SimpleDateFormat("MMM dd, yyyy hh:mm a").parse(dataArrayList.get(0).getUploadedDate()));
                d1=calendar.getTime();
                calendar.setTime(new SimpleDateFormat("MMM dd, yyyy hh:mm a").parse(dataArrayList.get(dataArrayList.size()-1).getUploadedDate()));
                d2 = calendar.getTime();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(generateData());

       /* LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[] {


                new DataPoint(6, 10),
                new DataPoint(8, 50),
                new DataPoint(d3, 30),
                new DataPoint(d4, 20),
                new DataPoint(d5, 60),
                new DataPoint(d6, 100),
                new DataPoint(d7, 55),
                new DataPoint(d8, 35),
                new DataPoint(d9, 25),
                new DataPoint(d10, 65),
                new DataPoint(d11, 70),

        });*/
        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(LineGraph.this, (new SimpleDateFormat("MMM dd"))));
        graph.getGridLabelRenderer().setNumHorizontalLabels(7);

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d2.getTime());
        graph.getViewport().setMaxX(d1.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

        series.setOnDataPointTapListener(new OnDataPointTapListener() {
            @Override
            public void onTap(Series series, DataPointInterface dataPoint) {
                Toast.makeText(getApplicationContext(), "Series1: On Data Point clicked: " + dataPoint, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private DataPoint[] generateData() {

        DataPoint[] values = new DataPoint[dataArrayList.size()];
        for (int i=0; i<dataArrayList.size(); i++) {

            String uploadedValue=dataArrayList.get(i).getUploadedValue();
            String uploadedDate=dataArrayList.get(i).getUploadedDate();

            try{
                Calendar calendar= Calendar.getInstance();
                calendar.setTime(new SimpleDateFormat("MMM dd, yyyy hh:mm a").parse(uploadedDate));
                Date date=calendar.getTime();
                Integer value=Integer.parseInt(uploadedValue);

                DataPoint v = new DataPoint(date, value);
                values[i] = v;
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return values;
    }

}
