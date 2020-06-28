package com.pma.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.system.Os;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Dash;
import com.google.android.gms.maps.model.Dot;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PatternItem;
import com.google.android.gms.maps.model.PolylineOptions;
import com.pma.R;
import com.pma.model.Activity;
import com.pma.model.Location;
import com.pma.view_model.DayPreviewViewModel;

import java.util.ArrayList;
import java.util.List;


public class MyMapFragment extends Fragment implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap map;
    private DayPreviewViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_map, container, false);

        viewModel = new ViewModelProvider(requireActivity()).get(DayPreviewViewModel.class);


        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        return v;
    }

    @Override
    public void onMapReady(final GoogleMap map) {

        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(45.2671, 19.8335), 13));

        viewModel.getWalkingActivities().observe(getViewLifecycleOwner(), new Observer<List<Activity>>() {
            @Override
            public void onChanged(List<Activity> activities) {
                if(activities == null) return;

                float sumLat = 0;
                float sumLon = 0;
                int numOfLocations = 0;

                for(Activity activity : activities){
                    if(activity.getLocations() == null) continue;
                    if(activity.getDuration() < 3) continue;


                    PolylineOptions options = new PolylineOptions();
                    options.color(Color.parseColor("#ff0000"));

                    ArrayList<PatternItem> pattern = new ArrayList<>();
                    pattern.add(new Dot());
                    options.pattern(pattern);

                    for (Location loc: activity.getLocations()) {

                        numOfLocations++;
                        sumLat += loc.getLat();
                        sumLon += loc.getLon();

                        LatLng latLng = new LatLng(loc.getLat(),loc.getLon());
                        options.add(latLng);
                    }
                    map.addPolyline(options);
                }

               if(numOfLocations != 0){
                   map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(sumLat/numOfLocations, sumLon/numOfLocations), 13));
               }

            }
        });
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

}
