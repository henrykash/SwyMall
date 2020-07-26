package com.example.swymall.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.swymall.Utils;

import java.util.ArrayList;

public class GroceryItem implements Parcelable {
    private int Id;
    private double price;
    private String name;
    private String description;
    private String imageUrl;
    private String category;
    private int availableAmount;
    private int rate;
    private int userPoint;
    private int popularityPoint;
    //array list of the review class
    private ArrayList<Review> reviews;

    //at the time we are initializing our constructor rate, userPoint, popularityPoint, arrayList are zero hence hence there are no Review
    public GroceryItem(double price, String name, String description, String imageUrl, String category, int availableAmount) {
        this.Id = Utils.getID();
        this.price = price;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.category = category;
        this.availableAmount = availableAmount;
        //manually define some values for the last four items
        this.rate = 0;
        this.userPoint = 0;
        this.popularityPoint = 0;
        //for the reviews initialize the arrayList
        reviews = new ArrayList<>();
    }

    protected GroceryItem(Parcel in) {
        Id = in.readInt();
        price = in.readDouble();
        name = in.readString();
        description = in.readString();
        imageUrl = in.readString();
        category = in.readString();
        availableAmount = in.readInt();
        rate = in.readInt();
        userPoint = in.readInt();
        popularityPoint = in.readInt();
    }

    public static final Creator<GroceryItem> CREATOR = new Creator<GroceryItem>() {
        @Override
        public GroceryItem createFromParcel(Parcel in) {
            return new GroceryItem(in);
        }

        @Override
        public GroceryItem[] newArray(int size) {
            return new GroceryItem[size];
        }
    };

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getPopularityPoint() {
        return popularityPoint;
    }

    public void setPopularityPoint(int popularityPoint) {
        this.popularityPoint = popularityPoint;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "GroceryItem{" +
                "Id=" + Id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", category='" + category + '\'' +
                ", availableAmount=" + availableAmount +
                ", rate=" + rate +
                ", userPoint=" + userPoint +
                ", popularityPoint=" + popularityPoint +
                ", reviews=" + reviews +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeDouble(price);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageUrl);
        dest.writeString(category);
        dest.writeInt(availableAmount);
        dest.writeInt(rate);
        dest.writeInt(userPoint);
        dest.writeInt(popularityPoint);
    }
}
