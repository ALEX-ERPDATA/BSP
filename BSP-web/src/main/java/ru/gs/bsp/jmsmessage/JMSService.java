package ru.gs.bsp.jmsmessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSConsumer;
import javax.jms.Destination;
import javax.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;



public class JMSService { 

    private final static JMSService SERVICE = new JMSService();  
    
    private static JMSProducer producer;
    private static JMSConsumer consumer;
    private static JMSContext  context ;
     

    private static Destination destinationOut ;
    private static Destination destinationIn ;
    
    private static final String HOST =         "ARM2"; // Host name or IP address    
    private static final int PORT =            2500; // Listener port for your queue manager
    private static final String CHANNEL =      "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR =         "QM_IM"; // Queue manager name   
    private static final String QUEUE_OUT =    "BSP.TO.WH"; // Queue that the application
    private static final String QUEUE_IN =     "WH.TO.BSP"; // Queue that the application
    
    //JMS 2.0 - control COnnection and Session on side WAS  throught using Context
    private JMSService()   {             
        try {
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();
            
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "BSP APP");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
   
            
            //JMSCC0033 требует разных контекстов для синхронного и асинхронного обменов
            context = cf.createContext();
            JMSContext contAsync = cf.createContext();
            
            
            //Create Destinations
            destinationOut = context.createQueue("queue:///" + QUEUE_OUT);
            
            producer = context.createProducer(); // autoclosable
            
            destinationIn = context.createQueue("queue:///" + QUEUE_IN);
            consumer = contAsync.createConsumer(destinationIn);
                        
            //Create Listener for queue-responce
            consumer.setMessageListener(new InnerMessageListener("==BSP Consumer"));         
            
            
        } catch (JMSException ex) {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JMSService getInstatnce() {      
        return SERVICE;
    }
    
    public boolean sendMessage(String mess) throws JMSException {
        boolean answer = false;
     
        //если VIP , то повышенный приоритет 
        if (mess.equals("111") ) {
            int priority = 8;
            producer.setPriority(priority);
            System.out.println("==BSP change priority to " + priority);            
        }
        
        
        // set message settings
        TextMessage message = context.createTextMessage(mess);
        message.setJMSReplyTo(destinationIn);
        
        // устанавливаю TTL - не заработало
        /*java.util.Date date = new java.util.Date();
        long expiration = date.getTime() + 60000; //10 мин
        message.setJMSExpiration(expiration);
        */
        
        //send message synchron
        producer.send(destinationOut, message);
             
        answer = true;
        System.out.println("==BSP Producer sent message:\n" + message);
      
        return answer;
      
    }
      public JMSProducer getProducer () {
        return producer;
    }
    
    public Destination getDestinationIn () {
        return destinationOut;
    }   
}
