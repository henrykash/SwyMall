package com.example.swymall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.swymall.Adapters.GroceryItemAdapter;
import com.example.swymall.Models.GroceryItem;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";

    private MaterialToolbar toolbar;
    private EditText searchBox;
    private ImageView btnSearch;
    private TextView firstCat, secondCat, thirdCat, txtAllCategories;
    private BottomNavigationView bottomNavigationView;
    private RecyclerView recyclerView;

    private GroceryItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //cal the initView method here
        initViews();

        //call the initBottomNavigationView method
        initBottomNavigationView();

        //set the support action for the toolbar
        setSupportActionBar(toolbar);

        //initialize the adapter for the GroceryItemAdapter
        adapter = new GroceryItemAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        //set up a click listener for the btnSearch which is an image view
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the initSearch method here rather initialize it
                initSearch();
            }
        });

        //this click listener handles or monitors the edit text changes inside the search activity
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //call the initSearch method here also
                initSearch();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //array list of different categories  of the grocery items
        ArrayList<String> categories = Utils.getCategories(this);
        if (null != categories) {
            if (categories.size() > 0) {
                if (categories.size() == 1) {
                    showCategories(categories, 1);
                } else if (categories.size() == 2) {
                    showCategories(categories, 2);
                } else {
                    showCategories(categories, 3);
                }
            }
        }
    }

    //method to show the categories of the items
    private void showCategories(final ArrayList<String> categories, int i) {
        switch (i) {
            case 1:
                //handle the visibility of the first category
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);

                //set an Onclick listener for the first text view to show a list of items to this specific category inside the recyclerview adapter
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            case 2:
                //handle the visibility of the first category
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.GONE);

                //set an Onclick listener for the first text view to show a list of items to this specific category inside the recyclerview adapter
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });

                //set an Onclick listener for the second text view to show a list of items to this specific category inside the recyclerview adapter
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });
                break;
            case 3:
                //handle the visibility of the first category
                firstCat.setVisibility(View.VISIBLE);
                firstCat.setText(categories.get(0));
                secondCat.setVisibility(View.VISIBLE);
                secondCat.setText(categories.get(1));
                thirdCat.setVisibility(View.VISIBLE);
                thirdCat.setText(categories.get(2));

                //set an Onclick listener for the first text view to show a list of items to this specific category inside the recyclerview adapter
                firstCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(0));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });

                //set an Onclick listener for the second text view to show a list of items to this specific category inside the recyclerview adapter
                secondCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(1));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });
                //set an Onclick listener for the second text view to show a list of items to this specific category inside the recyclerview adapter
                thirdCat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //use the method from utils class to get the category of items from groceryItem
                        ArrayList<GroceryItem> items = Utils.getItemsByCategory(SearchActivity.this, categories.get(2));
                        if (null != items){
                            adapter.setItems(items);
                        }
                    }
                });

                break;
            default:
                //set the visibility of the text views to GONE
                firstCat.setVisibility(View.GONE);
                secondCat.setVisibility(View.GONE);
                thirdCat.setVisibility(View.GONE);
                break;
        }
    }

    //a method to perform the search work
    private void initSearch() {
        //first check if the user has entered anything or not
        if (!searchBox.getText().toString().equals("")) {
            //TODO:write the logic to get items from the shared preferences
            String name = searchBox.getText().toString(); //get the text of our editText

            ArrayList<GroceryItem> items = Utils.searchForItems(this, name);
            //check if this array list is null or empty
            if (null != items) {
                adapter.setItems(items);
            }
        }
    }

    //this method sets the home icon first to be selected when  we launch our app
    private void initBottomNavigationView() {

        bottomNavigationView.setSelectedItemId(R.id.home);
        //TODO:finish this 21/5/2020
        //set each of the bottom navigation items on click listener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.home:
                        //navigate the user to the main activity
                        Intent intent = new Intent(SearchActivity.this, MainActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK); //to clear the back stack
                        startActivity(intent);

                        break;
                    case R.id.cart:
                        break;
                    case R.id.search:
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initViews() {
        toolbar = findViewById(R.id.toolbar);
        searchBox = findViewById(R.id.searchBox);
        btnSearch = findViewById(R.id.btnSearch);

        firstCat = findViewById(R.id.txtFirstCat);
        secondCat = findViewById(R.id.txtSecondtCat);
        thirdCat = findViewById(R.id.txtThirdtCat);
        txtAllCategories = findViewById(R.id.txtAllCategories);
        bottomNavigationView = findViewById(R.id.bottomNavView);
        recyclerView = findViewById(R.id.recyclerView);
    }
}
