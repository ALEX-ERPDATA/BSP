package ru.gs.bsp.jmsmessage;


import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

 
public class InnerMessageListener implements MessageListener {
    private String consumerName;
  
    public InnerMessageListener(String consumerName) {
        this.consumerName = consumerName;
    }
    
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage) message;
        try {
            // Сообщение получено
            String body = textMessage.getText();
            System.out.println(consumerName + " received " + body); 
            MessagesStorage.getInstance().putResponceMessage(message);
              
              
            
        } catch (JMSException e) {
            e.printStackTrace();
        }
        
        
        
    }
 
}
