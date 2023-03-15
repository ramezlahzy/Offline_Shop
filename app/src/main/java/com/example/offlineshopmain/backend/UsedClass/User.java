package com.example.offlineshopmain.backend.UsedClass;

import java.util.List;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private List<String> watched_list_ids;
    private List<String> fav_Products_ids;
    private List<String> fav_Shops_ids;
    private String imageURL;
    private double longitude;
    private double latitude;

    public User() {
    }

    public User(String name, String email, String password, List<String> watched_list_ids, List<String> fav_Products_ids, List<String> fav_Shops_ids, String imageURL, double longitude, double latitude) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.watched_list_ids = watched_list_ids;
        this.fav_Products_ids = fav_Products_ids;
        this.fav_Shops_ids = fav_Shops_ids;
        this.imageURL = imageURL;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public List<String> getWatched_list_ids() {
        return watched_list_ids;
    }

    public void setWatched_list_ids(List<String> watched_list_ids) {
        this.watched_list_ids = watched_list_ids;
    }

    public List<String> getFav_Products_ids() {
        return fav_Products_ids;
    }

    public void setFav_Products_ids(List<String> fav_Products_ids) {
        this.fav_Products_ids = fav_Products_ids;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public List<String> getFav_Shops_ids() {
        return fav_Shops_ids;
    }

    public void setFav_Shops_ids(List<String> fav_Shops_ids) {
        this.fav_Shops_ids = fav_Shops_ids;
    }
}
