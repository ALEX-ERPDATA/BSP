package ru.gs.bsp.servlets;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
//import javax.naming.*;  
//import javax.jms.*; 

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class GetCurrencyRates  extends HttpServlet {
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // для реализации очереди
        /*
        try {   //Create and start connection  
            InitialContext ctx=new InitialContext();  
            QueueConnectionFactory f=(QueueConnectionFactory)ctx.lookup("OneConnectionFactory");  
            QueueConnection con=f.createQueueConnection();  
            con.start();  
            //2) create queue session  
            QueueSession ses=con.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);  
            //3) get the Queue object  
            Queue t=(Queue)ctx.lookup("MyQueue");  
            //4)create QueueSender object         
            QueueSender sender=ses.createSender(t);  
            //5) create TextMessage object  
            TextMessage msg=ses.createTextMessage();  
            msg.setText("This is my output message"); 
            System.out.println("==Message successfully sent.");  
            
            con.close();  
        }
        catch(Exception e){System.out.println(e);}  
        */ 
                
        //Object o  = getServletContext().getAttribute("!!!_transport_attr_from_welcome");
        //System.out.println("== transport_attr = " + o); 
        
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<title>");
            out.println("Currency  Exchange");
            out.println("</title>");
            out.println("<body>");
            out.println("<h2> 1 USD = 65 RUB </h2>");
            out.println("<h2> 1 EUR = 71 RUB </h2>");
            out.println("<h3>  Attribute from Welcome Servlet = </h3>");
            //out.print(o.toString());
            out.println("</body>");
            out.println("</html>");
        } finally { 
            out.close();
        }
        
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
  
    }
 
    // Когда пользователь вводит userName & password, и нажимает Submit.
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }    
        
        
   
    
 
}

