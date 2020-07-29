package com.example.swymall;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.swymall.Adapters.GroceryItemAdapter;
import com.example.swymall.Models.GroceryItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

public class MainFragment extends Fragment {
    //declare member variables to initialize them
    private RecyclerView newItemsRecView, popularItemsRecView, suggestedItemsRecView;

    //initialize the adapter from the adapter java class
    private GroceryItemAdapter newItemsAdapter, popularItemsAdapter, suggestedItemsAdapter;

    private BottomNavigationView bottomNavigationView;

    //first override the  onCreateView method
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //create a view object
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        //call the initView method after initializing bottom nav view
        initViews(view);

        //call the initBottomNavView method which sets the icon home to be selected first each time we launch the app
        initBottomNavigationView();

        //call the method from utils class for clearing shared preference here
        //Utils.clearSharedPreferences(getActivity());

        //call the method to initiate the recyclerViews
    //    initRecViews();


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
       //call this method on onResume this is because when a user gives the star reviews and resumes to main activity
        // he should be able to recreate the main activity
        initRecViews();
    }

    //method to instantiate the recViews
    private void initRecViews() {
        newItemsAdapter = new GroceryItemAdapter(getActivity());
        newItemsRecView.setAdapter(newItemsAdapter);
        newItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        popularItemsAdapter = new GroceryItemAdapter(getActivity());
        popularItemsRecView.setAdapter(popularItemsAdapter);
        popularItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));


        suggestedItemsAdapter = new GroceryItemAdapter(getActivity());
        suggestedItemsRecView.setAdapter(suggestedItemsAdapter);
        suggestedItemsRecView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.HORIZONTAL, false));

        //we are going to receive all arrayList from the shared preferences
        ArrayList<GroceryItem> newItems = Utils.getAllItems(getActivity());
        //check to make sure arrayList is not null
        if (null != newItems) {
            //we are going to order the items on the recyclerView using a comparator
            Comparator<GroceryItem> newItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getId() - o2.getId();
                }
            };

            Comparator<GroceryItem> reverseComparator = Collections.reverseOrder(newItemsComparator);
            Collections.sort(newItems, reverseComparator);
            newItemsAdapter.setItems(newItems);
        }
        //for the second RecyclerView popular items
        ArrayList<GroceryItem> popularItems = Utils.getAllItems(getActivity());
        if (null != popularItems) {
            Comparator<GroceryItem> popularItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getPopularityPoint() - o2.getPopularityPoint();
                }
            };

            Collections.sort(popularItems, Collections.reverseOrder(popularItemsComparator));
            popularItemsAdapter.setItems(popularItems);
        }

        ArrayList<GroceryItem> suggestedItems = Utils.getAllItems(getActivity());
        if (null != suggestedItems) {

            Comparator<GroceryItem> suggestedItemsComparator = new Comparator<GroceryItem>() {
                @Override
                public int compare(GroceryItem o1, GroceryItem o2) {
                    return o1.getUserPoint() - o2.getUserPoint();
                }
            };
            Collections.sort(suggestedItems, Collections.reverseOrder(suggestedItemsComparator));
            suggestedItemsAdapter.setItems(suggestedItems);
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
                        Toast.makeText(getActivity(), "Selected", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.cart:
                        Intent cartIntent = new Intent(getActivity(), CartActivity.class);
                        cartIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(cartIntent);
                        break;
                    case R.id.search:
                        Intent intent = new Intent(getActivity(), SearchActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });

    }

    private void initViews(View view) {

        bottomNavigationView = view.findViewById(R.id.bottomNavView);
        newItemsRecView = view.findViewById(R.id.newItemRecView);
        popularItemsRecView = view.findViewById(R.id.popularItemRecView);
        suggestedItemsRecView = view.findViewById(R.id.suggestedItemsRecView);
    }
}
