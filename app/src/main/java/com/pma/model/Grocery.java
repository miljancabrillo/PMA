package com.pma.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Grocery {

    private int id;
    private String name;
    private float kcalPer100gr;
    private float proteinPer100gr;
    private float carbPer100gr;
    private float fatPer100gr;

}
