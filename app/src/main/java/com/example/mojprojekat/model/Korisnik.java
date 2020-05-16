package com.example.mojprojekat.model;

public class Korisnik {

    private long id;
    private String first;
    private String last;
    private String display;
    private String email;
    private String password;

    public Korisnik(){
        this.id=0;
        this.first="";
        this.last="";
        this.display="";
        this.email="";
        this.password="";
    };

    public Korisnik(long id,String first,String last, String display,String email,String password){
        this.id=id;
        this.first=first;
        this.last=last;
        this.display=display;
        this.email=email;
        this.password=password;
    };

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String format(){
        return "Id: "+id+" First: "+first+" Last: "+last+" Display: "+display+" Email: "+email+" Password: "+password;
    }
}
