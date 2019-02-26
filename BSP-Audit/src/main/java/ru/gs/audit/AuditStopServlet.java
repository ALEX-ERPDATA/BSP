package ru.gs.audit;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuditStopServlet  extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    
        AuditEngine.getInstance().stop();
        
        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<title>");
            out.println("State");
            out.println("</title>");
            out.println("<body>");
            out.println("<h2> The Audit's stopping... </h2>");
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
