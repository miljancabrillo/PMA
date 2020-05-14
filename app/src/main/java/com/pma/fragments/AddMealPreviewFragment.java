package com.pma.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pma.DataMock;
import com.pma.R;
import com.pma.adapters.GroceryAmountRecyclerAdapter;
import com.pma.model.GroceryAndAmountPair;
import com.pma.model.Meal;
import com.pma.view_model.AddMealViewModel;

import org.w3c.dom.Text;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class AddMealPreviewFragment extends Fragment implements GroceryAmountRecyclerAdapter.GroceryAmountPairListener {

    AddMealViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_add_meal_preview, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AddMealViewModel.class);


        RecyclerView groceryAmountRecycler = root.findViewById(R.id.new_meal_grocery_amount_recycler);
        groceryAmountRecycler.setLayoutManager(new LinearLayoutManager(this.getContext()));
        groceryAmountRecycler.setHasFixedSize(true);

        //dodavanje dividera
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(groceryAmountRecycler.getContext(),
                ((LinearLayoutManager)groceryAmountRecycler.getLayoutManager()).getOrientation());
        groceryAmountRecycler.addItemDecoration(dividerItemDecoration);

        //kreiranje adaptera i ubacivanje podataka u adpter
        final GroceryAmountRecyclerAdapter adapter = new GroceryAmountRecyclerAdapter();
        adapter.setListener(this);
        groceryAmountRecycler.setAdapter(adapter);

        viewModel.getMeal().observe(getViewLifecycleOwner(), new Observer<Meal>() {
            @Override
            public void onChanged(Meal meal) {
                DecimalFormat df = new DecimalFormat("#.##");
                df.setRoundingMode(RoundingMode.CEILING);

                adapter.setPairs(meal.getGroceryAndAmountPairs());
                ((TextView)root.findViewById(R.id.total_kcal)).setText(df.format(meal.getTotalKcal()));
                ((TextView)root.findViewById(R.id.total_protein)).setText(df.format(meal.getTotalProtein()));
                ((TextView)root.findViewById(R.id.total_carb)).setText(df.format(meal.getTotalCarb()));
                ((TextView)root.findViewById(R.id.total_fat)).setText(df.format(meal.getTotalFat()));
            }
        });

        ((Button)root.findViewById(R.id.finish_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean success = viewModel.addMeal();
                if(success == false){
                    Toast.makeText(getContext(), "Meal can't be empty", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getContext(), "Meal added", Toast.LENGTH_LONG).show();
                }
            }
        });

        return root;
    }

    @Override
    public void onGroceryAmountPairClicked(GroceryAndAmountPair pair) {
        viewModel.removeGroceryAmountPair(pair);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item  = menu.findItem(R.id.search);
        item.setVisible(false);

    }
}
