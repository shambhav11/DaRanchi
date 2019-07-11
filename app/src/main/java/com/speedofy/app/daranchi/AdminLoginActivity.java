package com.speedofy.app.daranchi;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class AdminLoginActivity extends AppCompatActivity {
    EditText username,password,oldPassword;
    Button login,changePass;
    TextView change;
    String userName,passWord;
    int flag;
    TextView textView,textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);

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
                    databaseReference.child("Login").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                if (snapshot.getKey().contains("Username")) {
                                    userName=snapshot.getValue().toString();
                                } else if (snapshot.getKey().contains("Password"))
                                    passWord=snapshot.getValue().toString();
                            }
                            if (userName.equals(username.getText().toString())&& passWord.equals(password.getText().toString())) {
                                Intent intent = new Intent(getApplicationContext(), AdminChoosingActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(),"Authentication Failed",Toast.LENGTH_SHORT).show();
                            }
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

            }
        });
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference();
                databaseReference1.child("Login").child("Password").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue().toString().contains(oldPassword.getText().toString())&& username.getText().toString().equals(password.getText().toString())) {
                            databaseReference1.child("Login").child("Password").setValue(username.getText().toString());
                            Toast.makeText(getApplicationContext(),"Password has been changed, Login Again",Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Password can't be changed, Make sure the right password is entered",Toast.LENGTH_SHORT).show();

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
