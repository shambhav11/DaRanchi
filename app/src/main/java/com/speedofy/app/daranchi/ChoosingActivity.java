package com.speedofy.app.daranchi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ChoosingActivity extends AppCompatActivity {
    TextView textView,textView2,textview3,textview4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Button ClinicButton=findViewById(R.id.clinics);
        Button DoctorButton=findViewById(R.id.doctors);
        Button PatientsButton=findViewById(R.id.patients);
        Button AdminButton=findViewById(R.id.admin);

        textView = findViewById(R.id.district);
        textView2 = findViewById(R.id.cityname);
        textview3 = findViewById(R.id.welcome);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/akshar_unicode.ttf");
        textView.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textview3.setTypeface(typeface);
        textView2.setText("राँची, झारखंड");
        textView.setText("जिला प्रशासन");
        textview3.setText("स्वागत हे");

        ClinicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent.putExtra("Type","Clinics");
                startActivity(intent);
            }
        });
        DoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), RecyclerViewActivity.class);
                intent.putExtra("Type","Doctors");
                startActivity(intent);
            }
        });
        PatientsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), PagerStripActivity.class);
                intent.putExtra("Type","Patients");
                startActivity(intent);
            }
        });
        AdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), AdminLoginActivity.class);
                intent.putExtra("Type","Doctors");
                startActivity(intent);
            }
        });
    }
}
