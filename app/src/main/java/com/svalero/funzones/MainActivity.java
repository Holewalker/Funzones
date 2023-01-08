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

import com.svalero.funzones.utils.SessionUtil;

public class MainActivity extends AppCompatActivity {
    private SessionUtil session;//global variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        session = new SessionUtil(MainActivity.this);

        TextView textViewUsername = findViewById(R.id.username);
        textViewUsername.setText(session.getUserName());
    }


    public void listActivitiesNav(View view) {
        Intent intent = new Intent(MainActivity.this, ListActivities.class);
        startActivity(intent);
    }

    public void addActivityNav(View view) {
        Intent intent = new Intent(MainActivity.this, AddActivity.class);
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

