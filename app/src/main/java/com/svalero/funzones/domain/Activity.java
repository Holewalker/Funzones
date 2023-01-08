package com.svalero.funzones.domain;

import static androidx.room.ForeignKey.CASCADE;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.svalero.funzones.utils.DateConverter;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
/*
@Entity(
        foreignKeys = {
                @ForeignKey(
                        entity = User.class,
                        parentColumns = "id",
                        childColumns = "id_user",
                        onDelete = CASCADE
                ),
                @ForeignKey(
                        entity = Place.class,
                        parentColumns = "id",
                        childColumns = "id_place",
                        onDelete = CASCADE
                )},
        indices = {
                @Index(value = {"id"}, unique = true),
                @Index("id_user"),
                @Index("id_place")
        })
*/
@Entity(foreignKeys = {
        @ForeignKey(entity = User.class, parentColumns = "id", childColumns = "id_user", onDelete = CASCADE),
        @ForeignKey(entity = Place.class, parentColumns = "id", childColumns = "id_place", onDelete = CASCADE)
})
public class Activity implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private long id_user;
    private long id_place;
    private String name;

    public Activity(long id_user, long id_place, String name, String description, Date date) {
        this.id_user = id_user;
        this.id_place = id_place;
        this.name = name;
        this.description = description;
        this.date = date;
    }

    private String description;
    @TypeConverters(DateConverter.class)

    private Date date;
}
