package ru.lukdiman.ejb.core.banking;

import javax.ejb.EJBObject;
import javax.ejb.FinderException;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public interface Banking extends EJBObject {
    public BigDecimal transfer(String accountIdFrom, String accountIdTo, BigDecimal amount) throws FinderException, RemoteException;
}
