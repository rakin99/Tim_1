package com.example.mojprojekat.model;

public class Account {

    private long id;
    private String smtp;
    private String pop3_imap;
    private String username;
    private String password;

    public Account(){
        this.id=0;
        this.smtp ="";
        this.pop3_imap ="";
        this.username ="";
        this.password="";
    };

    public Account(long id, String smtp, String pop3_imap, String username, String password){
        this.id=id;
        this.smtp = smtp;
        this.pop3_imap = pop3_imap;
        this.username = username;
        this.password=password;
    };

    public long getId(){
        return id;
    }

    public void setId(long id){
        this.id=id;
    }

    public String getSmtp() {
        return smtp;
    }

    public void setSmtp(String smtp) {
        this.smtp = smtp;
    }

    public String getPop3_imap() {
        return pop3_imap;
    }

    public void setPop3_imap(String pop3_imap) {
        this.pop3_imap = pop3_imap;
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

    public String format(){
        return "Id: "+id+" Smtp: "+ smtp +" pop3_imap: "+ pop3_imap +" username: "+ username +" Password: "+password;
    }
}
