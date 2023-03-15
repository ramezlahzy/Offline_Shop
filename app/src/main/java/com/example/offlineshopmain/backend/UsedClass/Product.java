package com.example.offlineshopmain.backend.UsedClass;

import android.content.Context;

import com.example.offlineshopmain.backend.NewAllData;

import java.util.List;

public class Product {
    private String id;
    private String id_Owner_Shop;
    private String product_name;
    private String product_Kind;
    private String describtion;
    private String abstract_Category_Id;
    private int popularity_Point;
    private int userPoint;
    private int price;
    private String imageURL;
    private List<String> likedusersids;
    private List<String> dislikedusersids;
    private List<String> watched_Users_id;

    public Product() {
    }

    public Product(String product_name, String product_Kind, String describtion, String abstract_Category_Id, int popularity_Point, int userPoint, int price, String imageURL, List<String> likedusersids, List<String> dislikedusersids,String id_Owner_Shop
    ,List<String> watched_Users_id) {
        this.product_name = product_name;
        this.product_Kind = product_Kind;
        this.describtion = describtion;
        this.abstract_Category_Id = abstract_Category_Id;
        this.popularity_Point = popularity_Point;
        this.userPoint = userPoint;
        this.price = price;
        this.imageURL = imageURL;
        this.likedusersids = likedusersids;
        this.dislikedusersids = dislikedusersids;
        this.id_Owner_Shop=id_Owner_Shop;
        this.watched_Users_id=watched_Users_id;
    }

    public List<String> getWatched_Users_id() {
        return watched_Users_id;
    }

    public void setWatched_Users_id(List<String> watched_Users_id) {
        this.watched_Users_id = watched_Users_id;
    }

    public String getId_Owner_Shop() {
        return id_Owner_Shop;
    }

    public void setId_Owner_Shop(String id_Owner_Shop) {
        this.id_Owner_Shop = id_Owner_Shop;
    }

    public void addToFireStore(Context context){
        NewAllData.addProduct(this,context);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getProduct_Kind() {
        return product_Kind;
    }

    public void setProduct_Kind(String product_Kind) {
        this.product_Kind = product_Kind;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public String getAbstract_Category_Id() {
        return abstract_Category_Id;
    }

    public void setAbstract_Category_Id(String abstract_Category_Id) {
        this.abstract_Category_Id = abstract_Category_Id;
    }

    public int getPopularity_Point() {
        return popularity_Point;
    }

    public void setPopularity_Point(int popularity_Point) {
        this.popularity_Point = popularity_Point;
    }

    public int getUserPoint() {
        return userPoint;
    }

    public void setUserPoint(int userPoint) {
        this.userPoint = userPoint;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public List<String> getLikedusersids() {
        return likedusersids;
    }

    public void setLikedusersids(List<String> likedusersids) {
        this.likedusersids = likedusersids;
    }

    public List<String> getDislikedusersids() {
        return dislikedusersids;
    }

    public void setDislikedusersids(List<String> dislikedusersids) {
        this.dislikedusersids = dislikedusersids;
    }

}
