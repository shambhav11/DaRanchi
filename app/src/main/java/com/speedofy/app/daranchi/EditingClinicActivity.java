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

public class EditingClinicActivity extends AppCompatActivity {
    EditText clinicName,applicantName,address,contactNumber,expiryDate,issueDate,doctor1,
            doctor2,doctor3,doctor4,doctor5,doctor6,doctor7,doctor8,doctor9,doctor10,
    fileNumber,registrationNumber,missingDocuments,old1,old2,old3,old4,old5,old6,old7,old8,
            old9,old10,outcome,present1,present2,present3,present4,present5,present6,present7,
            present8,present9,present10;
    String data;
    String name;
    List<EditText> doctorEdittext,oldEdittexts,newEdittexts;
    Button saveData,deleteData;
    int counter,flag;
    String patientCount;
    public static final int REQUEST_FOR_DATA=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_clinic);
        clinicName=findViewById(R.id.name_edittext);
        applicantName=findViewById(R.id.applicant_edittext);
        address=findViewById(R.id.address_edittext);
        contactNumber=findViewById(R.id.contact_edittext);
        expiryDate=findViewById(R.id.expiry_edittext);
        issueDate=findViewById(R.id.issue_edittext);
        doctor1=findViewById(R.id.doctor_edittext1);
        doctor2=findViewById(R.id.doctor_edittext2);
        doctor3=findViewById(R.id.doctor_edittext3);
        doctor4=findViewById(R.id.doctor_edittext4);
        doctor5=findViewById(R.id.doctor_edittext5);
        doctor6=findViewById(R.id.doctor_edittext6);
        doctor7=findViewById(R.id.doctor_edittext7);
        doctor8=findViewById(R.id.doctor_edittext8);
        doctor9=findViewById(R.id.doctor_edittext9);
        doctor10=findViewById(R.id.doctor_edittext10);
        fileNumber=findViewById(R.id.file_edittext);
        missingDocuments=findViewById(R.id.missing_edittext);
        outcome=findViewById(R.id.outcome_edittext);
        registrationNumber=findViewById(R.id.registration_edittext);
        old1=findViewById(R.id.old_edittext1);
        old2=findViewById(R.id.old_edittext2);
        old3=findViewById(R.id.old_edittext3);
        old4=findViewById(R.id.old_edittext4);
        old5=findViewById(R.id.old_edittext5);
        old6=findViewById(R.id.old_edittext6);
        old7=findViewById(R.id.old_edittext7);
        old8=findViewById(R.id.old_edittext8);
        old9=findViewById(R.id.old_edittext9);
        old10=findViewById(R.id.old_edittext10);

        doctorEdittext=new ArrayList<>();
        oldEdittexts=new ArrayList<>();
        newEdittexts=new ArrayList<>();
        oldEdittexts.add(old1);
        oldEdittexts.add(old2);
        oldEdittexts.add(old3);
        oldEdittexts.add(old4);
        oldEdittexts.add(old5);
        oldEdittexts.add(old6);
        oldEdittexts.add(old7);
        oldEdittexts.add(old8);
        oldEdittexts.add(old9);
        oldEdittexts.add(old10);



        doctorEdittext.add(doctor1);
        doctorEdittext.add(doctor2);
        doctorEdittext.add(doctor3);
        doctorEdittext.add(doctor4);
        doctorEdittext.add(doctor5);
        doctorEdittext.add(doctor6);
        doctorEdittext.add(doctor7);
        doctorEdittext.add(doctor8);
        doctorEdittext.add(doctor9);
        doctorEdittext.add(doctor10);
        present1=findViewById(R.id.present_edittext1);
        present2=findViewById(R.id.present_edittext2);
        present3=findViewById(R.id.present_edittext3);
        present4=findViewById(R.id.present_edittext4);
        present5=findViewById(R.id.present_edittext5);
        present6=findViewById(R.id.present_edittext6);
        present7=findViewById(R.id.present_edittext7);
        present8=findViewById(R.id.present_edittext8);
        present9=findViewById(R.id.present_edittext9);
        present10=findViewById(R.id.present_edittext10);
        newEdittexts.add(present1);
        newEdittexts.add(present2);
        newEdittexts.add(present3);
        newEdittexts.add(present4);
        newEdittexts.add(present5);
        newEdittexts.add(present6);
        newEdittexts.add(present7);
        newEdittexts.add(present8);
        newEdittexts.add(present9);
        newEdittexts.add(present10);
        saveData=findViewById(R.id.save_data_button);
        deleteData=findViewById(R.id.delete_clinic);


        Intent intent=getIntent();
        data=intent.getStringExtra("Type");


        saveData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();


                final DatabaseReference databaseReferencepatient=databaseReference.child("Clinics");
                databaseReferencepatient.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.getValue().toString().contains(clinicName.getText().toString())) {
                            counter=0;
                            for(DataSnapshot snapshot : dataSnapshot.getChildren())
                            {
                                if(snapshot.getKey().contains(clinicName.getText().toString()))
                                {
                                    counter++;
                                }
                            }

                            for (final DataSnapshot snapshot1 : dataSnapshot.getChildren()) {

                                flag=0;
                                if (snapshot1.getKey().contains(clinicName.getText().toString())) {
                                    patientCount = snapshot1.getKey();
                                    databaseReferencepatient.child(snapshot1.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                            for (DataSnapshot dataSnapshot2 : dataSnapshot.getChildren()) {
                                                if (dataSnapshot2.getKey().equals("Address") && dataSnapshot2.getValue().toString().contains(address.getText().toString())) {
                                                    flag = 0;
                                                    DatabaseReference reference=databaseReference.child("Clinics").child(clinicName.getText().toString());
                                                    reference.child("Name of Clinic").setValue(clinicName.getText().toString());
                                                    reference.child("Name of Applicant").setValue(applicantName.getText().toString());
                                                    reference.child("Address").setValue(address.getText().toString());
                                                    reference.child("Date of Expiry").setValue(expiryDate.getText().toString());
                                                    reference.child("Date of Issue").setValue(issueDate.getText().toString());
                                                    reference.child("File Number").setValue(fileNumber.getText().toString());
                                                    reference.child("Outcome of Application").setValue(outcome.getText().toString());
                                                    reference.child("Phone Number").setValue(contactNumber.getText().toString());
                                                    if(data.contains("Add"))
                                                    {
                                                        reference.child("Password").setValue("1234567890");
                                                    }
                                                    reference.child("Registration Number").setValue(registrationNumber.getText().toString());
                                                    String doctors="",oldMachines="",newMachines="";
                                                    for(EditText editText : doctorEdittext)
                                                    {

                                                        if(!editText.getText().toString().equals(""))
                                                        {
                                                            doctors= doctors.concat(editText.getText().toString()+";");
                                                            reference.child("Details of Doctor Operating").setValue(doctors);
                                                        }

                                                    }
                                                    for(EditText editText : oldEdittexts)
                                                    {

                                                        if(!editText.getText().toString().equals(""))
                                                        {
                                                            oldMachines=oldMachines.concat(editText.getText().toString()+", ;");
                                                            reference.child("Old Machines").setValue(oldMachines);
                                                        }

                                                    }
                                                    for(EditText editText : newEdittexts)
                                                    {

                                                        if(!editText.getText().toString().equals(""))
                                                        {
                                                            newMachines= newMachines.concat(editText.getText().toString()+";");
                                                            reference.child("Present Machines").setValue(newMachines);
                                                        }

                                                    }
                                                    Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();

                                                    Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                                                    finish();
                                                    break;

                                                } else if (dataSnapshot2.getKey().equals("Address")) {
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

                                                            DatabaseReference reference=databaseReference.child("Clinics").child(patientCount2);
                                                            reference.child("Name of Clinic").setValue(clinicName.getText().toString());
                                                            reference.child("Name of Applicant").setValue(applicantName.getText().toString());
                                                            reference.child("Address").setValue(address.getText().toString());
                                                            reference.child("Date of Expiry").setValue(expiryDate.getText().toString());
                                                            reference.child("Date of Issue").setValue(issueDate.getText().toString());
                                                            reference.child("File Number").setValue(fileNumber.getText().toString());
                                                            reference.child("Outcome of Application").setValue(outcome.getText().toString());
                                                            reference.child("Phone Number").setValue(contactNumber.getText().toString());
                                                            if(data.contains("Add"))
                                                            {
                                                                reference.child("Password").setValue("1234567890");
                                                            }
                                                            reference.child("Registration Number").setValue(registrationNumber.getText().toString());
                                                            String doctors="",oldMachines="",newMachines="";
                                                            for(EditText editText : doctorEdittext)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    doctors= doctors.concat(editText.getText().toString()+";");
                                                                    reference.child("Details of Doctor Operating").setValue(doctors);
                                                                }

                                                            }
                                                            for(EditText editText : oldEdittexts)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    oldMachines=oldMachines.concat(editText.getText().toString()+", ;");
                                                                    reference.child("Old Machines").setValue(oldMachines);
                                                                }

                                                            }
                                                            for(EditText editText : newEdittexts)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    newMachines= newMachines.concat(editText.getText().toString()+";");
                                                                    reference.child("Present Machines").setValue(newMachines);
                                                                }

                                                            }
                                                            Toast.makeText(getApplicationContext(),"Data Saved",Toast.LENGTH_SHORT).show();
                                                            finish();
                                                        }
                                                        else {
                                                            databaseReference = FirebaseDatabase.getInstance().getReference();
                                                            String patientCount2 = patientCount+"(1)";

                                                            DatabaseReference reference=databaseReference.child("Clinics").child(patientCount2);
                                                            reference.child("Name of Clinic").setValue(clinicName.getText().toString());
                                                            reference.child("Name of Applicant").setValue(applicantName.getText().toString());
                                                            reference.child("Address").setValue(address.getText().toString());
                                                            reference.child("Date of Expiry").setValue(expiryDate.getText().toString());
                                                            reference.child("Date of Issue").setValue(issueDate.getText().toString());
                                                            reference.child("File Number").setValue(fileNumber.getText().toString());
                                                            reference.child("Outcome of Application").setValue(outcome.getText().toString());
                                                            reference.child("Phone Number").setValue(contactNumber.getText().toString());
                                                            if(data.contains("Add"))
                                                            {
                                                                reference.child("Password").setValue("1234567890");
                                                            }
                                                            reference.child("Registration Number").setValue(registrationNumber.getText().toString());
                                                            String doctors="",oldMachines="",newMachines="";
                                                            for(EditText editText : doctorEdittext)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    doctors= doctors.concat(editText.getText().toString()+";");
                                                                    reference.child("Details of Doctor Operating").setValue(doctors);
                                                                }

                                                            }
                                                            for(EditText editText : oldEdittexts)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    oldMachines=oldMachines.concat(editText.getText().toString()+", ;");
                                                                    reference.child("Old Machines").setValue(oldMachines);
                                                                }

                                                            }
                                                            for(EditText editText : newEdittexts)
                                                            {

                                                                if(!editText.getText().toString().equals(""))
                                                                {
                                                                    newMachines= newMachines.concat(editText.getText().toString()+";");
                                                                    reference.child("Present Machines").setValue(newMachines);
                                                                }

                                                            }
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
                            String patientCount2 = clinicName.getText().toString();

                            DatabaseReference reference=databaseReference.child("Clinics").child(patientCount2);
                            reference.child("Name of Clinic").setValue(clinicName.getText().toString());
                            reference.child("Name of Applicant").setValue(applicantName.getText().toString());
                            reference.child("Address").setValue(address.getText().toString());
                            reference.child("Date of Expiry").setValue(expiryDate.getText().toString());
                            reference.child("Date of Issue").setValue(issueDate.getText().toString());
                            reference.child("File Number").setValue(fileNumber.getText().toString());
                            reference.child("Outcome of Application").setValue(outcome.getText().toString());
                            reference.child("Phone Number").setValue(contactNumber.getText().toString());
                            if(data.contains("Add"))
                            {
                                reference.child("Password").setValue("1234567890");
                            }
                            reference.child("Registration Number").setValue(registrationNumber.getText().toString());
                            String doctors="",oldMachines="",newMachines="";
                            for(EditText editText : doctorEdittext)
                            {

                                if(!editText.getText().toString().equals(""))
                                {
                                    doctors= doctors.concat(editText.getText().toString()+";");
                                    reference.child("Details of Doctor Operating").setValue(doctors);
                                }

                            }
                            for(EditText editText : oldEdittexts)
                            {

                                if(!editText.getText().toString().equals(""))
                                {
                                    oldMachines=oldMachines.concat(editText.getText().toString()+", ;");
                                    reference.child("Old Machines").setValue(oldMachines);
                                }

                            }
                            for(EditText editText : newEdittexts)
                            {

                                if(!editText.getText().toString().equals(""))
                                {
                                    newMachines= newMachines.concat(editText.getText().toString()+";");
                                    reference.child("Present Machines").setValue(newMachines);
                                }

                            }

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



        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.child("Clinics").child(clinicName.getText().toString()).removeValue();
                Toast.makeText(getApplicationContext(),"Data Deleted",Toast.LENGTH_SHORT).show();

                Intent intent1=new Intent(getApplicationContext(),AdminChoosingActivity.class);
                startActivity(intent1);
            }
        });


        if(data.contains("Edit"))
        {
            Intent intent1=new Intent(getApplicationContext(),RecyclerViewActivity.class);
            intent1.putExtra("Type","ClinicsYES");
            startActivityForResult(intent1,REQUEST_FOR_DATA);
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
            databaseReference.child("Clinics").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        if (dataSnapshot1.getKey().contains("Name of Clinic")) {
                            clinicName.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Name of Applicant")) {
                            applicantName.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Phone Number")) {
                            contactNumber.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Address")) {
                            address.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Date of Expiry")) {
                            expiryDate.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Date of Issue")) {
                            issueDate.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Details of Doctor Operating")) {
                            String dummy = dataSnapshot1.getValue().toString();
                            String[] machine = dummy.split(";");
                            for (int i = 0; i < machine.length; i++) {
                                doctorEdittext.get(i).setText(machine[i]);
                            }

                        }
                        if (dataSnapshot1.getKey().contains("File Number")) {
                            fileNumber.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Registration Number")) {
                            registrationNumber.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Outcome")) {
                            outcome.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Missing Documents")) {
                            missingDocuments.setText(dataSnapshot1.getValue().toString());
                        }
                        if (dataSnapshot1.getKey().contains("Old Machines")) {
                            String dummy = dataSnapshot1.getValue().toString();
                            String[] machine = dummy.split(";");
                            for (int i = 0; i < machine.length; i++) {
                                oldEdittexts.get(i).setText(machine[i]);
                            }
                        }
                        if (dataSnapshot1.getKey().contains("Present Machine")) {
                            String dummy = dataSnapshot1.getValue().toString();
                            String[] machine = dummy.split(";");
                            for (int i = 0; i < machine.length; i++) {
                                newEdittexts.get(i).setText(machine[i]);
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
