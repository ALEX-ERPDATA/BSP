package ru.gs.bsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

public class XWelcomeServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        RequestDispatcher dispatcher; 
        String linkPage = null;
       
        // получаем ПАРАМЕТРЫ контекста приложения
        Enumeration<String> e = getServletContext().getInitParameterNames();  
        String str="";  
        while(e.hasMoreElements()){  
            str=e.nextElement();  
            System.out.println("=cont param "+str);  
        }

        //  получаем ПАРАМЕТРЫ инициализации конкретного Сервлета                
        Enumeration <String> initNames = this.getServletConfig().getInitParameterNames();
        while (initNames.hasMoreElements()) {           
            System.out.println("==init param " +  initNames.nextElement());
        }
        
        //  смотрит API работы с АТРИБУТАМИ  контекста
        ServletContext sc = request.getServletContext(); 
        sc.setAttribute("!!!_transport_attr_from_welcome", "id=123");
        Enumeration en11 = sc.getAttributeNames();
        while (en11.hasMoreElements()) {
               System.out.println("==атрибуты контекста " + en11.nextElement());
        }
        
        
        
        Enumeration en2 = request.getSession().getAttributeNames();
        while (en2.hasMoreElements()) {
               System.out.println("==атрибуты сессии " + en2.nextElement());
        }
        
        // forward на страницу
        //System.out.println("==количество параметров в web.xml " +  initNames.);
        linkPage = getServletConfig().getInitParameter("linkPage");
                                      
        //dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/firstPage.jsp");
        dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/views/"+linkPage);
        dispatcher.forward(request, response);       
               
    }
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
}

