package com.skoruz.amwell.patient;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.skoruz.amwell.R;
import com.skoruz.amwell.adapter.MyRecyclerViewAdapter;
import com.skoruz.amwell.utils.DividerItemDecoration;


public class MyHealthFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    String[] names;
    int[]image_id={R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,
            R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp,R.drawable.ic_android_black_18dp};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_health,container,false);
        names=getResources().getStringArray(R.array.homeListNames);
        recyclerView= (RecyclerView) view.findViewById(R.id.my_recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),null));
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        adapter=new MyRecyclerViewAdapter(names,image_id);
        recyclerView.setAdapter(adapter);

        ((MyRecyclerViewAdapter)adapter).setOnItemClickListener(new MyRecyclerViewAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent myIntent = null;
                switch (position) {
                    case 0:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        startActivity(myIntent);
                        break;
                    case 1:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        //myIntent=new Intent(Patient_Home.this,HealthHistory.class);
                        startActivity(myIntent);
                        break;
                    case 2:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        // myIntent=new Intent(Patient_Home.this,ToDos.class);
                        startActivity(myIntent);
                        break;
                    case 3:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        //myIntent=new Intent(Patient_Home.this,Appointments.class);
                        startActivity(myIntent);
                        break;
                    case 4:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        //myIntent=new Intent(Patient_Home.this,HealthReferences.class);
                        startActivity(myIntent);
                        break;
                    case 5:
                        myIntent = new Intent(getActivity(), HealthRecord.class);
                        //myIntent=new Intent(Patient_Home.this,HealthDocuments.class);
                        startActivity(myIntent);
                        break;
                    case 6:
                        myIntent = new Intent(getActivity(), MyDoctorsActivity.class);
                        //myIntent=new Intent(Patient_Home.this,Providers.class);
                        startActivity(myIntent);
                        break;
                    case 7:
                        myIntent = new Intent(getActivity(), LabDetails.class);
                        //myIntent=new Intent(Patient_Home.this,Providers.class);
                        startActivity(myIntent);
                        break;
                    case 8:
                        myIntent = new Intent(getActivity(), Remainders.class);
                        //myIntent=new Intent(Patient_Home.this,Providers.class);
                        startActivity(myIntent);
                        break;
                    default:
                        break;

                }
            }
        });

        return view;
    }
}
