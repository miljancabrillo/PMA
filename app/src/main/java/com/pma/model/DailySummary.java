package com.pma.model;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySummary {

    private Date day;
    private Float kcalIn;
    private Float kcalOut;
    private Float totalProtein;
    private Float totalCarb;
    private Float totalFat;

}
