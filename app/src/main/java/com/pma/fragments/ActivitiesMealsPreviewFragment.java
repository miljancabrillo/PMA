package com.pma.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.pma.DataMock;
import com.pma.R;
import com.pma.activities.MealDetailsActivity;
import com.pma.adapters.ActivityPreviewRecyclerAdapter;
import com.pma.adapters.MealPreviewRecyclerAdapter;

import java.util.ArrayList;

public class ActivitiesMealsPreviewFragment extends Fragment implements MealPreviewRecyclerAdapter.MealClickListener{

    //proslijediti datum i na osnovu njega povaditi sve iz baze
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

        PieChart pieChart = rootView.findViewById(R.id.pie_chart);
        addChartData(pieChart);

        return rootView;
    }

    @Override
    public void onClick(int mealId) {
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        startActivity(intent);
    }

    private void addChartData(PieChart pieChart){

        pieChart.setEntryLabelColor(Color.parseColor("#000000"));
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription(new Description());
        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.getDescription().setEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(132, "proteins"));
        yValues.add(new PieEntry(234, "fats"));
        yValues.add(new PieEntry(132, "carbs"));

        //create the data set
        PieDataSet pieDataSet = new PieDataSet(yValues, "");
        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueTextSize(12);

        //add colors to dataset
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.parseColor("#3dd149"));
        colors.add(Color.parseColor("#136bd6"));
        colors.add(Color.parseColor("#d62e1e"));
        pieDataSet.setColors(colors);

        //create pie data object
        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }

}
