package com.speedofy.app.daranchi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminChoosingActivity extends AppCompatActivity {
    Button addClinic,editClinic,addDoctor,editDoctor,addPatient,editPatient,addUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_choosing);
        addClinic=findViewById(R.id.clinics);
        editClinic=findViewById(R.id.edit_clinics);
        addDoctor=findViewById(R.id.doctor);
        editDoctor=findViewById(R.id.edit_doctor);
        addPatient=findViewById(R.id.patient);
        editPatient=findViewById(R.id.edit_patient);
        addUser=findViewById(R.id.add_user);

        addClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingClinicActivity.class);
                intent.putExtra("Type","Add");
                startActivity(intent);

            }
        });
        editClinic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingClinicActivity.class);
                intent.putExtra("Type","Edit");
                startActivity(intent);

            }
        });


        addDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingDoctorActivity.class);
                intent.putExtra("Type","Add");
                startActivity(intent);

            }
        });
        editDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingDoctorActivity.class);
                intent.putExtra("Type","Edit");
                startActivity(intent);

            }
        });
        addPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingPatientActivity.class);
                intent.putExtra("Type","Add");
                startActivity(intent);

            }
        });
        editPatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),EditingPatientActivity.class);
                intent.putExtra("Type","Edit");
                startActivity(intent);

            }
        });
        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),UserAddActivity.class);
                startActivity(intent);
            }
        });
    }
}
