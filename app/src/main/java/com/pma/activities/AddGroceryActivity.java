package com.pma.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.pma.R;
import com.pma.utils.Utils;
import com.pma.view_model.AddGroceryViewModel;

public class AddGroceryActivity extends AppCompatActivity {

    private AddGroceryViewModel viewModel;
    private EditText name;
    private EditText kcal;
    private EditText protein;
    private EditText carb;
    private EditText fat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewModel = new ViewModelProvider(this).get(AddGroceryViewModel.class);

        findViewById(R.id.add_grocery_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = findViewById(R.id.grocery_name);
                kcal = findViewById(R.id.grocery_kcal);
                protein = findViewById(R.id.grocery_proteins);
                carb = findViewById(R.id.grocery_carbs);
                fat = findViewById(R.id.grocery_fats);

                if(name.getText().toString().matches("") || protein.getText().toString().matches("")
                        || carb.getText().toString().matches("") || fat.getText().toString().matches("")
                        || kcal.getText().toString().matches("")){

                    Toast.makeText(AddGroceryActivity.this, "Must fill all fields", Toast.LENGTH_LONG).show();

                } else {

                    viewModel.getGrocery().setName(name.getText().toString());
                    viewModel.getGrocery().setKcalPer100gr(Float.parseFloat(kcal.getText().toString()));
                    viewModel.getGrocery().setProteinPer100gr(Float.parseFloat(protein.getText().toString()));
                    viewModel.getGrocery().setCarbPer100gr(Float.parseFloat(carb.getText().toString()));
                    viewModel.getGrocery().setFatPer100gr(Float.parseFloat(fat.getText().toString()));
                    viewModel.getGrocery().setUserEmail(Utils.getCurrentUsername(AddGroceryActivity.this));
                    viewModel.addGrocery();

                    Toast.makeText(AddGroceryActivity.this, "Grocery added", Toast.LENGTH_LONG).show();
                    clear();
                }
            }
        });

    }

    public void clear() {
        name.getText().clear();
        kcal.getText().clear();
        protein.getText().clear();
        carb.getText().clear();
        fat.getText().clear();
    }

}
