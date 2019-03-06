package ru.gs.bsp.jmsmessage;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

 
public class InnerMessageListener implements MessageListener {
    private String consumerName;
  
    public InnerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }
 
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            String body = textMessage.getText();
            System.out.println(consumerName + " received " + ":\n"+ body);    
            
            // дальше логика пересылки ответа на html       
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
        
        
    }
 
}
