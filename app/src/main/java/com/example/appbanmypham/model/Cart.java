package com.example.appbanmypham.model;

public class Cart {
    long documentID;
    String idsanpham;
    String username;

    int soluong;
    Item item;

    boolean check;


    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public Cart() {
    }

    public String getIdsanpham() {
        return idsanpham;
    }

    public void setIdsanpham(String idsanpham) {
        this.idsanpham = idsanpham;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }


    public long getDocumentID() {
        return documentID;
    }

    public void setDocumentID(long documentID) {
        this.documentID = documentID;
    }
}
