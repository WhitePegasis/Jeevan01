package com.example.jeevan.Models;

public class Users {
    String dp,username,mail,password,userid,lastmessage,type,docStatus, RequestSent;



    public Users(String dp, String username, String mail, String password, String userid, String lastmessage, String type, String status) {
        this.dp = dp;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userid = userid;
        this.lastmessage = lastmessage;
        this.type=type;
        this.docStatus=status;
    }

    public Users(String username, String mail, String password,String type,String RequestSent) {
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.type=type;
        this.RequestSent=RequestSent;
    }

    public Users(String docStatus){
        this.docStatus=docStatus;
    }
    public Users(){}

    public String getStatus() {
        return docStatus;
    }

    public void setStatus(String docStatus) {
        this.docStatus = docStatus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getUserid(String key) {
        return userid;
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
}
