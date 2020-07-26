package com.example.swymall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.swymall.Adapters.ReviewsAdapter;
import com.example.swymall.Models.GroceryItem;
import com.example.swymall.Models.Review;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.ArrayList;

public class GroceryItemActivity extends AppCompatActivity implements AddReviewDialog.AddReview {

    private static final String TAG = "GroceryItemActivity";

    @Override
    public void onAddReviewResult(Review review) {
        Log.d(TAG, "onAddReviewResult: new review" + review);
        //call the method from utils class to add reviews to shared preferences
        Utils.addReview(this, review);
        ArrayList<Review> reviews = Utils.getReviewById(this, review.getGroceryItemId());
        if (null != reviews) {
            adapter.setReviews(reviews);
        }
    }

    public static final String GROCERY_ITEM_KEY = "incoming_item";
    //initialize the views
    private RecyclerView reviewsRecView;
    private TextView txtName, txtPrice, txtDescription, txtAddReviews;
    private ImageView itemImage, firstEmptyStar, firstFilledStar, secondEmptyStar, secondFilledStar, thirdEmptyStar, thirdFilledStar;
    private Button btnAddToCart;
    private RelativeLayout firstRelLayout, secondRelLayout, thirdRelLayout;

    //initialize the toolbar
    private MaterialToolbar toolbar;

    private GroceryItem incomingItem;

    private ReviewsAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_item);

        //set the actionbar
        setSupportActionBar(toolbar);

        //call the init method from which we initialized the views
        initViews();

        //lets create our review adapter
        adapter = new ReviewsAdapter();

        //to receive incoming GroceryItem object
        Intent intent = getIntent();
        //check if the intent is null or not
        if (null != intent) {
            incomingItem = intent.getParcelableExtra(GROCERY_ITEM_KEY);
            //write an if statement to make sure incoming item is not null incase its not initialize the text views
            if (null != incomingItem) {
                txtName.setText(incomingItem.getName());
                txtDescription.setText(incomingItem.getDescription());
                txtPrice.setText(String.valueOf(incomingItem.getPrice()) + "$");

                //show the image with glide
                Glide.with(this)
                        .asBitmap()
                        .load(incomingItem.getImageUrl())
                        .into(itemImage);
                //get the reviews from the Utils class
                ArrayList<Review> reviews = Utils.getReviewById(this, incomingItem.getId());

                reviewsRecView.setAdapter(adapter);
                reviewsRecView.setLayoutManager(new LinearLayoutManager(this));
                //check the reviews to make sure its not null in the array list
                if (null != reviews) {
                    if (reviews.size() > 0) {
                        adapter.setReviews(reviews);
                    }
                }
                //create onclick listeners of btnAddToCart
                btnAddToCart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:complete this later, add item to the cart and navigate user to cart activity
                        Utils.addToCart(GroceryItemActivity.this, incomingItem);
                    }
                });
                //create onclick listener for the txtAddReview
                txtAddReviews.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //TODO:show a dialogue
                        AddReviewDialog dialog = new AddReviewDialog();
                        Bundle bundle = new Bundle();
                        bundle.putParcelable(GROCERY_ITEM_KEY, incomingItem);
                        dialog.setArguments(bundle);
                        dialog.show(getSupportFragmentManager(), "Add review");
                    }
                });
                //method called to handle the rating...call this method inside the if block
                handleRating();
            }

        }
    }

    //a method to handle the rating
    private void handleRating() {
        switch (incomingItem.getRate()) {

            case 0:
                //when all the stars are not filled
                firstEmptyStar.setVisibility(View.VISIBLE);
                firstFilledStar.setVisibility(View.GONE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 1:
                //when only the first star is filled set visibility visible
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.VISIBLE);
                secondFilledStar.setVisibility(View.GONE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 2:
                //make two of the stars in rating visible
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.VISIBLE);
                thirdFilledStar.setVisibility(View.GONE);
                break;

            case 3:
                //show all the three filled rating stars
                firstEmptyStar.setVisibility(View.GONE);
                firstFilledStar.setVisibility(View.VISIBLE);
                secondEmptyStar.setVisibility(View.GONE);
                secondFilledStar.setVisibility(View.VISIBLE);
                thirdEmptyStar.setVisibility(View.GONE);
                thirdFilledStar.setVisibility(View.VISIBLE);
                break;
        }

        //set on click listeners for each of relative layouts
        firstRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 1) {    //when a user clicks on the first star
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 1);
                    incomingItem.setRate(1);
                    handleRating(); //call this method to handle the visibility of rating stars
                }
            }
        });

        secondRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 2) {    //when a user clicks on the first star
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 2);
                    incomingItem.setRate(2); //from the switch statement
                    handleRating(); //call this method to handle the visibility of rating stars
                }
            }
        });

        thirdRelLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (incomingItem.getRate() != 3) {    //when a user clicks on the first star
                    Utils.changeRate(GroceryItemActivity.this, incomingItem.getId(), 3);
                    incomingItem.setRate(3);
                    handleRating(); //call this method to handle the visibility of rating stars
                }
            }
        });
    }


    //method to instantiate the views
    private void initViews() {
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtDescription = findViewById(R.id.txtDescription);
        txtAddReviews = findViewById(R.id.txtAddReview);

        itemImage = findViewById(R.id.itemImage);


        firstEmptyStar = findViewById(R.id.firstEmptyStar);
        firstFilledStar = findViewById(R.id.firstFilledStar);
        secondEmptyStar = findViewById(R.id.secondEmptyStar);
        secondFilledStar = findViewById(R.id.secondFilledStar);
        thirdEmptyStar = findViewById(R.id.thirdEmptyStar);
        thirdFilledStar = findViewById(R.id.thirdFilledStar);

        btnAddToCart = findViewById(R.id.btnAddToCart);
        reviewsRecView = findViewById(R.id.reviewsRecView);
        firstRelLayout = findViewById(R.id.firstStarRelLayout);
        secondRelLayout = findViewById(R.id.secondStarRelLayout);
        thirdRelLayout = findViewById(R.id.thirdStarRelLayout);

        toolbar = findViewById(R.id.toolbar);
    }


}