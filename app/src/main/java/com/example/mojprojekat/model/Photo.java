package com.example.mojprojekat.model;

public class Photo {
    private int id;
    private String path;

    public Photo(){
        this.id=0;
        this.path="";
    }

    public Photo(int id, String path){
        this.id=id;
        this.path=path;
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
