package ru.gs.printservice.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MakePrintFormServlet extends HttpServlet {
     protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            
        // Получаем значение параметр из запроса
        //String headerName = request.getParameter("header");
       
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<title>");
            out.println("Your order");
            out.println("</title>");
            out.println("<body>");
            out.println("<h2> Book A </h2>");
            out.println("1 unit");
            out.println("<h2> Book B </h2>");
            out.println("3 units");
            out.println("</body>");
            out.println("</html>");
        } finally { 
            out.close();
        }
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
