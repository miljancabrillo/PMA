package com.pma.model;

import androidx.annotation.Nullable;
import androidx.room.Ignore;

import org.apache.commons.lang3.time.DateUtils;

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
public class DailySummary {

    private Date day;
    private Float kcalIn;
    private Float kcalOut;
    private Float totalProtein;
    private Float totalCarb;
    private Float totalFat;

    @Override
    public boolean equals(@Nullable Object obj) {
        DailySummary ds = (DailySummary) obj;
        return DateUtils.isSameDay(ds.getDay(), this.day);
    }
}
