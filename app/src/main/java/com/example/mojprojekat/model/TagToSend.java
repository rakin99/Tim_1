package com.example.mojprojekat.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TagToSend implements Serializable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("dateModified")
    @Expose
    private String dateModified;

    public TagToSend() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateModified() {
        return dateModified;
    }

    public void setDateModified(String dateModified) {
        this.dateModified = dateModified;
    }

}
