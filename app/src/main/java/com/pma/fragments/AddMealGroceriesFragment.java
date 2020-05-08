package com.pma.fragments;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.GroceryRecyclerAdapter;
import com.pma.model.Grocery;
import com.pma.view_model.AddMealViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMealGroceriesFragment extends Fragment implements GroceryRecyclerAdapter.GroceryClickListener{

    AddMealViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_meal_groceries, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AddMealViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.grocery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        final GroceryRecyclerAdapter adapter = new GroceryRecyclerAdapter();
        adapter.setListener(this);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        recyclerView.setAdapter(adapter);

        viewModel.getGroceries().observe(getViewLifecycleOwner(), new Observer<List<Grocery>>() {
            @Override
            public void onChanged(List<Grocery> groceries) {
                adapter.setGroceries((ArrayList<Grocery>) groceries);
            }
        });

        return root;
    }

    @Override
    public void onGroceryClicked(Grocery grocery) {

    }
}

