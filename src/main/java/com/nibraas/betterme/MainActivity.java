package com.nibraas.betterme;

import android.accounts.Account;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    TextView email, password, regist;
    Button SignIn;
    String myEmail, myPassword;
    FirebaseAuth myAuth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(user != null){
            Intent i = new Intent(MainActivity.this, HomePageActivity.class);
            startActivity(i);
        }
        email = findViewById(R.id.etEmail);
        SignIn = findViewById(R.id.signIn);
        password = findViewById(R.id.etPassword);
        regist = findViewById(R.id.regist);
        myAuth = FirebaseAuth.getInstance();



        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEmail = email.getText().toString();
                myPassword = password.getText().toString();

                if (myEmail.isEmpty()) {
                    email.setError("Please enter your email");
                    email.requestFocus();
                }
                else if(myPassword.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }else{
                    userLogin();
                }
            }
        });

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });
    }

    private void userLogin(){
        myAuth.signInWithEmailAndPassword(myEmail, myPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, HomePageActivity.class));
                }else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
