package com.svalero.funzones;

import static com.svalero.funzones.db.Constants.DATABASE_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.svalero.funzones.adapter.UserAdapter;
import com.svalero.funzones.db.AppDatabase;
import com.svalero.funzones.domain.User;

import java.util.ArrayList;
import java.util.List;

public class ListUsers extends AppCompatActivity {
    private List<User> users;
    private UserAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_users);
        users = new ArrayList<>();

        Intent intentFrom = getIntent();
        RecyclerView recyclerView = findViewById(R.id.rvListUsers);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new UserAdapter(this, users, intentFrom);
        recyclerView.setAdapter(adapter);

    }

    public void onResume() {
        super.onResume();
        users.clear();
        final AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, DATABASE_NAME).allowMainThreadQueries().build();
        users.addAll(db.userDao().getAll());
        adapter.notifyDataSetChanged();
    }

    public void returnNav(View view) {
        Intent intent = new Intent(ListUsers.this, MainActivity.class);
        startActivity(intent);
    }
}