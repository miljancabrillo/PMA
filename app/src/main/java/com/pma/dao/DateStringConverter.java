package com.pma.dao;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateStringConverter {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    private static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy-MM-dd");


    @TypeConverter
    public static Date fromString(String value) {

        Date date = new Date();

        try {
            if (value != null) {
                if (value.length() > 10) date = sdf.parse(value);
                else date = sdfShort.parse(value);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value == null ? null : date;
    }

    @TypeConverter
    public static String fromDate(Date value) {
        if (value == null) return null;
        String s;
        s = sdf.format(value);
        return s;
    }

}
