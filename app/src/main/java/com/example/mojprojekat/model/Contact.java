package com.example.mojprojekat.model;

import java.text.ParseException;
import java.util.GregorianCalendar;

public class Contact {
    private int id;
    private String first;
    private String last;
    private String photos;
    private String email;
    private String format;
    private Account account;

    private boolean unread;
    private GregorianCalendar dateTime;
    private boolean active;

    public Contact(int id, String first, String last, String email ,boolean unread,boolean active, GregorianCalendar dateTime, Account account) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
        this.unread=unread;
        this.active=active;
        this.dateTime=dateTime;
        this.account=account;
    }

    public Contact() {
        this.id = 0;
        this.first = "";
        this.last = "";
        this.email = "";
        this.unread=true;
        this.active=true;
        this.dateTime=new GregorianCalendar();
        this.account=new Account();
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

    public String getPhotos() {
        return photos;
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

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(GregorianCalendar date_Time) throws ParseException {
        this.dateTime = date_Time;
    }

    public Account getAccount() {return account; }

    public void setAccount(Account acc) throws ParseException{
        this.account = acc;
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
