package com.pma.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.pma.R;
import com.pma.activities.DayPreviewActivity;
import com.pma.view_model.DaysViewModel;

public class DaysFragment extends Fragment {

    private DaysViewModel daysViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        daysViewModel =
                ViewModelProviders.of(this).get(DaysViewModel.class);
        View root = inflater.inflate(R.layout.fragment_days, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        daysViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        View button = root.findViewById(R.id.buttonTest);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), DayPreviewActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


}
