package ru.gs.bsp.jmsmessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Queue;
import javax.jms.QueueConnectionFactory;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException; 

public class JMSService { 

    private final static JMSService SERVICE = new JMSService();  
    
    private static QueueConnectionFactory cf = null; 
    private static Queue  destination = null;
    private static JMSContext context = null;
    /*
    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 3000; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio
    */
    private JMSService()   {   
        try {
            //try {
            
            //JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            //JmsConnectionFactory cf = ff.createConnectionFactory();
                     
            /*
            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "BSP APP");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, APP_USER);
            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);
            */             
      
            
            // Create JMS Destination        
            Context ctx = new InitialContext();
            cf = (QueueConnectionFactory) ctx.lookup("java:app/env/jms/QCF_HABR_QUEUE_MANAGER");
            destination = (Queue) ctx.lookup(        "java:app/env/jms/HOME.TO.ES"); 
            context = cf.createContext();
            
            // destination = context.createQueue("queue:///" + QUEUE_NAME);
        } catch (NamingException ex) {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        
    }

    public static JMSService getInstatnce() {      
        return SERVICE;
    }
    
    public boolean processMessage(String message) {

            boolean answer = false;
                       
            //Create Producer
            JMSProducer producer = context.createProducer();
                       
           //Send the message
            producer.send(destination, message);           
            answer = true;
            System.out.println("==Sent message:\n" + message);  
            
        
        return answer;
    }

}
