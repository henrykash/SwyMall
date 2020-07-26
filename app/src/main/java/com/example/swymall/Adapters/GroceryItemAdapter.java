package com.example.swymall.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.swymall.GroceryItemActivity;
import com.example.swymall.Models.GroceryItem;
import com.example.swymall.R;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import static com.example.swymall.GroceryItemActivity.GROCERY_ITEM_KEY;

public class GroceryItemAdapter extends RecyclerView.Adapter<GroceryItemAdapter.ViewHolder> {

    //initialize the ArrayList and Context
    private ArrayList<GroceryItem> items = new ArrayList<>();
    private Context context;

    public GroceryItemAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grocery_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.txtName.setText(items.get(position).getName());
        holder.txtPrice.setText(String.valueOf(items.get(position).getPrice()) + "$");

        //use glide to set the images
        Glide.with(context)
                .asBitmap()
                .load(items.get(position).getImageUrl())
                .into(holder.image);

        holder.parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:navigate the user to groceryItemActivity
                Intent intent = new Intent(context, GroceryItemActivity.class);
                intent.putExtra(GROCERY_ITEM_KEY, items.get(position));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<GroceryItem> items) {
        this.items = items;
        //method called when data is changed for each item
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
     //initialize the member variable of the grocery item layout
        private TextView txtName, txtPrice;
        private ImageView  image;
        private MaterialCardView parent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtName = (itemView).findViewById(R.id.txtName);
            txtPrice = (itemView).findViewById(R.id.txtPrice);
            image = (itemView).findViewById(R.id.image);
            parent = (itemView).findViewById(R.id.parent);
        }
    }
}