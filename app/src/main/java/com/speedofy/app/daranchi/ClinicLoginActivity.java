package com.speedofy.app.daranchi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ClinicLoginActivity extends AppCompatActivity {

    private static final int REQUEST_FOR_DATA = 120;
    String name;
    EditText username,password,oldPassword;
    Button login,changePass;
    TextView textView,textView2;
    TextView change;
    int flag;
    String userName,passWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic_login);
        Intent intent1=new Intent(getApplicationContext(),RecyclerViewActivity.class);
        intent1.putExtra("Type","ClinicsYES");
        startActivityForResult(intent1,REQUEST_FOR_DATA);

        username=findViewById(R.id.Email);
        password=findViewById(R.id.password);
        oldPassword=findViewById(R.id.passwordOld);
        login=findViewById(R.id.btn_login);
        change=findViewById(R.id.change_pass);
        changePass=findViewById(R.id.btn_change);

        textView = findViewById(R.id.district);
        textView2 = findViewById(R.id.cityname);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/akshar_unicode.ttf");
        textView.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView2.setText("राँची, झारखंड");
        textView.setText("जिला प्रशासन");





        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference databaseReference=firebaseDatabase.getReference();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = 0;
                databaseReference.child("Clinics").child(username.getText().toString()).child("Password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().equals(password.getText().toString()))
                        {
                            Intent intent=new Intent(getApplicationContext(),ClinicAdminPanel.class);
                            startActivity(intent);
                        }
                        else
                        Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }

        });


        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldPassword.setVisibility(View.VISIBLE);
                oldPassword.setHint("Old Password");
                username.setHint("New Password");
                username.setInputType(password.getInputType());
                password.setHint("Confirm New Password");
                changePass.setVisibility(View.VISIBLE);
                login.setVisibility(View.GONE);
                name=username.getText().toString();
                username.setText("");

            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference();



                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplication(), "Enter Your Clinic Name", Toast.LENGTH_SHORT).show();
                    finish();
                }

                databaseReference1.child("Clinics").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().contains(name)) {
                            final DatabaseReference databaseReference2=databaseReference1.child("Clinics").child(name).child("Password");
                            databaseReference2.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.getValue().toString().contains(oldPassword.getText().toString()) && username.getText().toString().equals(password.getText().toString())) {
                                        databaseReference2.setValue(username.getText().toString());
                                        Toast.makeText(getApplicationContext(), "Password has been changed, Login Again", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Password can't be changed, Make sure the right password is entered", Toast.LENGTH_SHORT).show();

                                    }

                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(getApplication(), "Clinic not found in Database. Ask Admin to add your clinic or correct your name.", Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // Check if image is captured successfully
        if (resultCode == REQUEST_FOR_DATA) {
            super.onActivityResult(requestCode, resultCode, data);
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            if (data != null) {
                name = data.getStringExtra("Name");
                username.setText(name);
            }

        }

    }

}

