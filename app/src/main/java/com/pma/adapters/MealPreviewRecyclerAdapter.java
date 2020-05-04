package com.pma.adapters;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.R;
import com.pma.model.Meal;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MealPreviewRecyclerAdapter extends RecyclerView.Adapter<MealPreviewRecyclerAdapter.MealHolder> {

    private ArrayList<Meal> meals = new ArrayList<>();
    private MealClickListener listener;

    @NonNull
    @Override
    public MealHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.meal_preview_recycler_item, parent, false);
        return new MealHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MealHolder holder, int position) {
        Meal meal = meals.get(position);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        holder.mealNo.setText(Integer.toString(position + 1) + ".");
        holder.mealTime.setText(meal.getTimeString());
        holder.mealKcal.setText(df.format(meal.getTotalKcal()) + " kcal");
        holder.mealType.setText(meal.getType());

    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    public void setActivities(ArrayList<Meal> meals){
        this.meals = meals;
        notifyDataSetChanged();
    }

    class MealHolder extends  RecyclerView.ViewHolder{

        TextView mealNo;
        TextView mealTime;
        TextView mealKcal;
        TextView mealType;


        public MealHolder(@NonNull View itemView) {
            super(itemView);
            mealNo = itemView.findViewById(R.id.meal_no);
            mealTime = itemView.findViewById(R.id.meal_time);
            mealKcal = itemView.findViewById(R.id.meal_total_kcal);
            mealType = itemView.findViewById(R.id.meal_type);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onClick(1);
                    }
                }
            });
        }
    }

    public void setListener(MealClickListener listener){
        this.listener = listener;
    }

    public interface MealClickListener{
        public void onClick(int mealId);
    }
}
