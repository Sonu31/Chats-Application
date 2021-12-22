package com.example.whatsapp.Models;

public class MessageModle {

    String uId,message;
    Long timetemp;

    public MessageModle(String uId, String message, Long timetemp) {
        this.uId = uId;
        this.message = message;
        this.timetemp = timetemp;
    }

    public MessageModle(String uId, String message) {
        this.uId = uId;
        this.message = message;
    }

    public MessageModle() {}

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

    public Long getTimetemp() {
        return timetemp;
    }

    public void setTimetemp(Long timetemp) {
        this.timetemp = timetemp;
    }
}
