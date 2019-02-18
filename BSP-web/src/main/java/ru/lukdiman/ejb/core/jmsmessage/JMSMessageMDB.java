package ru.lukdiman.ejb.core.jmsmessage;

import javax.ejb.EJBException;
import javax.ejb.MessageDrivenBean;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: Diman
 * Date: 17.03.13
 * Time: 1:55
 * To change this template use File | Settings | File Templates.
 */
public class JMSMessageMDB implements MessageDrivenBean, MessageListener {
    @Override
    public void ejbRemove() throws EJBException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setMessageDrivenContext(MessageDrivenContext messageDrivenContext) throws EJBException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void onMessage(Message message) {
        try {
            LOGGER.info(((TextMessage)message).getText());
        } catch (JMSException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private static final Logger LOGGER = Logger.getLogger(JMSMessageMDB.class.getName());
}
