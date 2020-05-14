package com.pma.fragments;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.text.InputType;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.pma.R;
import com.pma.adapters.GroceryRecyclerAdapter;
import com.pma.model.Grocery;
import com.pma.model.GroceryAndAmountPair;
import com.pma.view_model.AddMealViewModel;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddMealGroceriesFragment extends Fragment implements GroceryRecyclerAdapter.GroceryClickListener {

    AddMealViewModel viewModel;
    Grocery selectedGrocery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_add_meal_groceries, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AddMealViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.grocery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        //dodavanje dividera
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                ((LinearLayoutManager) recyclerView.getLayoutManager()).getOrientation());

        final GroceryRecyclerAdapter adapter = new GroceryRecyclerAdapter();
        adapter.setListener(this);

        ((SimpleItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        recyclerView.setAdapter(adapter);

        //setButtonListeners(root);
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
        selectedGrocery = grocery;
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Add grocery")
                .inputType(InputType.TYPE_CLASS_NUMBER)
                .input("Amount in grams", "", new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(MaterialDialog dialog, CharSequence input) {
                        if (input.toString().equals("")) {
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(false);
                        }else{
                            dialog.getActionButton(DialogAction.POSITIVE).setEnabled(true);
                        }
                    }
                }).onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                       float amount = Float.parseFloat(dialog.getInputEditText().getText().toString());
                       viewModel.addGroceryAmountPair(new GroceryAndAmountPair(selectedGrocery,amount));
                    }
                 })
                .alwaysCallInputCallback()
                .build();
        //TU SE MOZE MODIFIKOVATI
        dialog.getActionButton(DialogAction.POSITIVE).setTextSize(16);
        dialog.show();
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.search);
        item.setVisible(false);

    }
}

