package com.nibraas.betterme;

import android.content.Intent;
import android.preference.PreferenceActivity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;


public class RegisterActivity extends AppCompatActivity {

    TextView email , password, c_password;
    Button regist;
    FirebaseAuth myAuth;
    String myEmail, myPassword, myC_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        email = findViewById(R.id.etEmail);
        password = findViewById(R.id.etPassword);
        c_password = findViewById(R.id.etC_password);
        regist = findViewById(R.id.registbtn);

        myAuth = FirebaseAuth.getInstance();

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myEmail = email.getText().toString().trim();
                myPassword = password.getText().toString().trim();
                myC_Password = c_password.getText().toString().trim();

                if(myEmail.isEmpty()){
                    email.setError("Please enter your email");
                    email.requestFocus();
                }
                else if(!Patterns.EMAIL_ADDRESS.matcher(myEmail).matches()){
                    email.setError("Please enter a valid email");
                    email.requestFocus();
                }
                else if(myPassword.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(myC_Password.isEmpty()){
                    c_password.setError("Please enter your password again");
                    c_password.requestFocus();
                }
                else if(!myPassword.equals(myC_Password)){
                    c_password.setError("Passwords need to match");
                    c_password.requestFocus();
                }
                else {
                    CreateNewUser();
                }
            }
        });
    }

    private void CreateNewUser (){
        myAuth.createUserWithEmailAndPassword(myEmail,myPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent i = new Intent(RegisterActivity.this, com.nibraas.betterme.PreferenceActivity.class);
                            startActivity(i);
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                email.setError("Email is already registered");
                                email.requestFocus();
                            } else if (task.getException() instanceof FirebaseAuthWeakPasswordException) {
                                password.setError("Please enter a longer password");
                                password.requestFocus();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
}