package com.pma.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.R;
import com.pma.model.Activity;
import com.pma.model.ActivityType;

import java.util.ArrayList;

public class ActivityRecyclerAdapter extends RecyclerView.Adapter<ActivityRecyclerAdapter.ActivityHolder> {

    private ArrayList<ActivityType> activities = new ArrayList<>();
    private ActivityRecyclerAdapter.ActivityClickListener listener;

    @NonNull
    @Override
    public ActivityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_recycler_item, parent, false);
        return new ActivityHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final ActivityHolder holder, final int position) {
        ActivityType activity = activities.get(position);
        holder.activityName.setText(activity.getName());
        holder.itemView.setTag(activities.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onActivityClicked(activities.get(holder.getAdapterPosition()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return activities.size();
    }

    public void setActivities(ArrayList<ActivityType> activities) {
        this.activities = activities;
        notifyDataSetChanged();
    }

    class ActivityHolder extends RecyclerView.ViewHolder {

        TextView activityName;
        View itemView;

        public ActivityHolder(@NonNull View itemView) {
            super(itemView);
            activityName = itemView.findViewById(R.id.activity_name);
            this.itemView = itemView;
        }
    }

    public interface ActivityClickListener {
        public void onActivityClicked(ActivityType activity);
    }

    public void setListener(ActivityClickListener listener) {
        this.listener = listener;
    }
}
