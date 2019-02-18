package ru.gs.bsp.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(urlPatterns = { "/annotationExample", "/annExample" })
public class InfoServlet extends HttpServlet {

    public static final String PARAMETER_SESSION = "FIRST_NAME";
   
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // добавляем хидеры
        response.setContentType("text/html;charset=UTF-8");
        // Получаем параметры инициализации сервлета
        String paramVal = getInitServletParam(request,"myParam");    
       
        
        // Получаем значение параметр из запроса
        String headerName = request.getParameter("header");
       

        PrintWriter out = response.getWriter();
        try {
            out.println("<html>");
            out.println("<title>");
            out.println("Information Page");
            out.println("</title>");
            out.println("<body>");
            out.println("<h2> Header </h2>");
            out.println(headerName);
            out.println("<h2> Value </h2>");
            out.println(request.getHeader(headerName));
            out.println("</body>");
            out.println("</html>");
        } finally { 
            out.close();
        }
    } 
    
    private String getInitServletParam(HttpServletRequest request , String name) {
        String myContextParam =
        request.getSession()
               .getServletContext()
               .getInitParameter(name);
        return myContextParam;
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    } 
    
    
    public void init(ServletConfig config) throws ServletException {
        System.out.println("== init InfoServlet ");
        // ru.study.utils.UtilPrint.print("== use Util.jar");        
    }
    
    @Override
    public void destroy() {
        System.out.println("== destroy InfoServlet ");
//        System.out.println("== ");
    } 
}

