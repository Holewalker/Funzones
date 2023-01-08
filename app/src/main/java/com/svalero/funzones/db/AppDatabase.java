package com.svalero.funzones.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.svalero.funzones.domain.Activity;
import com.svalero.funzones.domain.Place;
import com.svalero.funzones.domain.User;

@Database(entities = {Activity.class,Place.class, User.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {

    public abstract UserDao userDao();
    public abstract PlaceDao placeDao();
    public abstract ActivityDao activityDao();

}
