package com.example.mojprojekat.tools;

import com.example.mojprojekat.model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by milossimic on 3/21/16.
 */
public class Mokap {

    public static List<Message> getMesagges(){
        ArrayList<Message> messages = new ArrayList<Message>();
        Message u1 = new Message(1,"Mika","Pera","Nesto lepo","bcc",new Date(),"subject1","content1");
        Message u2 = new Message(2,"Zika","Pera","Nesto lepse","bcc",new Date(),"subject2","content2");
        Message u3 = new Message(3,"Jova","Pera","Nesto najlepse","bcc",new Date(),"subject3","content3");

        messages.add(u1);
        messages.add(u2);
        messages.add(u3);

        return messages;

    }

}
