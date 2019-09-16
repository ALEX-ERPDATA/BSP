package ru.gs.bsp.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
 
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
public class MainServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Forward (перенаправить) к странице /WEB-INF/views/loginView.jsp
        // (Пользователь не может прямо получить доступ  к страницам JSP расположенные в папке WEB-INF).
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("X-CURRENT-NAME", "SRW_USER");
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/mainPage.jsp");
 
        dispatcher.forward(request, response);
    }
    
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
               processRequest(request, response); 
    }

     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    
             processRequest(request, response); 
             
      // get Attribute - getAttribute() is for server-side usage only 
      // get Parameter - getParameter() возвращает параметры http-запроса. Они передаются от клиента к серверу. 
      // Например http://example.com/servlet?parameter=1. Может возвращать только String
        
        /*String user = request.getParameter("uname");
        String pw = request.getParameter("pass");
        RequestDispatcher dispatcher;              
        try  {	    
            
            if ( user.equals("ALEX") && pw.equals("123") )   {
                //запоминаем имя пользователя в сессию
                request.getSession(true).setAttribute("currentSessionUser",user); 
               // response.sendRedirect("WEB-INF/views/logged.jsp"); //logged-in page     
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/mainPage.jsp");
                dispatcher.forward(request, response);
            } 
            else  
                //response.sendRedirect("WEB-INF/views/loginInvalid.jsp"); //error page 
                dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginInvalid.jsp");
                dispatcher.forward(request, response);
        } 
	catch (Throwable theException) {
            System.out.println(theException); 
        } 
        */
        
    }                

 
        
}