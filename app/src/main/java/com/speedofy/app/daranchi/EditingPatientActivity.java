package com.speedofy.app.daranchi;

import android.content.Intent;
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

import java.util.ArrayList;
import java.util.List;

public class EditingPatientActivity extends AppCompatActivity {
    private static final int REQUEST_FOR_DATA = 110;
    String data;
    String name;
    EditText patientName,patientDoctor,patientClinic,patientAge,patientContact,patientAddress,
    visitDate,child1,child2,child3,child4,child5,child6,child7,child8,child9,child10,MTPindication,LMPDate,LMPMonth,
    relation2,procedure,referred,indication;
    Button saveData,deleteVisit,deletePatient;
    String visitNumber,patientCount;
    List<EditText> children;
    int flag,flagLMP;
    int counter=0,counterVisit;
    int son,daughter,total;
    int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_patient);
        Intent intent = getIntent();
        son=0;daughter=0;total=0;
        patientName=findViewById(R.id.name_edittext);
        patientDoctor=findViewById(R.id.file_edittext);
        patientClinic=findViewById(R.id.clinic_edittext);
        indication=findViewById(R.id.indication_edittext);
        patientAge=findViewById(R.id.age_edittext);
        patientContact=findViewById(R.id.contact_edittext);
        patientAddress=findViewById(R.id.address_edittext);
        visitDate=findViewById(R.id.issue_edittext);
        MTPindication=findViewById(R.id.registration_edittext);
        LMPDate=findViewById(R.id.outcome_edittext);
        children=new ArrayList<>();
        flagLMP=0;
        LMPMonth=findViewById(R.id.missing_edittext);
        relation2=findViewById(R.id.relative_edittext);
        procedure=findViewById(R.id.procedure_edittext);
        referred=findViewById(R.id.referred_edittext);
        child1=findViewById(R.id.doctor_edittext1);
        child2=findViewById(R.id.doctor_edittext2);
        child3=findViewById(R.id.doctor_edittext3);
        child4=findViewById(R.id.doctor_edittext4);
        child5=findViewById(R.id.doctor_edittext5);
        child6=findViewById(R.id.doctor_edittext6);
        child7=findViewById(R.id.doctor_edittext7);
        child8=findViewById(R.id.doctor_edittext8);
        child9=findViewById(R.id.doctor_edittext9);
        child10=findViewById(R.id.doctor_edittext10);
        children.add(child1);children.add(child2);children.add(child3);children.add(child4);children.add(child5);children.add(child6);children.add(child7);children.add(child8);children.add(child9);children.add(child10);

        saveData=findViewById(R.id.save_data_button);
        deleteVisit=findViewById(R.id.delete_visit);
        deletePatient=findViewById(R.id.delete_patient);

        data = intent.getStringExtra("Type");

        if (data.contains("Edit")) {
            Intent intent1 = new Intent(getApplicationContext(), PagerStripActivity.class);
            if(data.contains("Clinic"))
            intent1.putExtra("Type", "PatientsYESnomonth");
            else
            {
                intent1.putExtra("Type","PatientsYES");
            }
            startActivityForResult(intent1, REQUEST_FOR_DATA);
        }


        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                findViewById(R.id.indeterminateBar).setVisibility(View.VISIBLE);
                final DatabaseReference databaseReferencepatient=databaseReference.child("Patients");
                databaseReferencepatient.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().contains(patientName.getText().toString())) {
                            counter=0;
                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                if(snapshot.getKey().contains(patientName.getText().toString()))
                                {
                                    counter++;
                                }
                            }

                            for (final DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                                flag=0;
                                if (snapshot1.getKey().contains(patientName.getText().toString())) {
                                    patientCount = snapshot1.getKey();
                                    counterVisit=0;
                                    for (DataSnapshot dataSnapshot1 : snapshot1.getChildren()) {
                                        visitNumber = dataSnapshot1.getKey();
                                        counterVisit++;
                                        Log.e("TAGGGG", visitNumber);

                                    }
                                    databaseReferencepatient.child(snapshot1.getKey()).child("Visit "+counterVisit).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                                if (dataSnapshot2.getKey().equals("LMP") && dataSnapshot2.getValue().toString().contains(LMPDate.getText().toString())) {
                                                    flag = 0;
                                                    visitNumber = visitNumber.substring(0, visitNumber.lastIndexOf(" ")) + " " + String.valueOf(Integer.parseInt(visitNumber.substring(visitNumber.lastIndexOf(" ") + 1)) + 1);
                                                    DatabaseReference databaseReference1 = databaseReferencepatient.child(snapshot1.getKey()).child(visitNumber);
                                                    databaseReference1.child("Address").setValue(patientAddress.getText().toString());
                                                    databaseReference1.child("Age").setValue(patientAge.getText().toString());
                                                    databaseReference1.child("Clinic Name").setValue(patientClinic.getText().toString());
                                                    databaseReference1.child("Contact Number").setValue(patientContact.getText().toString());
                                                    databaseReference1.child("Date").setValue(visitDate.getText().toString());
                                                    databaseReference1.child("Doctor Name").setValue(patientDoctor.getText().toString());
                                                    databaseReference1.child("Indication of MTP").setValue(MTPindication.getText().toString());
                                                    databaseReference1.child("LMP").setValue(LMPDate.getText().toString());
                                                    databaseReference1.child("LMP Month").setValue(LMPMonth.getText().toString());
                                                    databaseReference1.child("Indication of Diagnostic Procedure").setValue(indication.getText().toString());
                                                    databaseReference1.child("Name and Relation 2nd").setValue(relation2.getText().toString());
                                                    databaseReference1.child(procedure.getText().toString().substring(procedure.getText().toString().lastIndexOf("(") + 1, procedure.getText().toString().lastIndexOf(")")) + " Procedure").setValue(procedure.getText().toString().substring(0,procedure.getText().toString().indexOf("(")));
                                                    databaseReference1.child("Referred by").setValue(referred.getText().toString());

                                                    for (EditText editText : children) {
                                                        if (!editText.getText().toString().equals("")) {
                                                            total++;
                                                            String data = editText.getText().toString();
                                                            if (data.toLowerCase().contains("daughter")) {
                                                                daughter++;

                                                            } else {
                                                                son++;
                                                            }

                                                            databaseReference1.child("Age of " + data.substring(0, data.indexOf(":"))).setValue(data.substring(data.indexOf(":") + 1));

                                                        }

                                                    }
                                                    databaseReference1.child("Total Children").setValue(total);
                                                    databaseReference1.child("Son").setValue(son);
                                                    databaseReference1.child("Daughter").setValue(daughter);
                                                    findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                                                    Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    flagLMP=1;
                                                    break;

                                                } else if (dataSnapshot2.getKey().equals("LMP")) {
                                                    Log.e("TAGGG", "Flag 1 kra");

                                                    flag++;
                                                    Log.e("TAGGGG", String.valueOf(counter));
                                                    Log.e("TAGG","If condition" + flag);
                                                    if(flag==counter)
                                                    {
                                                        Log.e("TAGGGG","Mujhe LMP nhi mila");
                                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                                                        if(patientCount.contains("(")) {
                                                            String patientCount2 = patientCount.substring(0, patientCount.indexOf("(") + 1) + counter+ ")";

                                                            DatabaseReference databaseReference1 = databaseReference.child("Patients").child(patientCount2).child("Visit 1");
                                                            databaseReference1.child("Address").setValue(patientAddress.getText().toString());
                                                            databaseReference1.child("Age").setValue(patientAge.getText().toString());
                                                            databaseReference1.child("Clinic Name").setValue(patientClinic.getText().toString());
                                                            databaseReference1.child("Contact Number").setValue(patientContact.getText().toString());
                                                            databaseReference1.child("Date").setValue(visitDate.getText().toString());
                                                            databaseReference1.child("Doctor Name").setValue(patientDoctor.getText().toString());
                                                            databaseReference1.child("Indication of MTP").setValue(MTPindication.getText().toString());
                                                            databaseReference1.child("LMP").setValue(LMPDate.getText().toString());
                                                            databaseReference1.child("LMP Month").setValue(LMPMonth.getText().toString());
                                                            databaseReference1.child("Indication of Diagnostic Procedure").setValue(indication.getText().toString());
                                                            databaseReference1.child("Name and Relation 2nd").setValue(relation2.getText().toString());
                                                            databaseReference1.child(procedure.getText().toString().substring(procedure.getText().toString().lastIndexOf("(") + 1, procedure.getText().toString().lastIndexOf(")")) + " Procedure").setValue(procedure.getText().toString().substring(0,procedure.getText().toString().indexOf("(")));
                                                            databaseReference1.child("Referred by").setValue(referred.getText().toString());

                                                            for (EditText editText : children) {
                                                                if (!editText.getText().toString().equals("")) {
                                                                    total++;
                                                                    String data = editText.getText().toString();
                                                                    if (data.toLowerCase().contains("daughter")) {
                                                                        daughter++;

                                                                    } else {
                                                                        son++;
                                                                    }

                                                                    databaseReference1.child("Age of " + data.substring(0, data.indexOf(":"))).setValue(data.substring(data.indexOf(":") + 1));

                                                                }

                                                            }
                                                            databaseReference1.child("Total Children").setValue(total);
                                                            databaseReference1.child("Son").setValue(son);
                                                            databaseReference1.child("Daughter").setValue(daughter);
                                                            findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                                                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                        else {
                                                            databaseReference = FirebaseDatabase.getInstance().getReference();
                                                            String patientCount2 = patientCount+"(1)";

                                                            DatabaseReference databaseReference1 = databaseReference.child("Patients").child(patientCount2).child("Visit 1");
                                                            databaseReference1.child("Address").setValue(patientAddress.getText().toString());
                                                            databaseReference1.child("Age").setValue(patientAge.getText().toString());
                                                            databaseReference1.child("Clinic Name").setValue(patientClinic.getText().toString());
                                                            databaseReference1.child("Contact Number").setValue(patientContact.getText().toString());
                                                            databaseReference1.child("Date").setValue(visitDate.getText().toString());
                                                            databaseReference1.child("Doctor Name").setValue(patientDoctor.getText().toString());
                                                            databaseReference1.child("Indication of MTP").setValue(MTPindication.getText().toString());
                                                            databaseReference1.child("LMP").setValue(LMPDate.getText().toString());
                                                            databaseReference1.child("Indication of Diagnostic Procedure").setValue(indication.getText().toString());
                                                            databaseReference1.child("LMP Month").setValue(LMPMonth.getText().toString());
                                                            databaseReference1.child("Name and Relation 2nd").setValue(relation2.getText().toString());
                                                            databaseReference1.child(procedure.getText().toString().substring(procedure.getText().toString().lastIndexOf("(") + 1, procedure.getText().toString().lastIndexOf(")")) + " Procedure").setValue(procedure.getText().toString().substring(0,procedure.getText().toString().indexOf("(")));
                                                            databaseReference1.child("Referred by").setValue(referred.getText().toString());

                                                            for (EditText editText : children) {
                                                                if (!editText.getText().toString().equals("")) {
                                                                    total++;
                                                                    String data = editText.getText().toString();
                                                                    if (data.toLowerCase().contains("daughter")) {
                                                                        daughter++;

                                                                    } else {
                                                                        son++;
                                                                    }

                                                                    databaseReference1.child("Age of " + data.substring(0, data.indexOf(":"))).setValue(data.substring(data.indexOf(":") + 1));

                                                                }

                                                            }
                                                            databaseReference1.child("Total Children").setValue(total);
                                                            databaseReference1.child("Son").setValue(son);
                                                            databaseReference1.child("Daughter").setValue(daughter);
                                                            findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
                                                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
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
//                                if(flagLMP==1)
//                                break;






                            }


                        }
                        else
                        {
                           DatabaseReference  databaseReference = FirebaseDatabase.getInstance().getReference();
                            String patientCount2 = patientName.getText().toString();

                            DatabaseReference databaseReference1 = databaseReference.child("Patients").child(patientCount2).child("Visit 1");
                            databaseReference1.child("Address").setValue(patientAddress.getText().toString());
                            databaseReference1.child("Age").setValue(patientAge.getText().toString());
                            databaseReference1.child("Clinic Name").setValue(patientClinic.getText().toString());
                            databaseReference1.child("Contact Number").setValue(patientContact.getText().toString());
                            databaseReference1.child("Date").setValue(visitDate.getText().toString());
                            databaseReference1.child("Doctor Name").setValue(patientDoctor.getText().toString());
                            databaseReference1.child("Indication of MTP").setValue(MTPindication.getText().toString());
                            databaseReference1.child("LMP").setValue(LMPDate.getText().toString());
                            databaseReference1.child("Indication of Diagnostic Procedure").setValue(indication.getText().toString());
                            databaseReference1.child("LMP Month").setValue(LMPMonth.getText().toString());
                            databaseReference1.child("Name and Relation 2nd").setValue(relation2.getText().toString());
                            databaseReference1.child(procedure.getText().toString().substring(procedure.getText().toString().lastIndexOf("(") + 1, procedure.getText().toString().lastIndexOf(")")) + " Procedure").setValue(procedure.getText().toString().substring(0,procedure.getText().toString().indexOf("(")));
                            databaseReference1.child("Referred by").setValue(referred.getText().toString());

                            for (EditText editText : children) {
                                if (!editText.getText().toString().equals("")) {
                                    total++;
                                    String data = editText.getText().toString();
                                    if (data.toLowerCase().contains("daughter")) {
                                        daughter++;

                                    } else {
                                        son++;
                                    }

                                    databaseReference1.child("Age of " + data.substring(0, data.indexOf(":"))).setValue(data.substring(data.indexOf(":") + 1));

                                }

                            }
                            databaseReference1.child("Total Children").setValue(total);
                            databaseReference1.child("Son").setValue(son);
                            databaseReference1.child("Daughter").setValue(daughter);
                            findViewById(R.id.indeterminateBar).setVisibility(View.GONE);
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


        deleteVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                counterVisit=0;
                databaseReference.child("Patients").child(patientName.getText().toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot snapshot : dataSnapshot.getChildren())
                        {
                            counterVisit++;
                        }
                        databaseReference.child("Patients").child(patientName.getText().toString()).child("Visit "+counterVisit).removeValue();
                        Toast.makeText(getApplicationContext(),"Visit Data Deleted",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        deletePatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Patients").child(patientName.getText().toString()).removeValue();
                Toast.makeText(getApplicationContext(),"Patient Deleted",Toast.LENGTH_SHORT).show();
                finish();
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
            DatabaseReference databaseReference = database.getReference();
            if (data != null) {
                name = data.getStringExtra("Name");
            }
            patientName.setText(name);
            databaseReference.child("Patients").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot11 : dataSnapshot.getChildren()) {
                        count=0;
                        for (DataSnapshot snapshot1 : snapshot11.getChildren()) {
                            if (snapshot1.getKey().contains("Doctor Name")) {
                                patientDoctor.setText(snapshot1.getValue().toString());
                            }

                            if (snapshot1.getKey().contains("Clinic Name")) {
                                patientClinic.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().equals("Age")) {
                                patientAge.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().equals("Indication of Diagnostic Procedure")) {
                                indication.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().contains("Contact Number")) {
                                patientContact.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().contains("Address")) {
                                patientAddress.setText(snapshot1.getValue().toString());
                            }

                            if (snapshot1.getKey().contains("MTP")) {
                                MTPindication.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().equals("LMP")) {
                                LMPDate.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().contains("LMP Month")) {
                                LMPMonth.setText(snapshot1.getValue().toString());
                            }
                            if (snapshot1.getKey().contains("Date")) {
                                visitDate.setText(snapshot1.getValue().toString());
                            }

                            if (snapshot1.getKey().contains("Name and Relation")) {
                                relation2.setText(snapshot1.getValue().toString());
                            }

                            if (snapshot1.getKey().contains("Procedure")) {
                                procedure.setText(snapshot1.getValue().toString() + "("+snapshot1.getKey().substring(0,snapshot1.getKey().indexOf("Procedure"))+")");
                            }

                            if (snapshot1.getKey().contains("Referred by")) {
                                referred.setText(snapshot1.getValue().toString());
                            }

                            if (snapshot1.getKey().contains("Referred by")) {
                                referred.setText(snapshot1.getValue().toString());
                            }
                            if(snapshot1.getKey().contains("Age of"))
                            {
                                children.get(count).setText(snapshot1.getKey().substring(6)+" : "+snapshot1.getValue().toString());
                                count++;
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
