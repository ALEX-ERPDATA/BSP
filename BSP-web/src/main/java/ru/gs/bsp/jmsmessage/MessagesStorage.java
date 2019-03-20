package ru.gs.bsp.jmsmessage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.jms.JMSException;
import javax.jms.Message;

public final class MessagesStorage {

    private static final MessagesStorage STORAGE = new MessagesStorage();
    private final Map<String,String> map = new HashMap();
     
    private MessagesStorage() { }
    
    public static MessagesStorage getInstance() {
        return STORAGE;
    }
    
    protected void addRequestID(String requestID) {
        map.put(requestID,null);
        
        
    }
    
    protected void putResponceMessage(Message mess) throws JMSException {           
        
       System.out.println(" == mess for replace = " + mess.getBody(String.class));   
        
       String correlId = mess.getJMSCorrelationID();
       
       System.out.println("== HashMap before remove : ");
       Set entrySet2 = map.entrySet(); 
       Iterator it2 = entrySet2.iterator();    
       while(it2.hasNext()){
          Map.Entry me = (Map.Entry)it2.next();
          System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }
              
       map.remove(correlId);
       
       System.out.println("== HashMap after remove : ");
       Set entrySet3 = map.entrySet(); 
       Iterator it3= entrySet3.iterator();
       while(it3.hasNext()){
          Map.Entry me = (Map.Entry)it3.next();
          System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }       
       
       map.put(correlId, mess.getBody(String.class));
       //map.replace(mess.getJMSCorrelationID(),mess);
       
       System.out.println("== HashMap after put : ");
       Set entrySet = map.entrySet(); 
       Iterator it = entrySet.iterator();    
       while(it.hasNext()){
          Map.Entry me = (Map.Entry)it.next();
          System.out.println("== Key is: "+me.getKey() + " value is: "+me.getValue());   
       }
    }
       
    public String getResponce(String requestID) {
        
       //Set entrySet = map.entrySet();
       //Iterator it = entrySet.iterator();            
       
       return map.get(requestID);      
    }
}



