package com.example.mojprojekat.tools;

import com.example.mojprojekat.model.Contact;
import com.example.mojprojekat.model.Message;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by milossimic on 3/21/16.
 */
public class Mokap {

    private static List<Contact> contacts=new ArrayList<Contact>();
    private static Contact c1=new Contact(1,"Mika","Mikic","mika@gmail.com");
    private static Contact c2=new Contact(2,"Zika","Zikic","mika@gmail.com");
    private static Contact c3=new Contact(3,"Jova","Jovic","mika@gmail.com");
    private static List<Message> messages= new ArrayList<Message>();
    public static List<Message> getMesagges() throws ParseException {
        ArrayList<Message> messages = new ArrayList<Message>();

        Message u1 = new Message(1, c2.getEmail(), c1.getEmail(), "Nesto lepo", "bcc", DateUtil.formatTimeWithSecond(DateUtil.getNow()), "subject1", "content1");
        Message u2 = new Message(2, c2.getEmail(), c1.getEmail(), "Nesto lepse", "bcc", DateUtil.formatTimeWithSecond(DateUtil.getNow()), "subject2", "content2");
        Message u3 = new Message(3, c3.getEmail(), c1.getEmail(), "Nesto najlepse", "bcc", DateUtil.formatTimeWithSecond(DateUtil.getNow()), "subject3", "content3");

        messages.add(u1);
        messages.add(u2);
        messages.add(u3);

        return messages;

    }
}
