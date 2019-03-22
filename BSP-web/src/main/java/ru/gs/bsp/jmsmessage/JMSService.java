package ru.gs.bsp.jmsmessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.UUID;
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
    
    private Destination destinationJour ;
    private JMSProducer producerJour ;
       
    private static final String HOST =         "ARM2"; // Host name or IP address    
    private static final int PORT =            2500; // Listener port for your queue manager
    private static final String CHANNEL =      "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR =         "QM_IM"; // Queue manager name   
    private static final String JOURNAL_OUT =  "REM.BSP.TO.JOURNAL"; // Queue that the application
    private static final String WH_OUT =       "BSP.TO.WH"; // Queue that the application
    private static final String WH_IN =        "WH.TO.BSP"; // Queue that the application
    private static final String QUEUE_CURR =   "BSP.CURRENCY"; // Queue that the application
    
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
            ///WH
            destinationOut = context.createQueue("queue:///" + WH_OUT);
            producer = context.createProducer(); // autoclosable
            
            destinationIn = context.createQueue("queue:///" + WH_IN);
            consumer = contAsync.createConsumer(destinationIn);
            
            //CURR
            Destination destinationCurr = context.createQueue("queue:///" + QUEUE_CURR);
            JMSConsumer consumerCurr = contAsync.createConsumer(destinationCurr);   
            
            ///Journal
            destinationJour = context.createQueue("queue:///" + JOURNAL_OUT);
            producerJour = context.createProducer(); 
            
            
            //Create Listeners for  receiving messages
            consumer.setMessageListener(new InnerMessageListener("==BSP Consumer from WH"));     
            consumerCurr.setMessageListener(new InnerMessageListener("==BSP Consumer from CURR"));             
            
             System.out.println("== BSP JmsService's been initialization");
             
        } catch (JMSException ex) {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JMSService getInstatnce() {      
        return SERVICE;
    }
    
    public String sendMessage(String mess) throws JMSException {
             
        //если VIP , то повышенный приоритет 
        if (mess.equals("111") ) {
            int priority = 8;
            producer.setPriority(priority);
            System.out.println("==BSP change priority to " + priority);            
        }
                
        // set Message Descriptor headers (MQMD)  
        TextMessage message = context.createTextMessage(mess);
        
        message.setJMSReplyTo(destinationIn);
        message.setJMSMessageID(UUID.randomUUID().toString());
                
        // устанавливаю TTL - не заработало
        /*java.util.Date date = new java.util.Date();
        long expiration = date.getTime() + 60000; //10 мин
        message.setJMSExpiration(expiration);
        */
        
        //send message synchron
        producer.send(destinationOut, message);
               
        System.out.println("==BSP Producer sent message:\n" + message);
        String messID = message.getJMSMessageID();
        MessagesStorage.getInstance().addRequestID(messID); 
        
        // запишем в Journal инфо о факте отправке сообщения
       producerJour.send(destinationJour, "SEND REQUEST_TO_WH: "+message.getBody(String.class));
        
        return messID;
      
    }
      public JMSProducer getProducer () {
        return producer;
    }
    
    public Destination getDestinationIn () {
        return destinationOut;
    }   
}
