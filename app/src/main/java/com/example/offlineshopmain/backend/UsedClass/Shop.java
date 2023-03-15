package com.example.offlineshopmain.backend.UsedClass;

import java.util.List;

public class Shop extends User {

    private String appiontments;
    private String address;
    private String product_Kind;
    private String government;
    private String city;
    private String phone_Number;
    private List<String> productsId;
    private List<String> categories_User_Id;
    private List<String> liked_Users_Id;
    private List<String> disliked_Users_Id;
    private List<String> followers_ids;
    private double rate;

    public Shop() {
        super();
    }

    public Shop(String appiontments, String name, String address, String product_Kind, String government, String city, String phone_Number, List<String> productsId, List<String> categories_User_Id, List<String> liked_Users_Id, List<String> disliked_Users_Id, String imageURL, double longitude, double latitude
            , String email, String password, List<String> watched_list_ids, List<String> fav, List<String> fav_shops,List<String> followers_id, double rate) {
        super(name, email, password, watched_list_ids, fav, fav_shops, imageURL, longitude, latitude);
        this.appiontments = appiontments;
        this.address = address;
        this.product_Kind = product_Kind;
        this.government = government;
        this.city = city;
        this.phone_Number = phone_Number;
        this.productsId = productsId;
        this.categories_User_Id = categories_User_Id;
        this.liked_Users_Id = liked_Users_Id;
        this.disliked_Users_Id = disliked_Users_Id;
        this.rate = rate;
        this.followers_ids =followers_id;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getAppiontments() {
        return appiontments;
    }

    public void setAppiontments(String appiontments) {
        this.appiontments = appiontments;
    }

    public List<String> getLiked_Users_Id() {
        return liked_Users_Id;
    }

    public void setLiked_Users_Id(List<String> liked_Users_Id) {
        this.liked_Users_Id = liked_Users_Id;
    }

    public List<String> getDisliked_Users_Id() {
        return disliked_Users_Id;
    }

    public void setDisliked_Users_Id(List<String> disliked_Users_Id) {
        this.disliked_Users_Id = disliked_Users_Id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProduct_Kind() {
        return product_Kind;
    }

    public void setProduct_Kind(String product_Kind) {
        this.product_Kind = product_Kind;
    }

    public String getGovernment() {
        return government;
    }

    public void setGovernment(String government) {
        this.government = government;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone_Number() {
        return phone_Number;
    }

    public void setPhone_Number(String phone_Number) {
        this.phone_Number = phone_Number;
    }

    public List<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<String> productsId) {
        this.productsId = productsId;
    }

    public List<String> getCategories_User_Id() {
        return categories_User_Id;
    }

    public void setCategories_User_Id(List<String> categories_User_Id) {
        this.categories_User_Id = categories_User_Id;
    }

    public List<String> getFollowers_ids() {
        return followers_ids;
    }

    public void setFollowers_ids(List<String> followers_ids) {
        this.followers_ids = followers_ids;
    }

    //    public List<String> getLiked_Users_Id() {
//        return liked_Users_Id;
//    }
//
//    public void setLiked_Users_Id(List<String> liked_Users_Id) {
//        this.liked_Users_Id = liked_Users_Id;
//    }
//
//    public List<String> getDisliked_Users_Id() {
//        return disliked_Users_Id;
//    }
//
//    public void setDisliked_Users_Id(List<String> disliked_Users_Id) {
//        this.disliked_Users_Id = disliked_Users_Id;
//    }
//
//    public String getImageURL() {
//        return imageURL;
//    }
//
//    public void setImageURL(String imageURL) {
//        this.imageURL = imageURL;
//    }


}
