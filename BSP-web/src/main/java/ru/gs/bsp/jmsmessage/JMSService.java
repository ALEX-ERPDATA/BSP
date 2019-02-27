package ru.gs.bsp.jmsmessage;

import com.ibm.msg.client.jms.JmsConnectionFactory;
import com.ibm.msg.client.jms.JmsFactoryFactory;
import com.ibm.msg.client.wmq.WMQConstants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSProducer;
import javax.jms.JMSException;

public class JMSService {

    private static JMSService SERVICE = null;

    private static final String HOST = "ARM2"; // Host name or IP address
    private static final int PORT = 1414; // Listener port for your queue manager
    private static final String CHANNEL = "SYSTEM.ADMIN.SVRCONN"; // Channel name
    private static final String QMGR = "HABR_QUEUE_MANAGER"; // Queue manager name
    private static final String APP_USER = "WAS_USER1"; // User name that application uses to connect to MQ
    private static final String APP_PASSWORD = "WAS_USER1"; // Password that the application uses to connect to MQ
    private static final String QUEUE_NAME = "HOME.TO.ES"; // Queue that the applicatio

    private JMSService() {
    }

    public static JMSService getInstatnce() {
        if (SERVICE == null) {
            SERVICE = new JMSService();
        }
        return SERVICE;
    }

    public boolean processMessage(String message) {

        boolean answer = true;

        try {
            // Create a connection factory
            JmsFactoryFactory ff = JmsFactoryFactory.getInstance(WMQConstants.WMQ_PROVIDER);
            JmsConnectionFactory cf = ff.createConnectionFactory();
            System.out.println("== JMS Connection Factory s is " + cf.getClass().getName());

            cf.setStringProperty(WMQConstants.WMQ_HOST_NAME, HOST);
            cf.setIntProperty(WMQConstants.WMQ_PORT, PORT);
            cf.setStringProperty(WMQConstants.WMQ_CHANNEL, CHANNEL);
            cf.setIntProperty(WMQConstants.WMQ_CONNECTION_MODE, WMQConstants.WMQ_CM_CLIENT);
            cf.setStringProperty(WMQConstants.WMQ_QUEUE_MANAGER, QMGR);
            cf.setStringProperty(WMQConstants.WMQ_APPLICATIONNAME, "JmsPutGet (JMS)");
            cf.setBooleanProperty(WMQConstants.USER_AUTHENTICATION_MQCSP, true);
            cf.setStringProperty(WMQConstants.USERID, APP_USER);
            cf.setStringProperty(WMQConstants.PASSWORD, APP_PASSWORD);

            
            // Create JMS Destination
            JMSContext context = cf.createContext();
            Destination destination = context.createQueue("queue:///" + QUEUE_NAME);
           
            
            //Create Producer
            JMSProducer producer = context.createProducer();
           
            
           //send the message
            producer.send(destination, message);
            System.out.println("==Sent message:\n" + message);

           

        } catch (JMSException ex) {
            Logger.getLogger(JMSService.class.getName()).log(Level.SEVERE, null, ex);
            answer = false;

        }
        return answer;
    }

}
