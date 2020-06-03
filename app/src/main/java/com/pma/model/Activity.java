package com.pma.model;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Activity {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String userId;
    private Date date;
    private Date startTime;
    private Date endTime;
    private boolean finished = false;
    private float duration;
    private float kcalBurned;
    @Ignore
    List<Location> locations;

}
