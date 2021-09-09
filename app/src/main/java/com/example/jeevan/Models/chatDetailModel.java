package com.example.jeevan.Models;

public class chatDetailModel {
    String uId,message,imageUrl;
    Long timestamp;

    public chatDetailModel(String uId, String message, Long timestamp) {
        this.uId = uId;
        this.message = message;
        this.timestamp = timestamp;
    }
    public chatDetailModel(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public chatDetailModel(String uId, String message,String image) {
        this.uId = uId;
        this.message = message;
        this.imageUrl=image;
    }
    public chatDetailModel(){}

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
