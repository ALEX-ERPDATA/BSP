package ru.gs.audit;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSConsumer;
import javax.jms.JMSException;

public final class AuditEngine {
    private static AuditEngine instance ;
    private static boolean start ;
    private static JMSConsumer auditConsumer;
    private final int uid = new Random().nextInt(100);
    
    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 1414; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio
    
    private AuditEngine () { 
             try {
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
                 auditConsumer = context.createConsumer(destination); // autoclosable
                
            } catch (JMSException ex) {
                
            }    
    }
    public static AuditEngine getInstance() {
        if (instance == null) { 
		synchronized(AuditEngine.class) { 
			if (instance == null)  instance = new AuditEngine(); 
		}	 
	}         
        return instance;
    }
    
    public void start() {          
        start = true;
        while (start=true) {
               String receivedMessage = auditConsumer.receiveBody(String.class, 3000); // in ms or 3 seconds
               System.out.println("==Audit Receive message:\n" + receivedMessage );
        }    
    }
    public void stop() {
        System.out.println("==in stop" + " uid= " + uid );
        start=false;
    }  
    public int getUid() {
        return uid;
    }
        
}
    

