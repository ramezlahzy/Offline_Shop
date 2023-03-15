package com.example.offlineshopmain.backend.UsedClass;

import android.content.Context;

import com.example.offlineshopmain.backend.NewAllData;

import java.util.ArrayList;

public class Abstract_Category {
    private String id;
    private String name;
    private ArrayList<String> All_Products_Id;
    private String photoURL;

    public Abstract_Category() {
    }

    public Abstract_Category(String name, ArrayList<String> all_Products_Id, String photoURL) {
        this.name = name;
        All_Products_Id = all_Products_Id;
        this.photoURL = photoURL;
    }
    public void addToFireStore(Context context){
        NewAllData.addAbstract_Category(this,context);
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

    public ArrayList<String> getAll_Products_Id() {
        return All_Products_Id;
    }

    public void setAll_Products_Id(ArrayList<String> all_Products_Id) {
        All_Products_Id = all_Products_Id;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }
}
