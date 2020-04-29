package com.pma.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

    @PrimaryKey()
    @NonNull
    private String email;
    private String password;
    private float weight;
    private float height;
    private int age;
    private boolean isSynced = false;
}
