package com.example.swymall.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swymall.Models.Review;
import com.example.swymall.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {

    //initialize the array list
    private ArrayList <Review> reviews = new ArrayList<>();
    //initialize the constructor
    public ReviewsAdapter(){

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviews_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtUserName.setText(reviews.get(position).getUserName());
        holder.txtDate.setText(reviews.get(position).getDate());
        holder.txtReview.setText(reviews.get(position).getText());


    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        //initialize the views
        private TextView txtUserName, txtDate, txtReview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtUserName =itemView.findViewById(R.id.txtUserName);
            txtReview =itemView.findViewById(R.id.txtReview);
            txtDate =itemView.findViewById(R.id.txtDate);
        }
    }
}
