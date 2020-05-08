package com.pma.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.os.Bundle;

import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.ActivityRecyclerAdapter;

public class AddActivityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView activitiesRecycler = findViewById(R.id.activity_type_recycler_view);
        activitiesRecycler.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecycler.setHasFixedSize(true);

        ((SimpleItemAnimator) activitiesRecycler.getItemAnimator()).setSupportsChangeAnimations(false);


        ActivityRecyclerAdapter adapter = new ActivityRecyclerAdapter();
        adapter.setActivities(DataMock.getInstance().getActivityTypes());
        activitiesRecycler.setAdapter(adapter);

    }
}
