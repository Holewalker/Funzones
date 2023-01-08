package com.svalero.funzones.domain;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(indices = {@Index(value = {"id"}, unique = true)})

public class Place {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;

}
