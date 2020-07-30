package com.example.swymall;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.widget.GridView;

import com.example.swymall.Models.GroceryItem;
import com.example.swymall.Models.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

//inside this class we can now create our data source
public class Utils {
    private static int ID = 0;
    private static int ORDER_ID = 0;

    private static final String DB_NAME = "fake_database";

    private static final String ALL_ITEMS_KEY = "all_items";

    private static final String CART_ITEMS_KEY = "cart_items";

    //use Gson file to cast the data into arrayList so declare it here
    private static Gson gson = new Gson();

    //type for converting Gson files into arrayList of GroceryItems
    private static Type groceryListType = new TypeToken<ArrayList<GroceryItem>>() {
    }.getType();


    //this method is going to initialize our shared preferences before usage in MainActivity
    public static void initSharedPreferences(Context context) {
        //first make sure there are no shared preferences saved in our application NB make sure we wre not recreating shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);

        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        //if there is some data in the ALL_ITEMS_KEY in shared preferences we are going to receive arrayList of items if there is not any
        //we are going to receive null for the default value
        if (null == allItems) {
            //initialize all of our items inside the below method
            initAllItems(context);
        }


    }

    private static void initAllItems(Context context) {
        //create an arrayList of different groceryItems for all of my items
        ArrayList<GroceryItem> allItems = new ArrayList<>();
        allItems.add(new GroceryItem(5.3,
                "IceCream ", "IceCream is a yummy",
                "https://www.psdmockups.com/wp-content/uploads/2019/08/Flat-Lid-Ice-Cream-Container-Tub-PSD-Mockup.jpg",
                "food", 18));


        GroceryItem milk = new GroceryItem(5.3,
                "milk ", "IceCream is a yummy",
                "https://www.psdmockups.com/wp-content/uploads/2020/06/Milk-Carton-Pour-Spout-PSD-Mockup.jpg",
                "drinks", 18);
        allItems.add(milk);

        GroceryItem soda = new GroceryItem(0.90,
                "Soda", "Tasty",
                "https://www.psdmockups.com/wp-content/uploads/2019/12/8oz-Stubby-Soda-Can-PSD-Mockup.jpg",
                "food", 10);
        allItems.add(soda);

        GroceryItem soap = new GroceryItem(10,
                "Soap", "Tasty",
                "https://www.psdmockups.com/wp-content/uploads/2019/12/Cosmetic-Soap-Bar-Packaging-PSD-Mockup.jpg",
                "detergents", 20);
        allItems.add(soap);

        GroceryItem nuts = new GroceryItem(23,
                "Nuts", "Tasty",
                "https://www.psdmockups.com/wp-content/uploads/2017/02/Brochure-Flyer-Pen-Bowl-Of-Pistachio-Nuts-PSD-Mockup.jpg",
                "food", 2);
        allItems.add(nuts);

        GroceryItem honey = new GroceryItem(2,
                "Honey", "Tasty",
                "https://www.psdmockups.com/wp-content/uploads/2018/05/Hexagon-Shaped-Honey-Jar-Container-Twist-Cap.jpg",
                "food", 9);
        allItems.add(honey);


        GroceryItem pizza = new GroceryItem(10.4, "pizza", "wonderful made pizza",
                "https://www.psdmockups.com/wp-content/uploads/2017/12/Closed-Pizza-Pie-Box-Cover-PSD-Mockup.jpg",
                "junk", 30);
        allItems.add(pizza);
        // pizza.setPopularityPoint(3);
        //pizza.setUserPoint(2);

        //add allItems arrayList to our shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ALL_ITEMS_KEY, gson.toJson(allItems));
        editor.commit();
    }

