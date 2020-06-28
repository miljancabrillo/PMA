package com.pma.fragments;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.PreferenceManager;
import androidx.preference.SwitchPreference;
import androidx.preference.SwitchPreferenceCompat;


import com.pma.R;
import com.pma.receivers.InternetConnectivityReceiver;
import com.pma.services.SynchronizationService;


public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.settings, rootKey);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final SwitchPreferenceCompat syncPreference = (SwitchPreferenceCompat) findPreference("dataSynchronization");

        syncPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                boolean isChecked = (boolean) newValue;

                AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getContext(), SynchronizationService.class);

                if(isChecked){
                    PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0,
                            intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,
                            SystemClock.elapsedRealtime(), AlarmManager.INTERVAL_HOUR * 6, pendingIntent);

                } else {
                    PendingIntent pendingIntent = PendingIntent.getService(getContext(), 0,
                            intent, PendingIntent.FLAG_NO_CREATE);
                    if(alarmManager != null && pendingIntent != null) alarmManager.cancel(pendingIntent);
                }
                syncPreference.setChecked(isChecked);
                return true;
            }
        });
    }
}
