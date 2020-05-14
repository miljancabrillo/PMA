package com.pma.activities;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.pma.adapters.DayPreviewPagerAdapter;

import  com.pma.R;
import com.pma.view_model.DayPreviewViewModel;
import com.pma.view_model.DaysViewModel;

import java.util.Date;

public class DayPreviewTabsActivity extends AppCompatActivity {

    private DayPreviewViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_preview_tabs);

        viewModel =  new ViewModelProvider(this).get(DayPreviewViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DayPreviewPagerAdapter dayPreviewPagerAdapter = new DayPreviewPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(dayPreviewPagerAdapter);

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        long milliseconds = (long) getIntent().getLongExtra("date", -1);
        viewModel.setDate(new Date(milliseconds));
    }

}