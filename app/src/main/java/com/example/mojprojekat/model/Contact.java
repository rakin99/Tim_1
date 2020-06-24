package com.example.mojprojekat.model;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Contact {
    private int id;
    private String first;
    private String last;
    private List<Photo> photos=new ArrayList<Photo>();
    private List<Message> messages=new ArrayList<Message>();
    private String email;
    private String format;

    private boolean unread;
    private GregorianCalendar dateTime;
    private boolean active;

    public Contact(int id, String first, String last, String email ,boolean unread,boolean active, GregorianCalendar dateTime) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
        this.unread=unread;
        this.active=active;
        this.dateTime=dateTime;
    }

    public Contact() {
        this.id = 0;
        this.first = "";
        this.last = "";
        this.email = "";
        this.unread=true;
        this.active=true;
        this.dateTime=new GregorianCalendar();
    }

    public int getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public String getFormat() {
        return format;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(GregorianCalendar date_Time) throws ParseException {
        this.dateTime = date_Time;
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
