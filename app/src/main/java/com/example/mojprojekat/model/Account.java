package com.example.mojprojekat.model;

public class Account {

    private long id;
    private String smtpAddress;
    private String inServerAddress;
    private String username;
    private String password;
    private boolean active;

    public Account(){
        this.id=0;
        this.smtpAddress ="";
        this.inServerAddress ="";
        this.username ="";
        this.password="";
        this.active=true;
    };

    public Account(long id, String smtp, String pop3_imap, String username, String password,boolean active){
        this.id=id;
        this.smtpAddress = smtp;
        this.inServerAddress = pop3_imap;
        this.username = username;
        this.password=password;
        this.active=active;
    };

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getSmtpAddress() {
        return smtpAddress;
    }

    public void setSmtpAddress(String smtpAddress) {
        this.smtpAddress = smtpAddress;
    }

    public String getInServerAddress() {
        return inServerAddress;
    }

    public void setInServerAddress(String inServerAddress) {
        this.inServerAddress = inServerAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String format(){
        return "Id: "+id+" smtpAddress: "+ smtpAddress +" pop3_imap: "+ inServerAddress +" username: "+ username +" Password: "+password;
    }
}
