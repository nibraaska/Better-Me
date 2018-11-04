package com.nibraas.betterme;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class HomePageActivity extends AppCompatActivity {

    float x1, y1, x2, y2;
    FirebaseDatabase firebaseDatabase;
    TextView quote;
    String child;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);


        firebaseDatabase = FirebaseDatabase.getInstance();
        quote = findViewById(R.id.quote);

        getQuote();


    }

    private void getQuote() {
        final DatabaseReference ref = firebaseDatabase.getReference("Quotes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long num = dataSnapshot.getChildrenCount();
                int randomNum = new Random().nextInt(((int)num) + 1);
                child = Integer.toString(randomNum);
                final DatabaseReference ref2 = firebaseDatabase.getReference("Quotes/" + child);
                ref2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        quote.setText(dataSnapshot.getValue().toString());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(HomePageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(HomePageActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();
                if(y1 > y2){
                    Intent i = new Intent(HomePageActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }
}
