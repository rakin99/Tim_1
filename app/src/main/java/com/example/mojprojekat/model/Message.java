package com.example.mojprojekat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;

public class Message implements Serializable {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("_from")
    @Expose
    private String from;

    @SerializedName("_to")
    @Expose
    private String to;

    @SerializedName("_cc")
    @Expose
    private String cc;

    @SerializedName("_bcc")
    @Expose
    private String bcc;

    @SerializedName("dateTime")
    @Expose
    private String dateTime;

    @SerializedName("subject")
    @Expose
    private String subject;

    @SerializedName("content")
    @Expose
    private String content;

    public Message(){
        this.id=0;
        this.from="";
        this.to="";
        this.cc="";
        this.bcc="";
        this.dateTime="";
        this.subject="";
        this.content="";
    }

    public Message(long id, String from, String to, String cc, String bcc, String dateTime, String subject, String content) {
        this.id = id;
        this.from=from;
        this.to=to;
        this.cc=cc;
        this.bcc=bcc;
        this.dateTime=dateTime;
        this.subject=subject;
        this.content=content;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return id;
    }

    public void setName(int id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String date_Time) throws ParseException {
        this.dateTime = date_Time;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
