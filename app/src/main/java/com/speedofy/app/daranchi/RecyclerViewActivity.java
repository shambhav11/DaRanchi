package com.speedofy.app.daranchi;

import android.app.Activity;
import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {
    private ViewPager viewPager;

    String type;
    String Firstdata,Seconddata;
    RecyclerView itemRecy;
    Activity activity;
    EditText searchBar;
    List<RecyclerActivityModel> recyclerActivityModels;
    RecyclerActivityAdapter recyclerActivityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        itemRecy=findViewById(R.id.item_recy);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        itemRecy.setLayoutManager(linearLayoutManager);
        searchBar=findViewById(R.id.search_bar);
        activity=this;
        ActionBar actionBar = getSupportActionBar();
        final TextView textView=findViewById(R.id.title);
        textView.setVisibility(View.VISIBLE);
        findViewById(R.id.right_drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(searchBar.getVisibility()==View.VISIBLE)
                {
                    searchBar.setVisibility(View.GONE);
                    findViewById(R.id.appbarr).setVisibility(View.VISIBLE);
                }
                else
                {
                    searchBar.setVisibility(View.VISIBLE);
                    findViewById(R.id.appbarr).setVisibility(View.GONE);
                }
            }
        });





        recyclerActivityModels=new ArrayList<>();
        final String type=getIntent().getStringExtra("Type");

        findViewById(R.id.drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();
        if(type.contains("Clinics")) {
            databaseReference.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        textView.setText("Clinics");
                        Firstdata = snapshot.getKey();
                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            if (dataSnapshot1.getKey().contains("Name of Applicant")) {
                                Seconddata = dataSnapshot1.getValue().toString();
                                recyclerActivityModels.add(new RecyclerActivityModel(Firstdata,Seconddata,"",type));
                                break;
                            }
                        }

                    }
                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getApplicationContext(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getApplicationContext(),activity);

                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    itemRecy.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if(type.contains("Doctors"))
        {
            textView.setText("Doctors");
            databaseReference.child("Doctors").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Firstdata = snapshot.getKey();
                        Log.e("TAG","Mail doctors waale loop mei aaya" + Firstdata);

                        for (DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            Log.e("Loop",dataSnapshot1.getKey());
                            if (dataSnapshot1.getKey().contains("Contact Number")) {
                                Seconddata = dataSnapshot1.getValue().toString();
                                recyclerActivityModels.add(new RecyclerActivityModel(Firstdata,Seconddata,"",type));
                                break;
                            }
                        }

                    }
                    if(type.contains("YES"))
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,1,getApplicationContext(),activity);
                    else
                        recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,type,0,getApplicationContext(),activity);

                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    itemRecy.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        else if(type.contains("Patients"))
        {
            textView.setText("Patients");
            recyclerActivityModels= new ArrayList<>();
            databaseReference.child(type).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot snapshot: dataSnapshot.getChildren())
                    {
                        String primaryData=snapshot.getKey();
                        String secondaryData = null,date=null;
                        String  verificationData = null;
                        String name=getIntent().getStringExtra("Name");
                        int flag=0;
                        for(DataSnapshot dataSnapshot1 : snapshot.getChildren()) {
                            for (DataSnapshot snapshot1 : dataSnapshot1.getChildren()) {
                                if (snapshot1.getKey().contains("Clinic Name") && name.contains(snapshot1.getValue().toString())) {
                                    flag++;
                                }
                                if(snapshot1.getKey().contains("Doctor Name") && name.contains(snapshot1.getValue().toString()))
                                {
                                    flag++;
                                }
                                if (snapshot1.getKey().contains("Procedure")) {
                                    secondaryData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().contains("Date")) {
                                    date=snapshot1.getValue().toString();
                                }

                                if (snapshot1.getKey().equals("LMP")) {
                                    verificationData = snapshot1.getValue().toString();
                                }

                            }
                        }
                        if(flag>=1)
                        {
                            recyclerActivityModels.add(new RecyclerActivityModel(primaryData,"("+date+") ",verificationData,"Patients"));
                        }
                    }
                    recyclerActivityAdapter=new RecyclerActivityAdapter(recyclerActivityModels,"Patients",0,getApplicationContext(),activity);
                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    itemRecy.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if(type.contains("Visits")) {
            recyclerActivityModels = new ArrayList<>();
            final String name = getIntent().getStringExtra("Name");

            databaseReference.child("Patients").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String primaryData = null,secondaryData = null,date=null;
                        int flag = 2;
                            for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                                if (snapshot1.getKey().contains("Clinic Name")) {
                                    primaryData=snapshot1.getValue().toString();
                                }

                                if (snapshot1.getKey().contains("Procedure")) {
                                    secondaryData = snapshot1.getValue().toString();
                                }
                                if (snapshot1.getKey().contains("Date")) {
                                    date=snapshot1.getValue().toString();
                                }


                        }
                        if (flag >= 1) {
                            recyclerActivityModels.add(new RecyclerActivityModel(primaryData,"("+date+") "+ secondaryData, " ", "Clinics"));
                        }
                    }
                    recyclerActivityAdapter = new RecyclerActivityAdapter(recyclerActivityModels, "Patients",0, getApplicationContext(),activity);
                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);

                    itemRecy.setAdapter(recyclerActivityAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());

            }
        });
    }


    void filter(String text){
        List<RecyclerActivityModel> temp = new ArrayList();
        for(RecyclerActivityModel d: recyclerActivityModels){
            //or use .equal(text) with you want equal match
            //use .toLowerCase() for better matches
            if(d.getPrimaryData().toLowerCase().contains(text.toLowerCase())){
                temp.add(d);
            }
        }
        //update recyclerview
        recyclerActivityAdapter.updateList(temp);
    }



}
