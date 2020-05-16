package com.example.mojprojekat.model;

public class Message {
    private long id;
    private String from;
    private String to;
    private String cc;
    private String bcc;
    private String dateTime;
    private String subject;
    private String content;

    public Message(){
        this.id=0;
        this.from="";
        this.to="";
        this.cc="";
        this.bcc="";
        this.dateTime="";
        this.subject="";
        this.content="";
    }

    public Message(long id, String from, String to,String cc,String bcc,String dateTime,String subject,String content) {
        this.id = id;
        this.from=from;
        this.to=to;
        this.cc=cc;
        this.bcc=bcc;
        this.dateTime=dateTime;
        this.subject=subject;
        this.content=content;
    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getName() {
        return id;
    }

    public void setName(int id) {
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

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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

}
