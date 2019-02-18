package ru.lukdiman.ejb.core.jmsmessage;

import javax.ejb.EJBLocalHome;
import javax.ejb.CreateException;

public interface JMSMessageLocalHome extends EJBLocalHome {
	public JMSMessageLocal create() throws CreateException;
}