package ru.lukdiman.ejb.core.jmsmessage;

import javax.ejb.EJBHome;
import javax.ejb.CreateException;
import java.rmi.RemoteException;

public interface JMSMessageHome extends EJBHome {
	public JMSMessage create() throws CreateException, RemoteException;
}