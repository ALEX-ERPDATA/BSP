package ru.gs.bsp.jmsmessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.Message;

public final class MessagesStorage {

    private static final MessagesStorage STORAGE = new MessagesStorage();
    private static final Map<String,Message> map = new HashMap();
     
    private MessagesStorage() { }
    
    public static MessagesStorage getInstance() {
        return STORAGE;
    }
    
    protected void addRequestID(String requestID) {
        map.put(requestID,null);        
    }
    
    protected void putResponceMessage(Message mess) throws JMSException {           
       map.replace(mess.getJMSCorrelationID(),mess);
       
       Set entrySet = map.entrySet(); 
       Iterator it = entrySet.iterator();
       System.out.println("==HashMap Key-Value Pairs : ");
       while(it.hasNext()){
          Map.Entry me = (Map.Entry)it.next();
          System.out.println("Key is: "+me.getKey() + " value is: "+me.getValue());   
        }
    }
    public Message getResponceMessage(String requestID) {
        
       Set entrySet = map.entrySet();
       Iterator it = entrySet.iterator();            
       
       return map.get(requestID);      
    }
}



