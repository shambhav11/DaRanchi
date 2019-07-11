package com.speedofy.app.daranchi;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DoctorActivity extends AppCompatActivity {
    String name, types;
    Activity activity;
    TextView Name, phoneNumber, presentClinicName, ownerName,showAll;
    RecyclerView patientsRecy, professionRecy;
    List<RecyclerActivityModel> recyclerActivityModels;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);
        Intent intent = getIntent();
        recyclerActivityModels = new ArrayList<>();
        activity=this;
        name = intent.getStringExtra("Name");
        ActionBar actionBar = getSupportActionBar();
        TextView textView = findViewById(R.id.title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(name);
        findViewById(R.id.drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        Name = findViewById(R.id.name);
        showAll = findViewById(R.id.all_patient_text);
        phoneNumber = findViewById(R.id.phone_number);
        presentClinicName = findViewById(R.id.present_clinic_name);
        ownerName = findViewById(R.id.clinic_owner);
        patientsRecy = findViewById(R.id.patients_recy);
        professionRecy = findViewById(R.id.profession_recy);

        Name.setText(name.trim());
        showAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent1.putExtra("Type","Patients");
                intent1.putExtra("Name",name.trim());
                startActivity(intent1);
            }
        });


        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        patientsRecy.setLayoutManager(linearLayoutManager2);
        professionRecy.setLayoutManager(linearLayoutManager3);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Doctors").child(name.trim()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if (snapshot.getKey().contains("Contact Number")) {
                        phoneNumber.setText(snapshot.getValue().toString());
                    } else if (snapshot.getKey().contains("Present Clinic")) {
                        presentClinicName.setText(snapshot.getValue().toString());
                        findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                        presentClinicName.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent1=new Intent(getApplicationContext(),ClinicActivity.class);
                                intent1.putExtra("Name",snapshot.getValue().toString());
                                startActivity(intent1);
                            }
                        });
                        databaseReference.child("Clinics").child(snapshot.getValue().toString()).child("Name of Applicant").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot2) {
                                if(dataSnapshot2.getValue()!=null)
                                ownerName.setText("Applied by" +dataSnapshot2.getValue().toString());
                            }


                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    } else if (snapshot.getKey().contains("Old Affiliations")) {
                        if (snapshot.getValue().toString().contains(",")) {
                            String[] oldClinics = snapshot.getValue().toString().split(",");
                            for (String clinic : oldClinics) {
                                recyclerActivityModels.add(new RecyclerActivityModel(clinic, "From Date - To Date","", "Clinics"));

                            }
                        } else {
                            recyclerActivityModels.add(new RecyclerActivityModel(snapshot.getValue().toString(), "From Date - To Date","", "Clinics"));

                        }
                        InsidePagesRecyclerAdapter recyclerActivityAdapter = new InsidePagesRecyclerAdapter(recyclerActivityModels, "Clinics", 0,getApplicationContext(),activity);
                        professionRecy.setAdapter(recyclerActivityAdapter);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        databaseReference.child("Patients").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recyclerActivityModels=new ArrayList<>();
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String primaryText=dataSnapshot1.getKey();

                    String secondaryText = "";
                    String verificationText = null;
                    int flag=0;
                    for(DataSnapshot snapshot: dataSnapshot1.getChildren()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            if (snapshot1.getKey().contains("Doctor Name") && name.trim().contains(snapshot1.getValue().toString())) {
                                flag = 100*(flag+1);
                                Log.e("TAGGGG",primaryText);
                            }
                            if (snapshot1.getKey().contains("Clinic Name") && presentClinicName.getText().toString().trim().contains(snapshot1.getValue().toString())) {
                                flag = 100*(flag+1);
                                Log.e("TAGGGG",primaryText);
                            }
                            if (snapshot1.getKey().contains("Procedure")) {
                                secondaryText = secondaryText.concat(snapshot1.getValue().toString());

                            }
                            if (snapshot1.getKey().equals("LMP")) {
                                verificationText = snapshot1.getValue().toString();
                            }
                            if (snapshot1.getKey().equals("Date")) {
                                secondaryText = secondaryText.concat("("+ snapshot1.getValue().toString()+") ");
                            }
                            if (snapshot1.getKey().contains("MTP")) {
                                if (snapshot1.getValue().toString().contains("YES"))
                                    flag++;
                            }

                        }
                        Log.e("TAG", String.valueOf(flag));
                    }
                    if(flag>=10000)
                    {
                        Log.e("TAGGGGG", String.valueOf(flag));
                        recyclerActivityModels.add(new RecyclerActivityModel(primaryText,secondaryText,verificationText,"Patients"));
                    }

                    InsidePagesRecyclerAdapter recyclerActivityAdapter=new InsidePagesRecyclerAdapter(recyclerActivityModels,"Patients",0, getApplicationContext(),activity);
                    patientsRecy.setAdapter(recyclerActivityAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
