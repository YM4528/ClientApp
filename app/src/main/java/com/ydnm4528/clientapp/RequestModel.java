package com.ydnm4528.clientapp;

public class RequestModel {

    public String namee;
    public String imagelink;
    public String createdAt;

    public RequestModel(String namee, String imagelink, String createdAt) {
        this.namee = namee;
        this.imagelink = imagelink;
        this.createdAt = createdAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public RequestModel(String namee, String imagelink) {
        this.namee = namee;
        this.imagelink = imagelink;
    }

    public RequestModel() {
    }

    public String getNamee() {
        return namee;
    }

    public void setNamee(String namee) {
        this.namee = namee;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }
}
