package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.User;
import com.svalero.funzones.utils.SessionUtil;

import java.io.InputStream;
import java.net.URL;

public class Login extends AppCompatActivity {
    private SessionUtil session;//global variable
    boolean flipFlop = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkExternalStoragePermission();
        checkInternetPermission();
        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().load("https://picsum.photos/500.jpg").into(imageView);
        TextView logUser = findViewById(R.id.logUsername);
        Intent intentFrom = getIntent();
        String username = intentFrom.getStringExtra("username");
        session = new SessionUtil(Login.this);
        if (username != null) {
            logUser.setText(username);
        }
        session.setUserId(0L);
        session.setUserName("");
    }

    public void refresh(View view) {

        String path = "https://picsum.photos/500?random=1.jpg";
        String path2 = "https://picsum.photos/500?random=2.jpg";
        ImageView imageView = findViewById(R.id.imageView);

        if (flipFlop) {
            Picasso.get().load(path).into(imageView);
            flipFlop = false;
        } else {
            Picasso.get().load(path2).into(imageView);
            flipFlop = true;
        }
    }

    public void login(View view) {

        TextView logUser = findViewById(R.id.logUsername);
        TextView logPassword = findViewById(R.id.logPassword);

        String username = logUser.getText().toString();
        String password = logPassword.getText().toString();

        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        try {
            User user = db.userDao().login(username, password);
            if (user != null) {
                Toast.makeText(this, R.string.loginOK, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Login.this, MainActivity.class);
                session.setUserId(user.getId());
                session.setUserName(user.getUsername());
                Log.i("AddActivity", "iduser" + session.getUserId());
                startActivity(intent);
            } else {
                Toast.makeText(this, R.string.loginNOK, Toast.LENGTH_LONG).show();
            }
        } catch (SQLiteConstraintException sce) {
            Snackbar.make(logUser, R.string.error, BaseTransientBottomBar.LENGTH_LONG).show();
        } finally {
            db.close();
        }
    }

    public void registerUser(View view) {
        Intent intent = new Intent(Login.this, AddUser.class);
        startActivity(intent);
    }

    private void checkExternalStoragePermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 225);
            }
        }
    }

    private void checkInternetPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.INTERNET)) {
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 225);
            }
        }
    }
}