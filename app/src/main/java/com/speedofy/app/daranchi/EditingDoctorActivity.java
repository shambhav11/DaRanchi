package com.speedofy.app.daranchi;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditingDoctorActivity extends AppCompatActivity {
    private static final int REQUEST_FOR_DATA = 105;
    EditText doctorName, doctorClinic, doctorContact;
    Button saveButton,deleteButton;
    String data,name;
    String patientCount;
    int counter,flag;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_doctor);
        Intent intent = getIntent();

        data = intent.getStringExtra("Type");
        doctorName=findViewById(R.id.name_edittext);
        doctorClinic=findViewById(R.id.clinic_edittext);
        doctorContact=findViewById(R.id.contact_edittext);
        saveButton=findViewById(R.id.save_data_button);
        deleteButton=findViewById(R.id.delete_doctor);


        final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                final DatabaseReference databaseReferencepatient=databaseReference.child("Doctors");
                databaseReferencepatient.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().contains(doctorName.getText().toString())) {
                            counter = 0;
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (snapshot.getKey().contains(doctorName.getText().toString())) {
                                    counter++;
                                }
                            }

                            for (final DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                                flag = 0;
                                if (snapshot1.getKey().contains(doctorName.getText().toString())) {
                                    patientCount = snapshot1.getKey();
                                    databaseReferencepatient.child(snapshot1.getKey().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                                if (dataSnapshot2.getKey().equals("Contact Number") && dataSnapshot2.getValue().toString().contains(doctorContact.getText().toString())) {
                                                    flag = 0;
                                                    DatabaseReference databaseReference1 = databaseReferencepatient.child(snapshot1.getKey().toString());
                                                    databaseReference1.child("Contact Number").setValue(doctorContact.getText().toString());
                                                    databaseReference1.child("Present Clinic").setValue(doctorClinic.getText().toString());
                                                    Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                                                    finish();

                                                    break;

                                                } else if (dataSnapshot2.getKey().equals("Contact Number")) {
                                                    Log.e("TAGGG", "Flag 1 kra");

                                                    flag++;
                                                    Log.e("TAGGGG", String.valueOf(counter));
                                                    Log.e("TAGG", "If condition" + flag);
                                                    if (flag == counter) {
                                                        Log.e("TAGGGG", "Mujhe LMP nhi mila");
                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                        if (patientCount.contains("(")) {
                                                            String patientCount2 = patientCount.substring(0, patientCount.indexOf("(") + 1) + counter + ")";

                                                            DatabaseReference databaseReference1 = databaseReference.child("Doctors").child(patientCount2);
                                                            databaseReference1.child("Contact Number").setValue(doctorContact.getText().toString());
                                                            databaseReference1.child("Present Clinic").setValue(doctorClinic.getText().toString());

                                                            Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        } else {
                                                            databaseReference = FirebaseDatabase.getInstance().getReference();
                                                            String patientCount2 = patientCount + "(1)";


                                                            DatabaseReference databaseReference1 = databaseReferencepatient.child(patientCount2);
                                                            databaseReference1.child("Contact Number").setValue(doctorContact.getText().toString());
                                                            databaseReference1.child("Present Clinic").setValue(doctorClinic.getText().toString());

                                                            Toast.makeText(getApplicationContext(), "Data Saved", Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }

                                                    }
                                                }
                                            }


                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError databaseError) {

                                        }
                                    });

                                }
                            }
                        }

//                                if(flagLMP==1)
//                                break;










                        else
                        {
                            DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference();
                            String patientCount2 = doctorName.getText().toString();

                            DatabaseReference databaseReference1 = databaseReference.child("Doctors").child(patientCount2);
                            databaseReference1.child("Contact Number").setValue(doctorContact.getText().toString());
                            databaseReference1.child("Present Clinic").setValue(doctorClinic.getText().toString());

                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("Doctors").child(doctorName.getText().toString()).removeValue();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_SHORT).show();

                Intent intent1=new Intent(getApplicationContext(),AdminChoosingActivity.class);
                startActivity(intent1);
            }
        });




        if (data.contains("Edit")) {
            Intent intent1 = new Intent(getApplicationContext(), RecyclerViewActivity.class);
            intent1.putExtra("Type", "DoctorsYES");
            startActivityForResult(intent1, REQUEST_FOR_DATA);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // Check if image is captured successfully
        if(resultCode==REQUEST_FOR_DATA) {
            super.onActivityResult(requestCode, resultCode, data);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference();
            if (data != null) {
                name = data.getStringExtra("Name");
            }
            doctorName.setText(name);
            databaseReference.child("Doctors").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot1 : dataSnapshot.getChildren()) {
                        if (snapshot1.getKey().contains("Contact Number")) {
                            doctorContact.setText(snapshot1.getValue().toString());
                        }
                        if (snapshot1.getKey().contains("Present Clinic")) {
                            doctorClinic.setText(snapshot1.getValue().toString());
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}

