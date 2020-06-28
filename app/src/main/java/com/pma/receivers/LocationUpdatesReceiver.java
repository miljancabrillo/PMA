package com.pma.receivers;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import com.google.android.gms.location.LocationResult;

import com.pma.dao.Database;
import com.pma.dao.LocationDao;

import java.util.Date;
import java.util.List;

public class LocationUpdatesReceiver extends BroadcastReceiver {

    public  static final String ACTION =
            "LOCATION_UPDATES_ACTION";

    private LocationDao locationDao;

    @Override
    public void onReceive(Context context, Intent intent) {
        //NotificationUtils.sendNotification(context,"Location updates received");

        locationDao = Database.getInstance(context).locationDao();

        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION.equals(action)) {

                LocationResult result = LocationResult.extractResult(intent);

                if (result != null) {
                    List<Location> locations = result.getLocations();
                    for (Location loc: locations) {
                        com.pma.model.Location location = new com.pma.model.Location();
                        location.setLon(loc.getLongitude());
                        location.setLat(loc.getLatitude());
                        location.setDateAndTime(new Date(loc.getTime()));
                        SaveLocationTask task =  new SaveLocationTask();
                        task.execute(location);
                    }
                }
            }
        }
    }

    private class SaveLocationTask extends AsyncTask<com.pma.model.Location, Void, Void>{

        @Override
        protected Void doInBackground(com.pma.model.Location... locations) {
            locationDao.insert(locations[0]);
            return null;
        }
    }
}
