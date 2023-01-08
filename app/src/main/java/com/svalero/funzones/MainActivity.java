package com.svalero.funzones;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MainActivity extends AppCompatActivity {

    String username = null;
    String idUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textViewUsername = findViewById(R.id.username);
        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        idUser = intentFrom.getStringExtra("id_user");

        if (username != null) {
            textViewUsername.setText(username);
        }
    }


    public void listActivitiesNav(View view) {
        Intent intent = new Intent(MainActivity.this, ListActivities.class);
        intent.putExtra("username", username);
        intent.putExtra("idUser", idUser);
        startActivity(intent);
    }

    public void addActivityNav(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("id_user", idUser);
        startActivity(intent);
    }

    public void listPlacesNav(View view) {
        Intent intent = new Intent(MainActivity.this, ListPlaces.class);
        startActivity(intent);
    }

    public void addPlaceNav(View view) {
        Intent intent = new Intent(MainActivity.this, AddPlace.class);
        startActivity(intent);
    }

    public void listUsersNav(View view) {
        Intent intent = new Intent(MainActivity.this, ListUsers.class);
        startActivity(intent);
    }

    public void logoutNav(View view) {
        Intent intent = new Intent(MainActivity.this, Login.class);
        startActivity(intent);
    }


    public void share(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Hey, you should try this app!");
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
    }
}

