package com.example.progetto;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Class which represents every card item with its information (image, place, data, price, description)
 */
@Entity(tableName = "wardrobe")
public class CardItem {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "item_id")
    private int id;

    @ColumnInfo(name = "item_image")
    private String imageResource;
    @ColumnInfo(name = "item_name")
    private String itemName;
    @ColumnInfo(name = "item_description")
    private String itemDescription;
    @ColumnInfo(name = "item_date")
    private String date;
    @ColumnInfo(name = "item_price")
    private String price;
    @ColumnInfo(name = "item_category")
    private String category;
    @ColumnInfo(name = "item_storelocation")
    private String location;


    @ColumnInfo(name = "item_wear_count")
    private int wear_count;


    public CardItem(String imageResource, String itemName, String itemDescription, String date,
                    String price, String category, String location, int wear_count) {
        this.imageResource = imageResource;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.date = date;
        this.price = price;
        this.category = category;
        this.location = location;
        this.wear_count = wear_count;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getDate() {
        return date;
    }

    public String getPrice() { return price; }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() { return category; }

    public String getLocation() { return location; }


    public int getWear_count() {
        return wear_count;
    }

    public void setWear_count(int wear_count) {
        this.wear_count = wear_count;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setLocation(String location) { this.location = location; }
}
