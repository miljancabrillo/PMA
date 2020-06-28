package com.pma.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ActivityType {

    @PrimaryKey
    private int id;
    private String name;
    private float met;
    private boolean isSynced = false;

    public void setSynced(boolean synced){
        this.isSynced = synced;
    }
}
