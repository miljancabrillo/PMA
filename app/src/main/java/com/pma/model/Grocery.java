package com.pma.model;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
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
    @Ignore
    private boolean expanded = false;

    @Override
    public boolean equals(@Nullable Object obj) {
        if(this.id == ((Grocery)obj).getId()) return true;
        return false;
    }
}
