package com.example.jeevan.Models;

//model class for user fragment which will show doctors
public class docModel {
    String dp,username,mail,password,userid,lastmessage,type,docStatus,sendRequest,viewDetails,chat;

    public docModel(String dp, String username, String mail, String password, String userid,
                    String lastmessage, String type, String docStatus, String sendRequest, String viewDetails, String chat) {
        this.dp = dp;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
        this.type = type;
        this.docStatus = docStatus;
        this.sendRequest = sendRequest;
        this.viewDetails = viewDetails;
        this.chat = chat;
    }

    public docModel(String username, String mail, String password,String type) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.type=type;
    }

    public String getSendRequest() {
        return sendRequest;
    }

    public void setSendRequest(String sendRequest) {
        this.sendRequest = sendRequest;
    }

    public String getViewDetails() {
        return viewDetails;
    }

    public void setViewDetails(String viewDetails) {
        this.viewDetails = viewDetails;
    }

    public String getChat() {
        return chat;
    }

    public void setChat(String chat) {
        this.chat = chat;
    }

    public docModel(String docStatus){
        this.docStatus=docStatus;
    }
    public docModel(){}

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDocStatus() {
        return docStatus;
    }

    public void setDocStatus(String docStatus) {
        this.docStatus = docStatus;
    }
}
