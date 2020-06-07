package com.pma.fragments;

import android.content.DialogInterface;
import android.graphics.Color;
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

import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.Theme;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View root = inflater.inflate(R.layout.fragment_add_meal_groceries, container, false);

        viewModel = new ViewModelProvider(getActivity()).get(AddMealViewModel.class);

        RecyclerView recyclerView = root.findViewById(R.id.grocery_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

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
    public void onGroceryClicked(final Grocery grocery) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setView(R.layout.dialog_title_and_edit_text);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                EditText amountEditText = ((AlertDialog)dialog).findViewById(R.id.custom_dialog_input_text);
                float amount = Float.parseFloat(amountEditText.getText().toString());
                viewModel.addGroceryAmountPair(new GroceryAndAmountPair(grocery,amount));
                dialog.dismiss();

            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        ((TextView)dialog.findViewById(R.id.custom_dialog_title)).setText("Add grocery");
        ((TextView)dialog.findViewById(R.id.custom_dialog_input_text)).setHint("Amount");

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
        ((EditText)dialog.findViewById(R.id.custom_dialog_input_text)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().equals("") || s == null){
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
                }else{
                    dialog.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

        MenuItem item = menu.findItem(R.id.search);
        item.setVisible(false);

    }
}

