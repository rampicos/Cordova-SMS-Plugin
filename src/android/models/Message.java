package com.ramkumar.cordovaplugins.sms.models;

import java.util.Date;

/**
 * Created by Ramkumar Murugadoss on 11/09/2016
 */
public class Message {

    private String Content;
    private String Sender;
    private String Recipient;
    private Date Time;

    public Message(String content, String sender, String recipient, Date time) {
        Content = content;
        Sender = sender;
        Recipient = recipient;
        Time = time;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getSender() {
        return Sender;
    }

    public void setSender(String sender) {
        Sender = sender;
    }

    public String getRecipient() {
        return Recipient;
    }

    public void setRecipient(String recipient) {
        Recipient = recipient;
    }

    public Date getTime() {
        return Time;
    }

    public void setTime(Date time) {
        Time = time;
    }

    @Override
    public String toString() {
        return getContent() + "  -  " + getTime().toString();
    }
}
