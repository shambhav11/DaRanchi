package com.speedofy.app.daranchi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ClinicAdminPanel extends AppCompatActivity {
    Button addDoctor,editDoctor,addPatient,editPatient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_admin_panel);
        addDoctor=findViewById(R.id.doctor);
        editDoctor=findViewById(R.id.edit_doctor);
        addPatient=findViewById(R.id.patient);
        editPatient=findViewById(R.id.edit_patient);


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
                intent.putExtra("Type","EditClinic");
                startActivity(intent);

            }
        });


    }
}
