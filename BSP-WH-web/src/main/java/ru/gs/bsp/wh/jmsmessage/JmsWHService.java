package ru.gs.bsp.wh.jmsmessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSConsumer;

public class JmsWHService { 

    private final static JmsWHService SERVICE = new JmsWHService();  
    
    private static JMSProducer producer;
    private static JMSConsumer consumer;

    private static Destination destinationOut ;
    private static Destination destinationIn ;
     
    private static final String HOST =         "ARM2"; // Host name or IP address
    private static final int PORT =            3000; // Listener port for your queue manager
    private static final String CHANNEL =      "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR =         "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER =     "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_OUT =    "HOME.TO.ES"; // Queue that the application
    private static final String QUEUE_IN =     "ES.TO.HOME"; // Queue that the application
    
    //JMS 2.0 - control COnnection and Session on side WAS  throught using Context
    private JmsWHService()   {             
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
            cf.setStringProperty(WMQConstants.USERID, APP_USER);
            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);  
            
            JMSContext context = cf.createContext();
            
            //Create Destinations
            //destinationOut = context.createQueue("queue:///" + QUEUE_OUT);
            //producer = context.createProducer(); // autoclosable
            
            destinationIn = context.createQueue("queue:///" + QUEUE_OUT);
            consumer = context.createConsumer(destinationIn);
            
            
        } catch (JMSException ex) {
            Logger.getLogger(JmsWHService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static JmsWHService getInstatnce() {      
        return SERVICE;
    }
    
    public boolean processMessage() {
        
        boolean answer = false;
        //send message             
        //producer.send(destinationOut, message);
        //answer = true;
        //System.out.println("==Sent message:\n" + message);
        
        
        //receive message
        String body = consumer.receiveBody(String.class,3000);
        System.out.println("==WH Receive message:\n" + body);
        
        
        return answer;
      
    }
   
}
