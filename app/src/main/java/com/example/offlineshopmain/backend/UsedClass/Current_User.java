package com.example.offlineshopmain.backend.UsedClass;

import java.util.List;

public class Current_User extends User {

    private String first_name;
    private String last_name;
    private String age;
    private String city;
    private String phone;


    public Current_User() {
    }


    public Current_User(String first_name, String last_name, String age, String city, List<String> watched_list_ids, String imageURL, boolean female, double longitude, double latitude, String password, String email, String phone
            , List<String> fav, List<String> fav_shops) {
        super(first_name + " " + last_name, email, password, watched_list_ids, fav, fav_shops, imageURL, longitude, latitude);
        this.first_name = first_name;
        this.last_name = last_name;
        this.age = age;
        this.city = city;
        this.phone = phone;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
