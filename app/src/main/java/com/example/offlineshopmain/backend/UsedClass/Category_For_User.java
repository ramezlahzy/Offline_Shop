package com.example.offlineshopmain.backend.UsedClass;

import android.content.Context;

import com.example.offlineshopmain.backend.NewAllData;

import java.util.List;

public class Category_For_User {
    private String id;
    private String id_For_Abstract;
    private List<String> productsId;
    private String name_Of_Category;
    private String imageURL;
    private String id_Owner_Shop;

    public Category_For_User() {
    }

    public Category_For_User(String id_For_Abstract, List<String> productsId, String name_Of_Category, String imageURL,String id_Owner_Shop) {
        this.id_For_Abstract = id_For_Abstract;
        this.productsId = productsId;
        this.name_Of_Category = name_Of_Category;
        this.imageURL = imageURL;
        this.id=name_Of_Category;
        this.id_Owner_Shop=id_Owner_Shop;
    }

    public String getId_Owner_Shop() {
        return id_Owner_Shop;
    }

    public void setId_Owner_Shop(String id_Owner_Shop) {
        this.id_Owner_Shop = id_Owner_Shop;
    }

    public String getId_For_Abstract() {
        return id_For_Abstract;
    }

    public void setId_For_Abstract(String id_For_Abstract) {
        this.id_For_Abstract = id_For_Abstract;
    }

    public void addToFireStore(Context context){
        NewAllData.addCategory_For_User(this,context);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getProductsId() {
        return productsId;
    }

    public void setProductsId(List<String> productsId) {
        this.productsId = productsId;
    }

    public String getName_Of_Category() {
        return name_Of_Category;
    }

    public void setName_Of_Category(String name_Of_Category) {
        this.name_Of_Category = name_Of_Category;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    @Override
    public String toString() {
        return "Category_For_User{" +
                "id='" + id + '\'' +
                ", id_For_Abstract='" + id_For_Abstract + '\'' +
                ", productsId=" + productsId +
                ", name_Of_Category='" + name_Of_Category + '\'' +
                ", imageURL='" + imageURL + '\'' +
                '}';
    }
}
