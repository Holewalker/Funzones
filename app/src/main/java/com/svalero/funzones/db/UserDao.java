package com.svalero.funzones.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.svalero.funzones.domain.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE username = :username")
    User getByName(String username);

    @Query("SELECT * FROM user WHERE id = :id")
    User getById(long id);

    @Query("DELETE FROM user WHERE username = :username")
    void deleteByUsername(String username);

    @Query("SELECT * FROM user WHERE username = :username AND password = :password")
    User login(String username, String password);

    @Insert
    void insert(User user);

    @Delete
    void delete(User user);

    @Update
    void update(User user);
}
