package com.pma.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.R;
import com.pma.model.Grocery;

import java.sql.ClientInfoStatus;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GroceryRecyclerAdapter extends RecyclerView.Adapter<GroceryRecyclerAdapter.GroceryHolder> {

    private ArrayList<Grocery> groceries = new ArrayList<>();
    private GroceryRecyclerAdapter.GroceryClickListener listener;

    private int selectedItem = -1;

    @NonNull
    @Override
    public GroceryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_recycler_item, parent, false);
        return new GroceryHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull final GroceryHolder holder, final int position) {
        Grocery grocery = groceries.get(position);
        DecimalFormat df = new DecimalFormat("#.##");

        holder.groceryName.setText(grocery.getName());
        holder.kcal.setText(df.format(grocery.getKcalPer100gr()) + " kcal");
        holder.protein.setText(df.format(grocery.getProteinPer100gr()) + " gr");
        holder.carb.setText(df.format(grocery.getCarbPer100gr()) + " gr");
        holder.fat.setText(df.format(grocery.getFatPer100gr()) + " gr");

        holder.expandable.setVisibility(grocery.isExpanded() ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public void setGroceries(ArrayList<Grocery> groceries) {
        this.groceries = groceries;
        notifyDataSetChanged();
    }

    class GroceryHolder extends RecyclerView.ViewHolder {

        TextView groceryName;
        TextView kcal;
        TextView protein;
        TextView fat;
        TextView carb;
        Button add;
        View expandable;

        public GroceryHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = itemView.findViewById(R.id.grocery_name);
            kcal = itemView.findViewById(R.id.kcal);
            protein = itemView.findViewById(R.id.protein);
            carb = itemView.findViewById(R.id.carb);
            fat = itemView.findViewById(R.id.fat);
            add = itemView.findViewById(R.id.add);

            this.expandable = itemView.findViewById(R.id.expandable_layout);
            groceryName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Grocery grocery = groceries.get(getAdapterPosition());
                    grocery.setExpanded(!grocery.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
            add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onGroceryClicked(groceries.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public interface GroceryClickListener {
        public void onGroceryClicked(Grocery grocery);
    }

    public void setListener(GroceryClickListener listener) {
        this.listener = listener;
    }
}