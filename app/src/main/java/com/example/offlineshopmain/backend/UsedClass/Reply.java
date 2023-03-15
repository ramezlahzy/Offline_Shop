package com.example.offlineshopmain.backend.UsedClass;

public class Reply {
    String textbody;
    String OwnerName;

    public Reply() {
    }

    public Reply(String textbody, String ownerName) {
        this.textbody = textbody;
        OwnerName = ownerName;
    }

    public String getTextbody() {
        return textbody;
    }

    public void setTextbody(String textbody) {
        this.textbody = textbody;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public void setOwnerName(String ownerName) {
        OwnerName = ownerName;
    }
}
