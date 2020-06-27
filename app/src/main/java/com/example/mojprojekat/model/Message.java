package com.example.mojprojekat.model;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class Message {

    private long id;

    private String from;

    private String to;

    private String cc;

    private String bcc;

    private GregorianCalendar dateTime;

    private String subject;

    private String content;

    private boolean unread;

    private boolean active;

    private long idFolder;

    public Message(){
        this.id=0;
        this.from="";
        this.to="";
        this.cc="";
        this.bcc="";
        this.dateTime=new GregorianCalendar();
        this.subject="";
        this.content="";
        this.unread=true;
        this.active=true;
        this.idFolder=0;
    }

    public Message(long id, String from, String to, String cc, String bcc, GregorianCalendar dateTime, String subject, String content,boolean unread,boolean active) {
        this.id = id;
        this.from=from;
        this.to=to;
        this.cc=cc;
        this.bcc=bcc;
        this.dateTime=dateTime;
        this.subject=subject;
        this.content=content;
        this.unread=unread;
        this.active=active;
        this.idFolder=0;
    }

    public long getIdFolder() {
        return idFolder;
    }

    public void setIdFolder(long idFolder) {
        this.idFolder = idFolder;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(GregorianCalendar date_Time) throws ParseException {
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