    //a method to clear the shared preferences call it inside the main fragment before anything
    public static void clearSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }

    //create a method to get the ArrayList
    public static ArrayList<GroceryItem> getAllItems(Context context) {
        //first initialize the shared preference object
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        return allItems;

    }

    //create a method to handle the ratings inside the GroceryItemActivity
    public static void changeRate(Context context, int itemId, int newRate) {
        //initialize the shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //receive array list of all of the items inside the shared preferences
        ArrayList<GroceryItem> allItems = gson.fromJson(sharedPreferences.getString(ALL_ITEMS_KEY, null), groceryListType);
        //check if the ArrayList is null or not
        if (null != allItems) {
            //if its not null create a new ArrayList
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                //if the id equals the id of the item we are receiving
                if (i.getId() == itemId) {
                    //set the rate to the new rate we are receiving
                    i.setRate(newRate);
                    newItems.add(i);  //add the i to a new list of items

                } else {
                    //add the i to my new items array list
                    newItems.add(i);
                }
            }
            //delete the all current array list inside the shared preferences
            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems)); //put new items inside the shared preferences
            editor.commit();
        }
    }

    //create a method to handle the reviews
    public static void addReview(Context context, Review review) {
        //initialize the shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        //receive array list of all of the items inside the shared preferences
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            //if allItems is not null then create a new ArrayList of GroceryItem
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i : allItems) {
                if (i.getId() == review.getGroceryItemId()) {
                    ArrayList<Review> reviews = new ArrayList<>();
                    reviews.add(review);
                    i.setReviews(reviews);
                    newItems.add(i);
                } else {
                    newItems.add(i);
                }
            }

            editor.remove(ALL_ITEMS_KEY);
            editor.putString(ALL_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }

    }

    public static ArrayList<Review> getReviewById(Context context, int itemId) {
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            for (GroceryItem i : allItems) {
                if (i.getId() == itemId) {
                    ArrayList<Review> reviews = i.getReviews();
                    return reviews;
                }
            }
        }
        return null;
    }

    //create method to add to cart
    public static void addItemToCart(Context context, GroceryItem item) {
        //initialize the shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);
        //check if the cartItem is null and initialize the array list
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        cartItems.add(item);
        Editor editor = sharedPreferences.edit();
        editor.remove(CART_ITEMS_KEY); //to remove the current item from the cart
        editor.putString(CART_ITEMS_KEY, gson.toJson(cartItems));
        editor.commit();
    }

    //create a method to add all the items from the array list to the cart
    public static ArrayList<GroceryItem> getCartItems(Context context) {
        //initialize the shared preferences
        SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
        ArrayList<GroceryItem> cartItems = gson.fromJson(sharedPreferences.getString(CART_ITEMS_KEY, null), groceryListType);

        return cartItems;
    }

    //create a method to handle search for items when the user searches for items
    public static ArrayList<GroceryItem> searchForItems(Context context, String text) {
        //first get the ArrayList of all the items
        ArrayList<GroceryItem> allItems = getAllItems(context);
        //check if allItems is not mull
        if (null != allItems) {
            //create a new array list of grocery items
            ArrayList<GroceryItem> items = new ArrayList<>();
            for (GroceryItem item : allItems) {
                if (item.getName().equalsIgnoreCase(text)) {
                    //add the item to the items array list here the user enters the exact name as inside the shared preferences
                    items.add(item);
                }
                //here is a logic to split the text the user uses for searching
                String[] name = item.getName().split(" ");
                for (int i = 0; i < name.length; i++) {
                    if (text.equalsIgnoreCase(name[i])) {
                        //in order to avoid duplicate value we write a boolean

                        boolean doesExist = false; //initialize it to false
                        //write a for loop to iterate through the items found in shared preferences
                        for (GroceryItem j : items) {
                            if (j.getId() == item.getId()) {
                                doesExist = true;
                            }
                        }

                        if (!doesExist) {
                            items.add(item);
                        }

                    }

                }
            }
            return items;
        }
        return null;
    }

    //create a method to handle the different categories
    public static ArrayList<String> getCategories(Context context) {
        //array list to get all of the items
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            //create a new Array list of type string
            ArrayList<String> categories = new ArrayList<>();
            //iterate through the allItems ArrayList and add different categories
            for (GroceryItem item : allItems) {
                //create a boolean to check if the categories already exist to avoid duplicate
                boolean doesExist = false;
                for (String s : categories) {
                    if (item.getCategory().equals(s)) {
                        //then change the value of the boolean to true
                        doesExist = true;
                    }
                }
                //check if it does not exist to our array list we are going to add the categories
                if (!doesExist) {
                    categories.add(item.getCategory());
                }
            }
            return categories;
        }
        return null;

    }

    //create a method to get items with a specific category
    public static ArrayList<GroceryItem> getItemsByCategory(Context context, String category) {
        //get the array list of all of the items
        ArrayList<GroceryItem> allItems = getAllItems(context);
        if (null != allItems) {
            //create a new Array list of type string
            ArrayList<GroceryItem> items = new ArrayList<>();
            //iterate through the allItems ArrayList and add different categories
            for (GroceryItem item : allItems) {
                if (item.getCategory().equals(category)) {
                    items.add(item);
                }
            }
            return items;
        }
        return null;
    }

    public static void deleteItemFromCart(Context context, GroceryItem item) {
        ArrayList<GroceryItem> cartItems = getCartItems(context);
        if (null != cartItems) {
            ArrayList<GroceryItem> newItems = new ArrayList<>();
            for (GroceryItem i: cartItems) {
                if (i.getId() != item.getId()) {
                    newItems.add(i);
                }
            }

            SharedPreferences sharedPreferences = context.getSharedPreferences(DB_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.remove(CART_ITEMS_KEY);
            editor.putString(CART_ITEMS_KEY, gson.toJson(newItems));
            editor.commit();
        }
    }

    //create a getter for the ID as was first initialized to 0 this method auto generates the IDs for our views
    public static int getID() {
        //increase the value of the id
        ID++;
        return ID;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }
}
