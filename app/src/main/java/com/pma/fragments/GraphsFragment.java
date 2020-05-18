package com.pma.fragments;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pma.R;
import com.pma.model.DailySummary;
import com.pma.view_model.GraphsViewModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class GraphsFragment extends Fragment {

    private GraphsViewModel graphViewModel;
    EditText date_in, date_in1;
    String date1 = "", date2 = "", category = "";
    Date date, d1, d2;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        /*graphViewModel =
                ViewModelProviders.of(this).get(GraphsViewModel.class);*/
        graphViewModel = new ViewModelProvider(this).get(GraphsViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_graphs, container, false);

        //get the spinner from the xml.
        final Spinner dropdown = root.findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Calories", "Carbs", "Fats", "Proteins"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        Date c = Calendar.getInstance().getTime();
        date = c;
        graphViewModel.setDate(date);

        category = dropdown.getSelectedItem().toString();

        final BarChart chart = (BarChart) root.findViewById(R.id.bar_chart);

        graphViewModel.getData();

        graphViewModel.getDaily().observe(getViewLifecycleOwner(), new Observer<List<DailySummary>>() {
            @Override
            public void onChanged(List<DailySummary> dailySummaries) {
                addBarChart(chart, date1, date2, category, dailySummaries);
            }
        });

        graphViewModel.getDailySummary().observe(getViewLifecycleOwner(), new Observer<List<DailySummary>>() {
            @Override
            public void onChanged(List<DailySummary> dailySummaries) {
                addBarChart(chart, date1, date2, category, dailySummaries);
            }
        });

        date_in = root.findViewById(R.id.date_input);
        date_in1 = root.findViewById(R.id.date_input1);

        date_in.setInputType(InputType.TYPE_NULL);
        date_in1.setInputType(InputType.TYPE_NULL);

        date_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_in);
            }
        });

        date_in1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(date_in1);
            }
        });

        root.findViewById(R.id.graph_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                date1 = date_in.getText().toString();
                date2 = date_in1.getText().toString();
                try {
                    d1 = df.parse(date1);
                    d2 = df.parse(date2);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                category = dropdown.getSelectedItem().toString();
                graphViewModel.setDates(d1, d2);
                if(!date1.matches("") && !date2.matches(""))
                    graphViewModel.getDataByDay();
                else if((date1.matches("") && !date2.matches("")) || (!date1.matches("") && date2.matches(""))){
                    Toast.makeText(getContext(), "Must fill all fields", Toast.LENGTH_LONG).show();
                } else {
                    graphViewModel.getData();
                }
            }
        });

        return root;
    }

    public void addBarChart (BarChart barChart, String date1, String date2, String category, List<DailySummary> dailySummaries){
        int i = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);
        barChart.setBorderColor(Color.RED);

        ArrayList<BarEntry> BarEntry = new ArrayList<>();

        if(date1.matches("") || date2.matches("")) {
            if(category.matches("Calories"))
                BarEntry.add(new BarEntry(0, dailySummaries.get(0).getKcalIn()));
            if(category.matches("Carbs"))
                BarEntry.add(new BarEntry(0, dailySummaries.get(0).getTotalCarb()));
            if(category.matches("Fats"))
                BarEntry.add(new BarEntry(0, dailySummaries.get(0).getTotalFat()));
            if(category.matches("Proteins"))
                BarEntry.add(new BarEntry(0, dailySummaries.get(0).getTotalProtein()));
        } else {
            for(DailySummary d : dailySummaries){
                if(category.matches("Calories"))
                    BarEntry.add(new BarEntry(i, d.getKcalIn()));
                if(category.matches("Carbs"))
                    BarEntry.add(new BarEntry(i, d.getTotalCarb()));
                if(category.matches("Fats"))
                    BarEntry.add(new BarEntry(i, d.getTotalFat()));
                if(category.matches("Proteins"))
                    BarEntry.add(new BarEntry(i, d.getTotalProtein()));
                i++;
            }
        }


        BarDataSet dataSet = new BarDataSet(BarEntry, "");
        dataSet.setColor(Color.parseColor("#de9621"));

        ArrayList<String> theDates = new ArrayList<>();
        if(date1.matches("") || date2.matches("")) {
            theDates.add(sdf.format(date));
        } else {
            for(DailySummary d : dailySummaries){
                theDates.add(sdf.format(d.getDay()));
            }
        }

        //uklanjanje sa srane osa
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getAxisLeft().setDrawLabels(false);
        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);
        barChart.getAxisLeft().setAxisMinimum(0);


        barChart.getXAxis().setGranularity(1f); // only intervals of 1 day
        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(theDates));


        BarData data = new BarData(dataSet);
        barChart.setData(data);
        barChart.invalidate();
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    }
