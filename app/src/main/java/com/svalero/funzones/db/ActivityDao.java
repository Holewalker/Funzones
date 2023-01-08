package com.svalero.funzones.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.funzones.domain.Activity;
import com.svalero.funzones.domain.Place;

import java.util.List;

@Dao
public interface ActivityDao {
    @Query("SELECT * FROM activity")
    List<Activity> getAll();

    @Query("SELECT * FROM activity WHERE name = :name")
    Activity getByName(String name);

    @Query("SELECT * FROM activity WHERE id = :id")
    Activity getById(long id);

    @Query("DELETE FROM activity WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Activity activity);

    @Delete
    void delete(Activity activity);

    @Update
    void update(Activity activity);
}
