package com.pma.model;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailySummary {

    private Date day;
    private Float kcalIn;
    private Float kcalOut;

}
