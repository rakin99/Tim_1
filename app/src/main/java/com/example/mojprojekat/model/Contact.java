package com.example.mojprojekat.model;

import java.text.ParseException;

public class Contact {
    private long id;
    private String first;
    private String last;
    private String photos;
    private String email;
    private String format;
    private String account;
    private boolean active;

    public Contact(long id, String first, String last, String email ,boolean active,  String account) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
        this.active=active;
        this.account=account;
    }

    public Contact() {
        this.id = 0;
        this.first = "";
        this.last = "";
        this.email = "";
        this.active=true;
        this.account= "";
    }

    public long getId() {
        return id;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getPhotos() {
        return photos;
    }

    public String getFormat() {
        return format;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public void setPhotos(String photos) {
        this.photos = photos;
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

    public String getAccount() {return account; }

    public void setAccount(String acc) throws ParseException{
        this.account = acc;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
