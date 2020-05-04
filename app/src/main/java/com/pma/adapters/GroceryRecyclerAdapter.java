package com.pma.adapters;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.R;
import com.pma.model.Grocery;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;

public class GroceryRecyclerAdapter extends  RecyclerView.Adapter<GroceryRecyclerAdapter.GroceryHolder> {

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
    public void onBindViewHolder(@NonNull GroceryHolder holder, final int position) {
        Grocery grocery = groceries.get(position);
        holder.groceryName.setText(grocery.getName());
        holder.itemView.setTag(groceries.get(position));

        holder.itemView.setBackgroundColor(Color.WHITE);

        if (selectedItem == position) {
            holder.itemView.setBackgroundColor(Color.GRAY);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int previousItem = selectedItem;
                selectedItem = position;

                notifyItemChanged(previousItem);
                notifyItemChanged(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return groceries.size();
    }

    public void setGroceries(ArrayList<Grocery> groceries){
        this.groceries = groceries;
        notifyDataSetChanged();
    }

    class GroceryHolder extends  RecyclerView.ViewHolder{

        TextView groceryName;
        View itemView;

        public GroceryHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = itemView.findViewById(R.id.grocery_name);
            this.itemView = itemView;
        }
    }

    public interface GroceryClickListener{
        public void onGroceryClicked(int groceryId);
    }

    public void setListener(GroceryClickListener listener) {
        this.listener = listener;
    }
}