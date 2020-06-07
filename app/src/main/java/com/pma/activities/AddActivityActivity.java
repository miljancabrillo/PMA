package com.pma.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pma.R;
import com.pma.adapters.ActivityRecyclerAdapter;
import com.pma.model.ActivityType;
import com.pma.view_model.AddActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddActivityActivity extends AppCompatActivity implements ActivityRecyclerAdapter.ActivityClickListener{

    private AddActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_activity);

        viewModel = new ViewModelProvider(this).get(AddActivityViewModel.class);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView activitiesRecycler = findViewById(R.id.activity_type_recycler_view);
        activitiesRecycler.setLayoutManager(new LinearLayoutManager(this));
        activitiesRecycler.setHasFixedSize(true);

        ((SimpleItemAnimator) activitiesRecycler.getItemAnimator()).setSupportsChangeAnimations(false);

        final ActivityRecyclerAdapter adapter = new ActivityRecyclerAdapter();
        activitiesRecycler.setAdapter(adapter);
        adapter.setListener(this);

        viewModel.getActivityTypes().observe(this, new Observer<List<ActivityType>>() {
            @Override
            public void onChanged(List<ActivityType> activityTypes) {
                adapter.setActivities((ArrayList<ActivityType>) activityTypes);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        final SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel.searchActivityTypes(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.equals("") || newText == null)  viewModel.searchActivityTypes("");
                return true;
            }
        });

        ((ImageView)searchView.findViewById(R.id.search_close_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setQuery("",false);
                viewModel.searchActivityTypes("");
            }
        });

        return true;
    }

    @Override
    public void onActivityClicked(final ActivityType activityType) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setView(R.layout.dialog_title_and_edit_text);
        builder.setPositiveButton("Add activity", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                EditText durationEditText = ((AlertDialog)dialog).findViewById(R.id.custom_dialog_input_text);
                viewModel.addActivity(activityType, Float.parseFloat(durationEditText.getText().toString()));
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        ((TextView)dialog.findViewById(R.id.custom_dialog_title)).setText(activityType.getName());

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
}
