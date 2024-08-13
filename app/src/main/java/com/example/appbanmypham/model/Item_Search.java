package com.example.appbanmypham.model;

import java.io.Serializable;

public class Item_Search implements Serializable {
    private String id; // Thêm trường ID vào lớp Item

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    private String imageUrl;
    private String name;
    private String category;

    // Constructor mặc định không tham số
    public Item_Search() {
    }

    public Item_Search(String id, String imageUrl, String name, String category) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.category = category;
    }

    // Getter và setter
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImage(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}

