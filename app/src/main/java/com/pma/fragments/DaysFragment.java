package com.pma.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.DataMock;
import com.pma.R;
import com.pma.activities.DayPreviewTabsActivity;
import com.pma.model.DailySummary;
import com.pma.view_model.DaysViewModel;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DaysFragment extends Fragment {

    private DaysViewModel daysViewModel;
    private RecyclerView recyclerView;
    private DaysAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        daysViewModel =
                ViewModelProviders.of(this).get(DaysViewModel.class);
        View root = inflater.inflate(R.layout.fragment_days, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        adapter = new DaysAdapter();
        adapter.setDays(DataMock.getInstance().getDailySummaries());

        recyclerView.setAdapter(adapter);

        //da je live data u observe bi mijenjo listu u adapteru da se osvjezava

        System.out.println(recyclerView.toString());
        return root;
    }

    private class DaysAdapter extends RecyclerView.Adapter<DaysAdapter.DaysHolder>{

        private ArrayList<DailySummary> days = new ArrayList<>();

        @NonNull
        @Override
        public DaysHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.day_preview_recycler_item, parent, false);
            return new DaysHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull DaysHolder holder, int position) {
            DailySummary day = days.get(position);

            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            holder.date.setText(new SimpleDateFormat("dd-MM-yyyy").format(day.getDay()));
            holder.kcalIn.setText(df.format(day.getKcalIn()) );
            holder.kcalOut.setText(df.format(day.getKcalOut()) );

            float diff = day.getKcalIn() - day.getKcalOut();

            if(diff > 0){
                String text = "+" + df.format(diff);
                holder.kcalDiff.setText(text + " kcal");
                holder.kcalDiff.setTextColor(Color.parseColor("#169139"));
            }else{
                String text = "-" + df.format(diff);
                holder.kcalDiff.setText(text + " kcal");
                holder.kcalDiff.setTextColor(Color.parseColor("#d1311f"));
            }
        }

        @Override
        public int getItemCount() {
            return days.size();
        }

        public void setDays(ArrayList<DailySummary> days){
            this.days = days;
            notifyDataSetChanged();
        }

        class DaysHolder extends  RecyclerView.ViewHolder{

            TextView date;
            TextView kcalIn;
            TextView kcalOut;
            TextView kcalDiff;

            public DaysHolder(@NonNull View itemView) {
                super(itemView);
                date = itemView.findViewById(R.id.day_preview_day);
                kcalIn = itemView.findViewById(R.id.day_preview_kcal_in);
                kcalOut = itemView.findViewById(R.id.day_preview_kcal_out);
                kcalDiff = itemView.findViewById(R.id.day_preview_kcal_diff);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(DaysFragment.this.getContext(), DayPreviewTabsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }
    }

}
