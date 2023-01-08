package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.funzones.adapter.PlaceAdapter;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Place;

import java.util.ArrayList;
import java.util.List;

public class ListPlaces extends AppCompatActivity {
    private List<Place> places;
    private PlaceAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_places);
        places = new ArrayList<>();

        Intent intentFrom = getIntent();
        RecyclerView recyclerView = findViewById(R.id.rvListPlaces);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new PlaceAdapter(this, places, intentFrom);
        recyclerView.setAdapter(adapter);

    }

    public void onResume() {
        super.onResume();
        places.clear();
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        places.addAll(db.placeDao().getAll());
        adapter.notifyDataSetChanged();
    }

    public void returnNav(View view) {
        Intent intent = new Intent(ListPlaces.this, MainActivity.class);
        startActivity(intent);
    }
}