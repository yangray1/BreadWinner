package com.example.crystalyip.csc301.ViewHolder;

import com.example.crystalyip.csc301.Interface.ItemClickListener;
import com.example.crystalyip.csc301.R;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class FoodViewListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtFoodName;
    public ImageView imageView;

    private ItemClickListener itemClickListener;

    public FoodViewListHolder(@NonNull View itemView) {
        super(itemView);

        txtFoodName = (TextView)itemView.findViewById(R.id.food_description);
        imageView = (ImageView)itemView.findViewById(R.id.food_image);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View view) {
        //itemClickListener.onClick(view, getAdapterPosition(), false);
    }
}
