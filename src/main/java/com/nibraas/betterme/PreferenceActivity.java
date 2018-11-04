package com.nibraas.betterme;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PreferenceActivity extends AppCompatActivity {

    TextView next, social_text, intellect_text, exp_text, health_text;
    ImageView social, intellect, exp, health;
    int b1 = 0, b2 = 0, b3 = 0, b4 = 0;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    FirebaseUser user;
    DatabaseReference myRef;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preference);

        next = findViewById(R.id.tohomepage);

        progressBar = findViewById(R.id.progressBar1);

        social = findViewById(R.id.socialbtn);
        social_text = findViewById(R.id.textView11);

        intellect = findViewById(R.id.intellectbtn);
        intellect_text = findViewById(R.id.textView12);

        exp = findViewById(R.id.expbtn);
        exp_text = findViewById(R.id.textView13);

        health = findViewById(R.id.healthbtn);
        health_text = findViewById(R.id.textView10);

        social.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(b1 % 2 == 0) {
                    social_text.setTextColor(Color.BLUE);
                    social.setSelected(true);
                }
                else{
                    social_text.setTextColor(Color.WHITE);
                    social.setSelected(false);
                }
                b1 += 1;
            }
        });

        intellect.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(b2 % 2 == 0) {
                    intellect_text.setTextColor(Color.BLUE);
                    intellect.setSelected(true);
                }
                else{
                    intellect_text.setTextColor(Color.WHITE);
                    intellect.setSelected(false);
                }
                b2 += 1;
            }
        });

        exp.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(b3 % 2 == 0) {
                    exp_text.setTextColor(Color.BLUE);
                    exp.setSelected(true);
                }
                else{
                    exp_text.setTextColor(Color.WHITE);
                    exp.setSelected(false);
                }
                b3 += 1;
            }
        });

        health.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onClick(View view) {
                if(b4 % 2 == 0) {
                    health_text.setTextColor(Color.BLUE);
                    health.setSelected(true);
                }
                else{
                    health_text.setTextColor(Color.WHITE);
                    health.setSelected(false);
                }
                b4 += 1;
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserPref();
            }
        });
    }

    private void updateUserPref(){
        String pref = "";
        progressBar.setVisibility(View.VISIBLE);

        user = FirebaseAuth.getInstance().getCurrentUser();
        if(health.isSelected()){
            pref += "Health, ";
        }
        if(social.isSelected()){
            pref += "Social, ";
        }
        if(intellect.isSelected()){
            pref += "Intellect, ";
        }
        if(exp.isSelected()){
            pref += "Experience";
        }
        if(pref != ""){
            myRef = database.getReference(user.getUid() + "/preference");
            myRef.setValue(pref);
        }

        progressBar.setVisibility(View.GONE);
    }
}