package ru.gs.bsp.jmsmessage;

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

public class JMSService { 

    private final static JMSService SERVICE = new JMSService();  
    private static JmsConnectionFactory cf = null;
    //private static JMSProducer producer = null;
    //private static Destination destination = null;
     
    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 3000; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio
    
    //JMS 2.0 - control COnnection and Session on side WAS  throught using Context
    private JMSService()   {   
      
        try {
            //Create connection factory 
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            cf = ff.createConnectionFactory();
                        
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "BSP APP");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, APP_USER);
            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
                         
           
              // Create JMS context
            /*JMSContext context = cf.createContext();
            Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
            JMSProducer producer = context.createProducer();
            context.createTextMessage("==Your message  is " + message);
            //send the message            
            producer.send(destination, message);
            System.out.println("==Sent11 message:\n" + message)
                    */
            
           // Create Consumer
           // consumer = context.createConsumer(queue); // autoclosable                       
        } catch (JMSException ex) {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }        
    }

    public static JMSService getInstatnce() {      
        return SERVICE;
    }
    
    public boolean sendMessage(String message) {

            boolean answer = false;
                      
            JMSContext context = cf.createContext();
            
            //Create Destination         
            Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
            JMSProducer producer = context.createProducer(); // autoclosable
            
           //Send the message
           System.out.println("== Produser =" + producer);               
           System.out.println("== Message  =" + message);              
           
           producer.send(destination, message);           
           answer = true;
           System.out.println("==Sent message:\n" + message);              
        
        return answer;
    }

}
