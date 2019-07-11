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
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ClinicActivity extends AppCompatActivity {
    String Name;
    Activity activity;
    TextView name,address,phoneNumber,expiryDate,fileNumber,additionalInformation,showPatients;
    RecyclerView machineRecy,doctorRecy,patientRecy,mtpIndicationRecy,missingRecy;
    List<RecyclerActivityModel> recyclerActivityModels,machineModel,patientModel,patientMTPModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);
        final Intent intent=getIntent();
        Name=intent.getStringExtra("Name");
        recyclerActivityModels=new ArrayList<>();
        machineModel=new ArrayList<>();
        patientModel=new ArrayList<>();
        patientMTPModel=new ArrayList<>();
        ActionBar actionBar = getSupportActionBar();
        TextView textView=findViewById(R.id.title);
        textView.setVisibility(View.VISIBLE);
        textView.setText(Name);
        findViewById(R.id.drawer_open).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.additionalframe).setVisibility(View.GONE);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        phoneNumber=findViewById(R.id.phone_number);
        expiryDate=findViewById(R.id.expiry_date);
        showPatients=findViewById(R.id.all_patient_text);
        fileNumber=findViewById(R.id.file_number);
        additionalInformation=findViewById(R.id.additional_information);
        machineRecy=findViewById(R.id.machine_recy);
        doctorRecy=findViewById(R.id.doctor_recy);
        patientRecy=findViewById(R.id.patient_recy);
        mtpIndicationRecy=findViewById(R.id.patient_MTP_indication_recy);
        missingRecy=findViewById(R.id.missing_recy);
        activity=this;

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext(),RecyclerView.VERTICAL,false);
        LinearLayoutManager linearLayoutManager2=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager3=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager4=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);
        LinearLayoutManager linearLayoutManager5=new LinearLayoutManager(getApplicationContext(),RecyclerView.HORIZONTAL,false);

        machineRecy.setLayoutManager(linearLayoutManager);
        doctorRecy.setLayoutManager(linearLayoutManager2);
        patientRecy.setLayoutManager(linearLayoutManager3);
        mtpIndicationRecy.setLayoutManager(linearLayoutManager4);

        missingRecy.setLayoutManager(linearLayoutManager5);
        showPatients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent1.putExtra("Type","Patients");
                intent1.putExtra("Name",Name);
                startActivity(intent1);
            }
        });
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference();

        databaseReference.child("Clinics").child(Name).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren())
                {
                    if(snapshot.getKey().contains("Name of Applicant"))
                    {
                        name.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("Address"))
                    {
                        address.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("Phone Number"))
                    {
                        if(snapshot.getValue().toString().contains(","))

                        phoneNumber.setText(snapshot.getValue().toString().replace(",","\n"));
                        else
                            phoneNumber.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("Date of Expiry"))
                    {
                        expiryDate.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("File Number"))
                    {
                        fileNumber.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("Additional Information")) {
                        findViewById(R.id.additionalframe).setVisibility(View.VISIBLE);

                        additionalInformation.setText(snapshot.getValue().toString());
                    }
                    else if (snapshot.getKey().contains("Details of Doctor Operating"))
                    {
                        String details=snapshot.getValue().toString();
                        recyclerActivityModels=new ArrayList<>();

                        if(details.contains(";"))
                        {
                            String[] doctors=details.split(";");
                            for(String doctor : doctors)
                            {
                                String primaryText=doctor.substring(0,doctor.indexOf(','));
                                recyclerActivityModels.add(new RecyclerActivityModel(primaryText,"","","Doctors"));
                            }
                        }
                        else
                        {
                            String primaryText=details.substring(0,details.indexOf(','));
                            recyclerActivityModels.add(new RecyclerActivityModel(primaryText,"","","Doctors"));

                        }
                        InsidePagesRecyclerAdapter recyclerActivityAdapter=new InsidePagesRecyclerAdapter(recyclerActivityModels,"Doctors",0,getApplicationContext(),activity);
                        doctorRecy.setAdapter(recyclerActivityAdapter);
                    }
                    else if (snapshot.getKey().contains("Machine"))
                    {
                        String details=snapshot.getValue().toString();
                        if(details.contains(";"))
                        {
                            String[] doctors=details.split(";");
                            for(String doctor : doctors)
                            {
                                Log.e("Crashes",doctor);
                                String primaryText=doctor.substring(0,doctor.indexOf(',',doctor.toLowerCase().lastIndexOf("odel")));
                                String secondaryText=snapshot.getKey().substring(0,snapshot.getKey().indexOf(" "));
                                machineModel.add(new RecyclerActivityModel(primaryText,secondaryText,"","Machines"));
                            }
                        }
                        else
                        {
                            String primaryText=details.substring(0,details.indexOf(',',details.toLowerCase().lastIndexOf("odel")));
                            String secondaryText=snapshot.getKey().substring(0,snapshot.getKey().indexOf(" "));
                            machineModel.add(new RecyclerActivityModel(primaryText,secondaryText,"" ,"Machines"));

                        }
                        InsidePagesRecyclerAdapter recyclerActivityAdapter=new InsidePagesRecyclerAdapter(machineModel,"Machines",0,getApplicationContext(),activity);
                        findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                        machineRecy.setAdapter(recyclerActivityAdapter);
                    }
                    else if (snapshot.getKey().contains("Missing Documents"))
                    {
                        recyclerActivityModels=new ArrayList<>();

                            String primaryText=snapshot.getValue().toString();
                            recyclerActivityModels.add(new RecyclerActivityModel(primaryText," ","","Missing"));


                        InsidePagesRecyclerAdapter recyclerActivityAdapter=new InsidePagesRecyclerAdapter(recyclerActivityModels,"Doctors",0,getApplicationContext(),activity);
                        missingRecy.setAdapter(recyclerActivityAdapter);
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

                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    String primaryText=dataSnapshot1.getKey();
                    String secondaryText = "";
                    String verificationData = null;
                    int flag=0;
                    for(DataSnapshot snapshot: dataSnapshot1.getChildren()) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            if (snapshot1.getKey().contains("Clinic") && Name.contains(snapshot1.getValue().toString())) {
                                flag = 100;
                            }
                            if (snapshot1.getKey().contains("Procedure")) {
                                secondaryText = secondaryText.concat(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().equals("LMP")) {
                                verificationData = snapshot1.getValue().toString();
                            }
                            if (snapshot1.getKey().equals("Date")) {
                                secondaryText = secondaryText.concat("("+ snapshot1.getValue().toString()+") ");
                            }
                            if (snapshot1.getKey().contains("MTP")) {
                                if (snapshot1.getValue().toString().contains("YES"))
                                    flag++;
                            }

                        }
                    }
                    if(flag==100)
                    {
                        patientModel.add(new RecyclerActivityModel(primaryText,secondaryText,verificationData,"Patients"));
                    }
                    else if(flag==101)
                    {
                        patientMTPModel.add(new RecyclerActivityModel(primaryText,secondaryText,verificationData,"Patients"));
                    }
                    InsidePagesRecyclerAdapter recyclerActivityAdapter=new InsidePagesRecyclerAdapter(patientModel,"Patients", 0,getApplicationContext(),activity);
                    patientRecy.setAdapter(recyclerActivityAdapter);
                    InsidePagesRecyclerAdapter recyclerActivityAdapter2=new InsidePagesRecyclerAdapter(patientMTPModel,"Patients", 0,getApplicationContext(),activity);
                    mtpIndicationRecy.setAdapter(recyclerActivityAdapter2);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
}
