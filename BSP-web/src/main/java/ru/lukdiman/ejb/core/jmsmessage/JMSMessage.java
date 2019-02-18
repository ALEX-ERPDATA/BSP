package ru.lukdiman.ejb.core.jmsmessage;

import javax.ejb.EJBObject;
import java.rmi.RemoteException;

public interface JMSMessage extends EJBObject {
	public boolean processMessage(String message) throws RemoteException;
}