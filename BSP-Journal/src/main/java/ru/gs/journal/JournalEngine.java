package ru.gs.journal;

import com.ibm.jms.*;
import com.ibm.mq.jms.*;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.Session;

public class JournalEngine {
    private static final JournalEngine INSTANCE = new JournalEngine() ;
    private static boolean start ;
    private static JMSConsumer consumer;
    
    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 1414; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio
    
    //MQ Extention
    private JournalEngine () { 
                
        try {
            //create ConnectionFactory
            
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setQueueManager(QMGR);
            cf.setHostName(HOST);
            cf.setPort(PORT);
            cf.setChannel(CHANNEL);
                        
            JMSContext context = cf.createContext();
            MQQueue queue = new MQQueue(QUEUE_NAME);
            //queue.setPersistence(WMQConstants.WMQ_PER_PER);
    
            consumer = context.createConsumer(queue);              
            
        } catch (JMSException ex) {
            Logger.getLogger(JournalEngine.class.getName()).log(Level.SEVERE, null, ex);
        }   
        
    }
    
    // JMS Extention    
    /*private JournalEngine () { 
             try {
                //create ConnectionFactory
                JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
                JmsConnectionFactory cf = ff.createConnectionFactory();
                System.out.println("== Audit Conn Factory s is " + cf.getClass().getName());

                cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
                cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
                cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
                cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
                cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
                cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "Audit Rec");
                cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
                cf.setStringProperty(WMQConstants.USERID, APP_USER);
                cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

                // Create JMS Destination
                JMSContext context = cf.createContext();
                Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
                
                //Create Consumer
                 consumer = context.createConsumer(destination); // autoclosable
                
            } catch (JMSException ex) {
                
            }    
    }*/
    public static JournalEngine getInstance() {         
        return INSTANCE;
    }
    
    public void start() {          
        start = true;
        while (start==true) {
               String receivedMessage = consumer.receiveBody(String.class, 3000); // in ms or 3 seconds
               System.out.println("== Audit Receive message:\n" + receivedMessage );
        }    
    }
    public void stop() {
        start=false;
        System.out.println("== Audit has been stop " );
    }     
        
}
    

