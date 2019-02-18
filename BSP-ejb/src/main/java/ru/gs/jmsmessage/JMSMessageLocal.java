package ru.gs.jmsmessage;

import javax.ejb.EJBLocalObject;

public interface JMSMessageLocal extends EJBLocalObject {
	public String processMessage(String message);
} 