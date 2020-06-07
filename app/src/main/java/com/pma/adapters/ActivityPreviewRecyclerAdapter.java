package com.pma.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;

import com.pma.R;
import com.pma.model.Activity;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class ActivityPreviewRecyclerAdapter extends Adapter <ActivityPreviewRecyclerAdapter.ActivityHolder>{
    private ArrayList<Activity> activities = new ArrayList<>();

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_preview_recycler_item, parent, false);
        return new ActivityHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityHolder holder, int position) {
        Activity activity = activities.get(position);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        holder.activityName.setText(activity.getName());
        if(!activity.getName().equals("Bazalni metabolizam")) holder.activityDuration.setText(df.format(activity.getDuration()) + " min");
        else holder.activityDuration.setText("24 h");
        holder.activityKcalBurend.setText(df.format(activity.getKcalBurned()) + " kcal");
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public void setActivities(ArrayList<Activity> activities){
        this.activities = activities;
        notifyDataSetChanged();
    }

    class ActivityHolder extends  RecyclerView.ViewHolder{

        TextView activityName;
        TextView activityDuration;
        TextView activityKcalBurend;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activity_name);
            activityDuration = itemView.findViewById(R.id.activity_duration);
            activityKcalBurend = itemView.findViewById(R.id.activity_kcal_burned);
        }

    }
}
