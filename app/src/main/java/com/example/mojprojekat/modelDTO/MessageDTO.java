package com.example.mojprojekat.modelDTO;

import com.example.mojprojekat.model.Message;
import com.example.mojprojekat.tools.DateUtil;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.text.ParseException;

public class MessageDTO implements Serializable {

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

    @SerializedName("unread")
    @Expose
    private boolean unread;

    @SerializedName("active")
    @Expose
    private boolean active;

    public MessageDTO(){
        this.id=0;
        this.from="";
        this.to="";
        this.cc="";
        this.bcc="";
        this.dateTime="";
        this.subject="";
        this.content="";
        this.unread=true;
        this.active=true;
    }

    public MessageDTO(Message m) throws ParseException {
        this.id = m.getId();
        this.from=m.getFrom();
        this.to=m.getTo();
        this.cc=m.getCc();
        this.bcc=m.getBcc();
        this.dateTime= DateUtil.formatTimeWithSecond(m.getDateTime());
        this.subject=m.getSubject();
        this.content=m.getContent();
        this.active=true;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isUnread() {
        return unread;
    }

    public void setUnread(boolean unread) {
        this.unread = unread;
    }
}
