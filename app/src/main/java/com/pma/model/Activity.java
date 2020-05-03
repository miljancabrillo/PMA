package com.pma.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Activity {

    //private ActivityType type;
    private String name;
    private Date dateAndTime;
    private float duration;
    private float kcalBurned;

}
