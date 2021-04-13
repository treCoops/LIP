package com.chathra.littleitalypizzeria.Model;

public class ProductModel {

    private String description;
    private String id;
    private String name;
    private String price;
    private String imgUrl;

    public ProductModel(){

    }

    public ProductModel(String description, String id, String name, String price, String imgUrl){
        this.description = description;
        this.id = id;
        this.name = name;
        this.price = price;
        this.imgUrl = imgUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
