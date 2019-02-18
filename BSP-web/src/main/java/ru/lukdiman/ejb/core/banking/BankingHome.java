package ru.lukdiman.ejb.core.banking;

import javax.ejb.CreateException;
import javax.ejb.EJBHome;
import java.rmi.RemoteException;

public interface BankingHome extends EJBHome {
    public Banking create() throws CreateException, RemoteException;
}
