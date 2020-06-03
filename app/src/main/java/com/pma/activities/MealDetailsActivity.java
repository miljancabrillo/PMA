package com.pma.activities;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.GroceryAmountRecyclerAdapter;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Meal;
import com.pma.view_model.MealDetailsViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class MealDetailsActivity extends AppCompatActivity {

    private MealDetailsViewModel viewModel;
    private int mealId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mealId = getIntent().getIntExtra("mealId", -1);
        viewModel = new ViewModelProvider(this).get(MealDetailsViewModel.class);

        viewModel.setMealId(mealId);

        final PieChart pieChart = findViewById(R.id.pie_chart);


        RecyclerView groceryAmountRecycler = findViewById(R.id.grocery_amount_recycler_view);
        groceryAmountRecycler.setLayoutManager(new LinearLayoutManager(this));
        groceryAmountRecycler.setHasFixedSize(true);

        final GroceryAmountRecyclerAdapter adapter = new GroceryAmountRecyclerAdapter(false);
        groceryAmountRecycler.setAdapter(adapter);

        viewModel.getPairs().observe(this, new Observer<List<GroceryAndAmountPair>>() {
            @Override
            public void onChanged(List<GroceryAndAmountPair> pairs) {
                adapter.setPairs((ArrayList<GroceryAndAmountPair>) pairs);
            }
        });

        viewModel.getMeal().observe(this, new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                DecimalFormat df = new DecimalFormat("#.##");
                ((TextView) findViewById(R.id.total_proteins)).setText(df.format(meal.getTotalProtein()) + " gr");
                ((TextView) findViewById(R.id.total_carbs)).setText(df.format(meal.getTotalCarb()) + " gr");
                ((TextView) findViewById(R.id.total_fats)).setText(df.format(meal.getTotalFat()) + " gr");
                ((TextView) findViewById(R.id.total_kcals)).setText(df.format(meal.getTotalKcal()) + " kcal");

                addChartData(pieChart, meal.getTotalProtein(), meal.getTotalCarb(), meal.getTotalFat());
            }
        });

    }

    private void addChartData(PieChart pieChart, float proteins, float carbs, float fats) {

        pieChart.setDrawEntryLabels(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDescription(new Description());
        pieChart.setHoleRadius(10f);
        pieChart.setTransparentCircleAlpha(0);
        pieChart.getDescription().setEnabled(false);

        ArrayList<PieEntry> yValues = new ArrayList<>();

        yValues.add(new PieEntry(proteins, "proteins"));
        yValues.add(new PieEntry(fats, "fats"));
        yValues.add(new PieEntry(carbs, "carbs"));

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
        pieData.setDrawValues(false);
        pieChart.setData(pieData);
        pieChart.invalidate();

    }
}
