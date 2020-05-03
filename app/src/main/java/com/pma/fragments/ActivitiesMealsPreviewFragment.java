package com.pma.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pma.DataMock;
import com.pma.R;
import com.pma.activities.MealDetailsActivity;
import com.pma.adapters.ActivityPreviewRecyclerAdapter;
import com.pma.adapters.MealPreviewRecyclerAdapter;

public class ActivitiesMealsPreviewFragment extends Fragment implements MealPreviewRecyclerAdapter.MealClickListener{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_activities_meals_preview, container, false);

        //ubacivanje podataka za recyclev view aktivnosti
        RecyclerView activitiesRecycler = rootView.findViewById(R.id.activities_recycler_view);
        activitiesRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        activitiesRecycler.setHasFixedSize(true);

        ActivityPreviewRecyclerAdapter activitiesAdapter = new ActivityPreviewRecyclerAdapter();
        activitiesAdapter.setActivities(DataMock.getInstance().getActivities());
        activitiesRecycler.setAdapter(activitiesAdapter);


        RecyclerView mealsRecycler = rootView.findViewById(R.id.meals_recycler_view);
        mealsRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        mealsRecycler.setHasFixedSize(true);

        MealPreviewRecyclerAdapter mealsAdapter = new MealPreviewRecyclerAdapter();
        mealsAdapter.setActivities(DataMock.getInstance().getMeals());
        mealsRecycler.setAdapter(mealsAdapter);

        mealsAdapter.setListener(this);

        return rootView;
    }

    @Override
    public void onClick(int mealId) {
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        startActivity(intent);
    }
}
