package com.pma.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.pma.R;
import com.pma.adapters.GroceryRecyclerAdapter;
import com.pma.model.Grocery;
import com.pma.view_model.GroceriesFragmentViewModel;

import java.util.ArrayList;
import java.util.List;

public class GroceriesFragment extends Fragment implements GroceryRecyclerAdapter.GroceryClickListener {

    private GroceriesFragmentViewModel viewModel;
    private GroceryRecyclerAdapter adapter;
    private RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel =  new ViewModelProvider(this).get(GroceriesFragmentViewModel.class);

        final View root = inflater.inflate(R.layout.fragment_groceries, container, false);

        recyclerView = root.findViewById(R.id.groceries_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new GroceryRecyclerAdapter();
        adapter.setListener(this);
        recyclerView.setAdapter(adapter);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);

        viewModel.getGroceries().observe(getViewLifecycleOwner(), new Observer<List<Grocery>>() {
            @Override
            public void onChanged(List<Grocery> groceries) {
                adapter.setGroceries((ArrayList<Grocery>) groceries);
            }
        });

        root.findViewById(R.id.search_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchString = ((TextView)root.findViewById(R.id.search_groceries)).getText().toString();
                viewModel.searchGroceries(searchString);
            }
        });

        return root;
    }

    @Override
    public void onGroceryClicked(Grocery grocery) {
        ((TextView)getView().findViewById(R.id.kcal)).setText(grocery.getKcalPer100gr() + " kcalsafa");
        ((TextView)getView().findViewById(R.id.protein)).setText(grocery.getProteinPer100gr() + " gr");
        ((TextView)getView().findViewById(R.id.fat)).setText(grocery.getFatPer100gr() + " gr");
        ((TextView)getView().findViewById(R.id.carb)).setText(grocery.getCarbPer100gr() + " gr");
    }
}
