package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.User;

public class AddUser extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        EditText regUsername = findViewById(R.id.regUsername);
        EditText regPassword = findViewById(R.id.regPassword);
        EditText regEmail = findViewById(R.id.regEmail);

        User newUser = new User(regUsername.toString(), regPassword.toString(), regEmail.toString());
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        try {
            db.userDao().insert(newUser);
            Intent intent = new Intent(AddUser.this, Login.class);
            startActivity(intent);

        } catch (SQLiteConstraintException sqLiteConstraintException) {

        } finally {
            db.close();
        }


    }
}

