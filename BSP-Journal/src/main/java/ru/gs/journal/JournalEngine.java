package ru.gs.journal;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

public final class  JournalEngine {
    private static final JournalEngine INSTANCE = new JournalEngine() ;
    private static boolean isStart ;
    // for JMS 2.0
     private static JMSConsumer consumer;
    
    // for JMS 1.1
    private static Connection connection ;
    private static Session session ;
    private static MessageConsumer messageConsumer;
    private static JmsConnectionFactory cf;
    
    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 3000; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio
    
    //  JMS Extention  ( both 1.1 and 2.0)
    private JournalEngine () { 
             try {
                              
                JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
                cf = ff.createConnectionFactory();
                System.out.println("== Jornal JMS  Conn Factory s is " + cf.getClass().getName());

                cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
                cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
                cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
                cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
                cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
                cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "Journal App");
                cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
                cf.setStringProperty(WMQConstants.USERID, APP_USER);
                cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);                                 
                               
            } catch (JMSException ex) {
                
            }    
    }
  
    //  JMS 2.0, MQ Extention - using MQQueueConnectionFactory and MQQueue
    /*  private JournalEngine () {                 
        try {
            //create ConnectionFactory            
            MQQueueConnectionFactory cf = new MQQueueConnectionFactory();
            System.out.println("== Journal MQ Conn Factory s is " + cf.getClass().getName());
            
            cf.setTransportType(WMQConstants.WMQ_CM_CLIENT);
            cf.setQueueManager(QMGR);
            cf.setHostName(HOST);
            cf.setPort(PORT);
            cf.setChannel(CHANNEL);
                        
            // Create Destination
            MQQueue queue = new MQQueue(QUEUE_NAME);
            //queue.setPersistence(WMQConstants.WMQ_PER_PER);
            
            
            JMSContext context = cf.createContext();
            consumer = context.createConsumer(queue);              
            
        } catch (JMSException ex) {
            Logger.getLogger(JournalEngine.class.getName()).log(Level.SEVERE, null, ex);
        }         
    }*/    
   
    public static JournalEngine getInstance() {         
        return INSTANCE;
    }    
   
    //JMS 1.1
    
    private void setDestination() {        
        try {
            //Create Destination
            Queue queue = session.createQueue(QUEUE_NAME);
            messageConsumer = session.createConsumer(queue);
            
        } catch (JMSException ex) {
            Logger.getLogger(JournalEngine.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void start() throws JMSException {          
        if (isStart==false) {        
            connection = cf.createConnection();         
            session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
            setDestination();
            
            connection.start();
            isStart=true;
            System.out.println("== Journal has been start " );                
            //sendMessage(); 
            while (isStart==true) {
                try {
                    TextMessage receivedMessage = (TextMessage) messageConsumer.receive(4000);// in ms or 4 seconds
                    System.out.println("== Journal Receive message:\n" + receivedMessage );
                } catch (JMSException ex) {
                    Logger.getLogger(JournalEngine.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
             System.out.println("== Journal's already runnig..." );
        }      
    }
    
    private void sendMessage() {
          while (isStart==true) {
              try {
                  TextMessage receivedMessage = (TextMessage) messageConsumer.receive(4000);// in ms or 4 seconds
                  System.out.println("== Journal Receive message:\n" + receivedMessage );
              } catch (JMSException ex) {
                  Logger.getLogger(JournalEngine.class.getName()).log(Level.SEVERE, null, ex);
              }
            }
    
    }
       public void stop() throws JMSException {
        isStart=false;
        messageConsumer.close();
        session.close();
        connection.close();
        
        System.out.println("== Journal has been stop " );        
    }
   

    //JMS 2.0 - control COnnection and Session on side WAS  throught using Context
    /*
    private void setDestination() {
         // Create JMS Destination
                JMSContext context = cf.createContext();
                Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
                
                //Create Consumer
                 consumer = context.createConsumer(destination); // autoclosable 
     }
    
     public void start() throws JMSException {          
        if (isStart==false) {        
            isStart=true;            
            setDestination();            
            System.out.println("== Journal has been start " );                
            
            while (isStart==true) {           
                TextMessage receivedMessage = (TextMessage) consumer.receive(4000);// in ms or 4 seconds
                System.out.println("== Journal Receive message:\n" + receivedMessage );
           
            }            
        } else {
             System.out.println("== Journal's already runnig..." );
        }  
   }
    public void stop() throws JMSException {
        isStart=false;       
        System.out.println("== Journal has been stop " );        
    }
  */
    
       
        
}
    

