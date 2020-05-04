package com.pma.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.GroceryAmountRecyclerAdapter;

public class AddMealPreviewFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_add_meal_preview, container, false);

        RecyclerView groceryAmountRecycler = root.findViewById(R.id.new_meal_grocery_amount_recycler);
        groceryAmountRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        groceryAmountRecycler.setHasFixedSize(true);

        GroceryAmountRecyclerAdapter adapter = new GroceryAmountRecyclerAdapter();
        adapter.setPairs(DataMock.getInstance().getMeals().get(0).getGroceryAndAmountPairs());
        groceryAmountRecycler.setAdapter(adapter);

        return root;
    }
}
