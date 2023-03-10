package com.svalero.funzones.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.svalero.funzones.domain.Place;

import java.util.List;

@Dao
public interface PlaceDao {
    @Query("SELECT * FROM place")
    List<Place> getAll();

    @Query("SELECT * FROM place WHERE name = :name")
    Place getByName(String name);

    @Query("SELECT * FROM place WHERE id = :id")
    Place getById(long id);

    @Query("DELETE FROM place WHERE name = :name")
    void deleteByName(String name);

    @Insert
    void insert(Place place);

    @Delete
    void delete(Place place);

    @Update
    void update(Place place);
}
