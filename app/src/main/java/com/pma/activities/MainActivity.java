package com.pma.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityTransition;
import com.google.android.gms.location.ActivityTransitionRequest;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.pma.R;
import com.pma.receivers.ActivityDetectionReceiver;
import com.pma.receivers.LocationUpdatesReceiver;
import com.pma.services.SynchronizationService;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private static final long UPDATE_INTERVAL = 20000; // Every 20 seconds.
    private static final long FASTEST_UPDATE_INTERVAL = 10000; // Every 10 seconds
    private static final long MAX_WAIT_TIME = UPDATE_INTERVAL * 5; // Every 5 minutes.


    private LocationRequest mLocationRequest;
    private FusedLocationProviderClient mFusedLocationClient;

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_days, R.id.nav_graph, R.id.nav_profile, R.id.nav_settings)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        SharedPreferences generalPreferences = getSharedPreferences("com.pma.GENERAL_PREFERENCES", Context.MODE_PRIVATE);
        boolean activityRecognitionEnabled = generalPreferences.getBoolean("activityRecognitionEnabled", false);


        boolean activityRecognitionPermitted  = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_GRANTED;

        boolean locationAccessForegroundPermitted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        boolean locationAccessBackgroundPermitted = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;

        if(android.os.Build.VERSION.SDK_INT >= 29) {
            if (!(activityRecognitionPermitted && locationAccessBackgroundPermitted && locationAccessForegroundPermitted)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACTIVITY_RECOGNITION, Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                                Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        } else{
            if (!locationAccessForegroundPermitted) {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            }
            if(!activityRecognitionEnabled) enableActivityRecognition();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_new_meal:
                Intent intent = new Intent(this, AddMealTabsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_new_grocery:
                Intent i = new Intent(this, AddGroceryActivity.class);
                startActivity(i);
                return true;
            case R.id.action_log_out:
                SharedPreferences preferences = getSharedPreferences("com.pma.LOGIN_PREFERENCES", Context.MODE_PRIVATE);
                preferences.edit().putString("userId", "").commit();

                Intent logOutIntent = new Intent(this, LoginActivity.class);
                logOutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(logOutIntent);
                finish();
                return true;
            case R.id.action_new_activity:
                Intent intent2 = new Intent(this, AddActivityActivity.class);
                startActivity(intent2);
                return true;
            case R.id.synchronization:
                Intent intent3 = new Intent(this, SynchronizationService.class);
                startService(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        enableActivityRecognition();
                    } else {
                        //Treba obraditi slucaj ako mozda odbije pristup
                    }
                    if(grantResults[1] == PackageManager.PERMISSION_GRANTED){
                        enableLocationTracking();
                    }else{
                        //opet pitat ako odbije
                    }
                }
                return;
            }
            case 2:{
                if (grantResults.length > 0){
                    if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        enableLocationTracking();
                    }
                }
                return;
            }
        }
    }

    private void enableActivityRecognition() {
        List<ActivityTransition> transitions = new ArrayList<>();

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_ENTER)
                        .build());

        transitions.add(
                new ActivityTransition.Builder()
                        .setActivityType(DetectedActivity.WALKING)
                        .setActivityTransition(ActivityTransition.ACTIVITY_TRANSITION_EXIT)
                        .build());

        ActivityTransitionRequest request = new ActivityTransitionRequest(transitions);

        Intent intent = new Intent(this, ActivityDetectionReceiver.class);
        intent.setAction("ACTIVITY_RECOGNITION_ACTION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Task<Void> task = ActivityRecognition.getClient(this)
                .requestActivityTransitionUpdates(request, pendingIntent);

        task.addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void result) {
                        SharedPreferences generalPreferences = getSharedPreferences("com.pma.GENERAL_PREFERENCES", Context.MODE_PRIVATE);
                        generalPreferences.edit().putBoolean("activityRecognitionEnabled", true).commit();
                        Toast.makeText(MainActivity.this, "Registering successful", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        task.addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                    }
                }
        );
    }

    private void enableLocationTracking(){

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setMaxWaitTime(MAX_WAIT_TIME);
        mLocationRequest.setSmallestDisplacement(5f);

        Intent intent = new Intent(this, LocationUpdatesReceiver.class);
        intent.setAction("LOCATION_UPDATES_ACTION");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mFusedLocationClient.requestLocationUpdates(mLocationRequest, pendingIntent);

    }

}
