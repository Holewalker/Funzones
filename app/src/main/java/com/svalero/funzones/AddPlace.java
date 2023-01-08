package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Place;
import com.svalero.funzones.domain.User;

public class AddPlace extends AppCompatActivity {
    private Place editPlace= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);
        Intent intentFrom = getIntent();
        editPlace = (Place) intentFrom.getSerializableExtra("place");
        EditText addPlaceName = findViewById(R.id.addPlaceName);
        EditText addPlaceDesc = findViewById(R.id.addPlaceDesc);
        EditText addPlaceAddress = findViewById(R.id.addPlaceAddress);
        EditText addPlaceLon = findViewById(R.id.addPlaceLon);
        EditText addPlaceLat = findViewById(R.id.addPlaceLat);

        if (editPlace != null) {
            addPlaceName.setText(editPlace.getName());
            addPlaceDesc.setText(editPlace.getDescription());
            addPlaceAddress.setText(editPlace.getAddress());
             addPlaceLon.setText(String.valueOf(editPlace.getLongitude()));
            addPlaceLat.setText(String.valueOf(editPlace.getLatitude()));
        }
    }


    public void register(View view) {

        EditText addPlaceName = findViewById(R.id.addPlaceName);
        EditText addPlaceDesc = findViewById(R.id.addPlaceDesc);
        EditText addPlaceAddress = findViewById(R.id.addPlaceAddress);
        EditText addPlaceLon = findViewById(R.id.addPlaceLon);
        EditText addPlaceLat = findViewById(R.id.addPlaceLat);
        Place newPlace = new Place(addPlaceName.getText().toString(), addPlaceDesc.getText().toString(), addPlaceAddress.getText().toString(), Double.parseDouble(addPlaceLon.getText().toString()), Double.parseDouble(addPlaceLat.getText().toString()));
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();

        try {
            if (editPlace != null) {
                db.placeDao().update(newPlace);
            } else
                db.placeDao().insert(newPlace);

            Intent intent = new Intent(AddPlace.this, MainActivity.class);
            Toast.makeText(this, newPlace.getName() + " Has been registered", Toast.LENGTH_LONG).show();
            startActivity(intent);

        } catch (SQLiteConstraintException sqLiteConstraintException) {
            Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show();

        } finally {
            db.close();
        }


    }

    public void returnNav(View view) {
        Intent intent = new Intent(AddPlace.this, MainActivity.class);
        startActivity(intent);
    }
}