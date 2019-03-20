package ru.gs.bsp.jmsmessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.Message;

public final class MessagesStorage {

    private final static  MessagesStorage STORAGE = new MessagesStorage();
    private final Map<String,String> map = new HashMap();
     
    private MessagesStorage() { }
    
    public static MessagesStorage getInstance() {
        return STORAGE;
    }
    
    protected void addRequestID(String requestID) {
       map.put(requestID,null);
       
       System.out.println("== HashMap after add ReqId : ");
       Set<Map.Entry<String,String>> set = map.entrySet(); 
       for (Map.Entry<String,String> me : set) {
           System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }   
    }
    
    protected void putResponceMessage(Message mess) throws JMSException {           
        
       String correlId = mess.getJMSCorrelationID();
       
       System.out.println("== HashMap before put Responce : ");
       Set<Map.Entry<String,String>> set = map.entrySet(); 
       for (Map.Entry<String,String> me : set) {
           System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }
              
       map.put(correlId, mess.getBody(String.class));
              
       System.out.println("== HashMap after put Responce : ");
       Set<Map.Entry<String,String>> set2 = map.entrySet(); 
       for (Map.Entry<String,String> me : set2) {
           System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }
    }
       
    public String getMessageResponce(String requestID) {
        
       //Set entrySet = map.entrySet();
       //Iterator it = entrySet.iterator();            
       
       return map.get(requestID);      
    }
}



