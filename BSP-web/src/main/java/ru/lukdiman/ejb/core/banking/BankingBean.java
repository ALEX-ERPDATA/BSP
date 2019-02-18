package ru.lukdiman.ejb.core.banking;

import javax.ejb.EJBException;
import javax.ejb.FinderException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;
import java.math.BigDecimal;
import java.rmi.RemoteException;

public class BankingBean implements SessionBean {
    private SessionContext sessionContext;

    public BigDecimal transfer(String accountIdFrom, String accountIdTo, BigDecimal amount) throws FinderException, RemoteException {
        return BigDecimal.ONE;
    }

    @Override
    public void ejbActivate() throws EJBException, RemoteException {

    }

    @Override
    public void ejbPassivate() throws EJBException, RemoteException {

    }

    @Override
    public void ejbRemove() throws EJBException, RemoteException {

    }

    @Override
    public void setSessionContext(SessionContext sessionContext) throws EJBException, RemoteException {
        this.sessionContext = sessionContext;
    }
}
