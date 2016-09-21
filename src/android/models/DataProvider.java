package com.ramkumar.cordovaplugins.sms.models;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

/**
 * Created by Ramkumar Murugadoss on 11/09/2016
 */
public class DataProvider extends Observable {
    private static DataProvider ourInstance = new DataProvider();

    public static DataProvider getInstance() {
        return ourInstance;
    }

    private Map<String, Conversation> conversationMap;
    private List<Conversation> conversationList;

    private DataProvider() {
        conversationMap = new HashMap<String, Conversation>();
        conversationList = new ArrayList<Conversation>();

        // Put some sample data in list by default
        addMessage(new Message("My Message", "Sender", "Recipient", new Date()));
    }

    public void addMessage(Message message) {
        if(conversationMap.containsKey(message.getSender())) {
            conversationMap.get(message.getSender()).addMessage(message);
        } else {
            Conversation conversation = new Conversation(message.getSender());
            conversation.addMessage(message);
            conversationMap.put(message.getSender(), conversation);
            conversationList.add(conversation);
        }
        setChanged();
        notifyObservers();
    }

    public List<Conversation> getConversations() {
        return conversationList;
    }

    public Map<String, Conversation> getConversationMap() {
        return conversationMap;
    }
}
