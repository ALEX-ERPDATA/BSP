package ru.gs.bsp.servlets;

import ru.lukdiman.ejb.core.jmsmessage.JMSMessageHome;
import ru.lukdiman.ejb.core.jmsmessage.JMSMessage;

import javax.naming.InitialContext;
import javax.naming.Context;
import javax.rmi.PortableRemoteObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueConnection;
import javax.jms.QueueConnectionFactory;
import javax.jms.QueueSession;
import javax.jms.Session;
import javax.jms.TextMessage;
import ru.gs.bsp.jmsmessage.JMSService;

public class SendJMSMessageServlet extends HttpServlet {
    private static final String MESSAGE_PARAMETER_NAME = "message";
    private static final String MESSAGE_SENDING_SUCCESS = "Сообщение успешно отправлено";
    private static final String MESSAGE_SENDING_ERROR = "Сообщение отправлено с ошибкой";

    private JMSMessage jmsMessage = null;

    /*@Override
    public void init() throws ServletException {
        super.init();

        try {
            Context ctx = new InitialContext();
            Object objHome = ctx.lookup("java:comp/env/ejb/JMSMessageLocal");
            JMSMessageHome jmsMessageHome = (JMSMessageHome) PortableRemoteObject.narrow(objHome, JMSMessageHome.class);
            jmsMessage = jmsMessageHome.create();
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }*/

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/sendMessage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String message = request.getParameter(MESSAGE_PARAMETER_NAME);
       //Отправить в очередь
        JMSService.getInstatnce().processMessage(message);
        
       //request.setAttribute(MESSAGE_PARAMETER_NAME, (jmsMessage.processMessage(message)) ? MESSAGE_SENDING_SUCCESS : MESSAGE_SENDING_ERROR);
       // request.getRequestDispatcher("/viewMessage.jsp").forward(request, response);
    }
    
    
    /*@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String message = request.getParameter(MESSAGE_PARAMETER_NAME);
        request.setAttribute(MESSAGE_PARAMETER_NAME, (jmsMessage.processMessage(message)) ? MESSAGE_SENDING_SUCCESS : MESSAGE_SENDING_ERROR);
        request.getRequestDispatcher("/viewMessage.jsp").forward(request, response);
    }*/
    
    
    
}
