package com.pma.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.SharedPreferences;
import android.provider.ContactsContract;

import com.pma.dao.Database;
import com.pma.dao.UserDao;
import com.pma.model.User;

import java.lang.reflect.AccessibleObject;

public class Utils {

    public static String getCurrentUsername(Context context){
        SharedPreferences loginPreferences = context.getSharedPreferences("com.pma.LOGIN_PREFERENCES", Context.MODE_PRIVATE);
        return loginPreferences.getString("userId", "");
    }

}
