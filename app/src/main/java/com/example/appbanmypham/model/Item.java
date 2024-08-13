package com.example.appbanmypham.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Item implements Serializable {

    public static final int Type_MYPHAM1 =1;
    public static final int Type_MYPHAM2 =2;
    public static final int Type_MYPHAM3 =3;

    private String imageUrl;
    private String name;
    private int type;
    private double price;
    private String description;
    private String id; // Thêm trường ID vào lớp Item



    public Item() {
    }

    public Item(String id,String imageUrl, String name, int type, double price, String description) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.type = type;
        this.price = price;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // Getter và Setter cho ID
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public Map<String,Object> toMap() {
        HashMap<String,Object> result = new HashMap<>();
        result.put("name", name);
        result.put("imageUrl", imageUrl);
        result.put("price", price); // Thêm giá vào Map
        return result;
    }
}
