package com.pma.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Grocery {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private float kcalPer100gr;
    private float proteinPer100gr;
    private float carbPer100gr;
    private float fatPer100gr;

}
