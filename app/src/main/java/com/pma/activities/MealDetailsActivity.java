package com.pma.activities;


import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.GroceryAmountRecyclerAdapter;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class MealDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        PieChart pieChart = findViewById(R.id.pie_chart);
        addChartData(pieChart);

        RecyclerView groceryAmountRecycler = findViewById(R.id.grocery_amount_recycler_view);
        groceryAmountRecycler.setLayoutManager(new LinearLayoutManager(this));
        groceryAmountRecycler.setHasFixedSize(true);

        GroceryAmountRecyclerAdapter adapter = new GroceryAmountRecyclerAdapter();
        adapter.setPairs(DataMock.getInstance().getMeals().get(0).getGroceryAndAmountPairs());
        groceryAmountRecycler.setAdapter(adapter);

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
