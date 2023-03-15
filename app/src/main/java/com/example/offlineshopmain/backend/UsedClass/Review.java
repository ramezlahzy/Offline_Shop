package com.example.offlineshopmain.backend.UsedClass;

import java.util.List;

public class Review {
    String id;
    String id_Owner_User;
    String text_Review;
    List<String> replies;
    List<String> likedUsersId;
    String date;
    String id_Product;
    int likes;

    public Review() {
    }

    public Review(String id_Owner_User, String text_Review, List<String> replies, String date, String id_Product,int likes,List<String>likedUsersId) {
        this.id_Owner_User = id_Owner_User;
        this.text_Review = text_Review;
        this.replies = replies;
        this.date=date;
        this.id_Product=id_Product;
        this.likes=likes;
        this.likedUsersId=likedUsersId;
    }

    public int getLikes() {
        return likes;
    }

    public List<String> getLikedUsersId() {
        return likedUsersId;
    }

    public void setLikedUsersId(List<String> likedUsersId) {
        this.likedUsersId = likedUsersId;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public String getId_Product() {
        return id_Product;
    }

    public void setId_Product(String id_Product) {
        this.id_Product = id_Product;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId_Owner_User() {
        return id_Owner_User;
    }

    public void setId_Owner_User(String id_Owner_User) {
        this.id_Owner_User = id_Owner_User;
    }

    public String getText_Review() {
        return text_Review;
    }

    public void setText_Review(String text_Review) {
        this.text_Review = text_Review;
    }

    public List<String> getReplies() {
        return replies;
    }

    public void setReplies(List<String> replies) {
        this.replies = replies;
    }
}
