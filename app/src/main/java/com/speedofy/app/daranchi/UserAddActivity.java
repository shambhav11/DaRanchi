package com.speedofy.app.daranchi;

import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserAddActivity extends AppCompatActivity {
    private static final String TAG ="USER ADD ACTIVITY" ;
    TextView textView,textView2;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_add);


        textView = findViewById(R.id.district);
        textView2 = findViewById(R.id.cityname);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/akshar_unicode.ttf");
        textView.setTypeface(typeface);
        textView2.setTypeface(typeface);
        textView2.setText("राँची, झारखंड");
        textView.setText("जिला प्रशासन");
        mAuth=FirebaseAuth.getInstance();
        final EditText email=findViewById(R.id.Email);
        final EditText pasword1=findViewById(R.id.password);


        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emaill=email.getText().toString();
                String password=pasword1.getText().toString();
                createUser(emaill,password);
            }
        });
    }

    private void createUser(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "User Created",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(getApplicationContext(), "User can't be created. Try Again",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });

    }

    private void updateUI(FirebaseUser user) {
        finish();
    }

}
