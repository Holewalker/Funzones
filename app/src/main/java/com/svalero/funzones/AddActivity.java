package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Activity;
import com.svalero.funzones.domain.User;
import com.svalero.funzones.utils.DatePickerFragment;

import java.sql.Date;

public class AddActivity extends AppCompatActivity {
    private Activity editActivity = null;
    String username = null;
    String idUser = null;
    EditText addActivityDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);
        Intent intentFrom = getIntent();
        editActivity = (Activity) intentFrom.getSerializableExtra("activity");
        username = intentFrom.getStringExtra("username");
        idUser = intentFrom.getStringExtra("idUser");
        EditText addActivityIdPlace = findViewById(R.id.addActivityIdPlace);
        EditText addActivityName = findViewById(R.id.addActivityName);
        EditText addActivityDesc = findViewById(R.id.addActivityDescription);
        addActivityDate = findViewById(R.id.addActivityDate);

        if (editActivity != null) {
            addActivityIdPlace.setText(String.valueOf(editActivity.getId_place()));
            addActivityName.setText(editActivity.getName());
            addActivityDesc.setText(editActivity.getDescription());
            addActivityDate.setText(String.valueOf(editActivity.getDate()));
        }
    }


    public void register(View view) {
        Intent intentFrom = getIntent();
        username = intentFrom.getStringExtra("username");
        idUser = intentFrom.getStringExtra("idUser");
        EditText addActivityIdPlace = findViewById(R.id.addActivityIdPlace);
        EditText addActivityName = findViewById(R.id.addActivityName);
        EditText addActivityDesc = findViewById(R.id.addActivityDescription);
        addActivityDate = findViewById(R.id.addActivityDate);

        Activity newActivity = new Activity(Integer.parseInt(idUser), Integer.parseInt(addActivityIdPlace.getText().toString()), addActivityName.getText().toString(), addActivityDesc.getText().toString(),addActivityDate.getText().toString());
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        try {
            if (editActivity != null) {
                db.activityDao().update(newActivity);
            } else
                db.activityDao().insert(newActivity);

            Intent intent = new Intent(AddActivity.this, MainActivity.class);
            Toast.makeText(this, newActivity.getName() + " Has been registered", Toast.LENGTH_LONG).show();
            startActivity(intent);

        } catch (SQLiteConstraintException sqLiteConstraintException) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();

        } finally {
            db.close();
        }


    }

    public void returnNav(View view) {
        Intent intent = new Intent(AddActivity.this, MainActivity.class);
        startActivity(intent);
    }

    public void dateSelector(View view) {
        if (view.getId() == R.id.addActivityDate) {
            showDatePickerDialog();
        }
    }

    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = day + "/" + (month + 1) + "/" + year;
                addActivityDate.setText(selectedDate);
            }
        });
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
}