package ru.gs.bsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LogoutServlet extends HttpServlet {

    public static final String PARAMETER_SESSION = "paremeterSession";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // разрываем сессию
        HttpSession session = request.getSession(false);
        session.invalidate();
       
        //добавить атрибут relogin
        session = request.getSession(true);
        session.setAttribute("RELOGIN", "true");
        
        //response.setStatus(((HttpServletResponse) response).SC_UNAUTHORIZED); 
        //response.setHeader("WWW-Authenticate","Basic realm=\"realm\"");     
        response.sendRedirect("../index.jsp");
                /*   
         
        <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Logout</title>
</head>
<body>
<% session.invalidate(); %>
<p>You have been successfully logout</p>
</body>
</html>    
          
         */   
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

