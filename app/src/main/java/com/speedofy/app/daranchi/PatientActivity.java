package com.speedofy.app.daranchi;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PatientActivity extends AppCompatActivity {
    String name,type,procedure,nameClinic,date;
    TextView Name,phoneNumber,Address,childrenNumber,LMPDate,showALL,mtp;
    RecyclerView childrenRecy,clinicsRecy;
    String  LMP;
    Activity activity;
    List<RecyclerActivityModel> recyclerActivityModels,visitModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient);
        Intent intent=getIntent();
        name = intent.getStringExtra("Name");
        LMP=intent.getStringExtra("Verify");
        recyclerActivityModels=new ArrayList<>();
        activity=this;
        visitModels=new ArrayList<>();
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
        phoneNumber = findViewById(R.id.phone_number);
        Address = findViewById(R.id.address);
        mtp = findViewById(R.id.MTP);
        showALL = findViewById(R.id.all_patient_text);
        LMPDate = findViewById(R.id.LMP);
        childrenNumber = findViewById(R.id.children_number_text);
        clinicsRecy = findViewById(R.id.clinics_recy);
        childrenRecy = findViewById(R.id.children_recy);
        Name.setText(name);
        showALL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent1.putExtra("Type","Visits");
                intent1.putExtra("Name",name);
                startActivity(intent1);
            }
        });

        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        LinearLayoutManager linearLayoutManager3 = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        clinicsRecy.setLayoutManager(linearLayoutManager2);
        childrenRecy.setLayoutManager(linearLayoutManager3);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference = firebaseDatabase.getReference();

        databaseReference.child("Patients").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    recyclerActivityModels=new ArrayList<>();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {


                        if (snapshot1.getKey().contains("Contact Number")) {
                            phoneNumber.setText(snapshot1.getValue().toString());
                        }
                        else if (snapshot1.getKey().contains("Date")) {
                            date=snapshot1.getValue().toString();
                        } else if (snapshot1.getKey().contains("Address")) {
                            Address.setText(snapshot1.getValue().toString());


                        } else if (snapshot1.getKey().contains("Total Children")) {
                            childrenNumber.setText(snapshot1.getValue().toString());

                        } else if (snapshot1.getKey().contains("Indication of")) {
                            mtp.setText(snapshot1.getValue().toString());

                        } else if (snapshot1.getKey().equals("LMP")) {
                            if(snapshot1.getValue().toString().contains(LMP))
                            LMPDate.setText(snapshot1.getValue().toString());
                            else
                            {
                                LMPDate.setText("2 LMP Dates Found : " + LMP + "\n" + snapshot1.getValue().toString());
                            }

                        } else if (snapshot1.getKey().contains("Age of ")) {
                            recyclerActivityModels.add(new RecyclerActivityModel(snapshot1.getKey() + " in Months ", snapshot1.getValue().toString(), "", "Missing"));

                        } else if (snapshot1.getKey().contains("Age of ")) {
                            recyclerActivityModels.add(new RecyclerActivityModel(snapshot1.getKey() + " in Months ", snapshot1.getValue().toString(),"", "Missing"));

                        } else if (snapshot1.getKey().contains("Procedure")) {
                            procedure = snapshot1.getValue().toString();
                        } else if (snapshot1.getKey().contains("Clinic Name")) {
                            nameClinic = snapshot1.getValue().toString();
                        }
                    }



                    visitModels.add(new RecyclerActivityModel(nameClinic,"("+date+")"+ procedure, "","Clinics"));
                    InsidePagesRecyclerAdapter recyclerActivityAdapter2 = new InsidePagesRecyclerAdapter(visitModels, "Clinics",0, getApplicationContext(),activity);
                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                    clinicsRecy.setAdapter(recyclerActivityAdapter2);
                    InsidePagesRecyclerAdapter recyclerActivityAdapter = new InsidePagesRecyclerAdapter(recyclerActivityModels, "Missing",0, getApplicationContext(),activity);
                    childrenRecy.setAdapter(recyclerActivityAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
