package com.pma.dao;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateIntConverter {
    @TypeConverter
    public static Date fromTimeStamp(Long value){
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimeStamp(Date date){
        return date == null ? null : date.getTime();
    }
}
