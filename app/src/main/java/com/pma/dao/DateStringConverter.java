package com.pma.dao;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateStringConverter {
    private static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

    @TypeConverter
    public static Date fromString(String value) {

        Date date = new Date();

        try {
            date = sdf.parse(value);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return value == null ? null : date;
    }

    @TypeConverter
    public static String fromDate(Date value) {
      String s;
      s = sdf.format(value);
      return s;
    }

}
