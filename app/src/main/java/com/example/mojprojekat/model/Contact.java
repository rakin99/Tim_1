package com.example.mojprojekat.model;

import java.util.ArrayList;
import java.util.List;

public class Contact {
    private int id;
    private String first;
    private String last;
    private List<Photo> photos=new ArrayList<Photo>();
    private List<Message> messages=new ArrayList<Message>();
    private String email;
    private String format;

    public Contact(int id, String first, String last, String email) {
        this.id = id;
        this.first = first;
        this.last = last;
        this.email = email;
    }

    public Contact() {
        this.id = 0;
        this.first = "";
        this.last = "";
        this.email = "";
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
}
