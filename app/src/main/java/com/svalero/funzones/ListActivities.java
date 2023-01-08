package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.funzones.adapter.ActivityAdapter;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.Activity;

import java.util.ArrayList;
import java.util.List;

public class ListActivities extends AppCompatActivity {
    private List<Activity> activities;
    private ActivityAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_activities);
        activities = new ArrayList<>();

        Intent intentFrom = getIntent();
        RecyclerView recyclerView = findViewById(R.id.rvListActivities);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new ActivityAdapter(this, activities, intentFrom);
        recyclerView.setAdapter(adapter);

    }

    public void onResume() {
        super.onResume();
        activities.clear();
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        activities.addAll(db.activityDao().getAll());
        adapter.notifyDataSetChanged();
    }

    public void returnNav(View view) {
        Intent intent = new Intent(ListActivities.this, MainActivity.class);
        startActivity(intent);
    }
}