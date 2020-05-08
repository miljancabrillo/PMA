package com.pma.fragments;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.preference.PreferenceFragmentCompat;

import com.pma.R;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

}
