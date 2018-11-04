package com.nibraas.betterme;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {

    ImageView btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(ProfileActivity.this, btn);
                popupMenu.getMenuInflater().inflate(R.menu.profile_menu, popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case  R.id.logout:
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(ProfileActivity.this, MainActivity.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                finish();
                                startActivity(i);
                                break;
                            case R.id.gotopref:
                                startActivity(new Intent(ProfileActivity.this, PreferenceActivity.class));
                                break;
                            case R.id.donate:
                                final AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                                builder.setMessage("This feature is coming soon!")
                                        .setTitle("Donate to charity");
                                builder.setPositiveButton("Can't wait!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                                builder.show();
                                break;
                            case R.id.notifications:
                                final AlertDialog.Builder builder2 = new AlertDialog.Builder(ProfileActivity.this);
                                builder2.setMessage("Come back once this is ready to change your notification settings")
                                        .setTitle("Notifications");
                                builder2.setPositiveButton("Can't wait!", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                                builder2.show();
                        }
                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }


}
