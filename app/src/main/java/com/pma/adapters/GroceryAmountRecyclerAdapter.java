package com.pma.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pma.R;
import com.pma.model.GroceryAndAmountPair;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class GroceryAmountRecyclerAdapter extends RecyclerView.Adapter<GroceryAmountRecyclerAdapter.GroceryAmountHolder> {

    private ArrayList<GroceryAndAmountPair> pairs = new ArrayList<>();
    private GroceryAmountPairListener listener;
    private boolean showDeleteBtn;

    public GroceryAmountRecyclerAdapter(boolean showDeleteBtn){
        this.showDeleteBtn = showDeleteBtn;
    }


    @NonNull
    @Override
    public GroceryAmountHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.grocery_amount_recycler_item, parent, false);
        return new GroceryAmountHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GroceryAmountHolder holder, int position) {
        GroceryAndAmountPair pair = pairs.get(position);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        holder.groceryName.setText(pair.getGrocery().getName());
        holder.groceryAmount.setText(Float.toString(pair.getAmount()) + " gr");
        holder.groceryKcal.setText(df.format(pair.getKcals()) + " kcal");

        if(!showDeleteBtn){
            ((ViewManager)holder.removeButton.getParent()).removeView(holder.removeButton);
            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) holder.groceryKcal.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.groceryKcal.setLayoutParams(params);
        }

    }

    @Override
    public int getItemCount() {
        return pairs.size();
    }

    public void setPairs(ArrayList<GroceryAndAmountPair> pairs){
        this.pairs = pairs;
        notifyDataSetChanged();
    }

    class GroceryAmountHolder extends  RecyclerView.ViewHolder{

        TextView groceryName;
        TextView groceryAmount;
        TextView groceryKcal;
        ImageButton removeButton;

        public GroceryAmountHolder(@NonNull View itemView) {
            super(itemView);
            groceryName = itemView.findViewById(R.id.grocery_name);
            groceryAmount = itemView.findViewById(R.id.grocery_amount);
            groceryKcal = itemView.findViewById(R.id.grocery_kcal);
            removeButton = itemView.findViewById(R.id.remove_button);

            removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        listener.onGroceryAmountPairClicked(pairs.get(getAdapterPosition()));
                    }
                }
            });
        }
    }

    public void setListener(GroceryAmountPairListener listener){
        this.listener = listener;
    }

    public interface GroceryAmountPairListener{
        public void onGroceryAmountPairClicked(GroceryAndAmountPair pair);
    }
}