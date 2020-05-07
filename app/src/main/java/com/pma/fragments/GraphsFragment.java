package com.pma.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.github.mikephil.charting.charts.BarChart;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.pma.R;
import com.pma.view_model.GraphsViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GraphsFragment extends Fragment {

    private GraphsViewModel graphViewModel;
    EditText date_in;
    EditText date_in1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        graphViewModel =
                ViewModelProviders.of(this).get(GraphsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_graphs, container, false);
        /*final Button button = root.findViewById(R.id.graph_button);
        graphViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/

        BarChart chart = (BarChart) root.findViewById(R.id.bar_chart);
        addBarChart(chart);

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

        //get the spinner from the xml.
        Spinner dropdown = root.findViewById(R.id.spinner1);
        //create a list of items for the spinner.
        String[] items = new String[]{"Calories", "Carbs", "Fats", "Proteins"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        return root;
    }

    public void addBarChart (BarChart barChart){

        barChart.getLegend().setEnabled(false);
        barChart.getDescription().setEnabled(false);

        ArrayList<BarEntry> BarEntry = new ArrayList<>();

        BarEntry.add(new BarEntry(2f, 0));
        BarEntry.add(new BarEntry(4f, 1));
        BarEntry.add(new BarEntry(6f, 2));
        BarEntry.add(new BarEntry(8f, 3));
        BarEntry.add(new BarEntry(7f, 4));
        BarEntry.add(new BarEntry(3f, 5));

        BarDataSet dataSet = new BarDataSet(BarEntry, "");

        ArrayList<String> theDates = new ArrayList<>();

        theDates.add("08.04.2020.");
        theDates.add("09.04.2020.");
        theDates.add("10.04.2020.");
        theDates.add("11.04.2020.");
        theDates.add("12.04.2020.");
        theDates.add("13.04.2020.");

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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(getContext(), dateSetListener, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    }
