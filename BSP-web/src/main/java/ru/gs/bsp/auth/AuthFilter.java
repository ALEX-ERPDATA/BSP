package ru.gs.bsp.auth;

import java.io.IOException;
import static java.util.Collections.list;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import ru.gs.bsp.jmsmessage.JMSService;

public class AuthFilter implements Filter {

    private static FilterConfig filterConfig;
    RequestDispatcher dispatcher ;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        System.out.println(" == init " + filterConfig.getFilterName());
        // init JMS engine
        //вернуть после отладки
        System.out.println(" == init " + " JMS отключен");
        //JMSService.getInstatnce();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.print("= do Filter: Request received .....");

        try {

            boolean authorized = this.checkUserAutorization((HttpServletRequest) request,(HttpServletResponse) response);

            // проверяем авторизирован ли пользователь, 
            // отвечаем только на глобальный вопрос - пропускать запрос или нет.
            //если да - пропускаем запрос дальше
            
            if (authorized) {
                System.out.print("== Authorizired! Good Luck ");
                chain.doFilter(request, response);
                
            } // не прошел авторизацию 
            else if (response instanceof HttpServletResponse ) {
                    if ( request.getParameter("uname") == null) { // введи логин - впервые или повторно
                        dispatcher =
                        request.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");                    
                    } else { // до свидания
                         System.out.println("== NO Avtorization, 401");
                        // для FORM AUTH
                        ((HttpServletResponse) response).setStatus(((HttpServletResponse) response).SC_UNAUTHORIZED);
                        dispatcher =
                        request.getServletContext().getRequestDispatcher("/WEB-INF/views/loginInvalid.jsp");
                    }
                    
                    dispatcher.forward(request, response);
                
                // !! падает из-за 2x dispatcher.forward
                // нужна логика
                     
                
               // вернуть 401
                /// для Base Auth
                //((HttpServletResponse) response).setStatus(((HttpServletResponse) response).SC_UNAUTHORIZED);
                //((HttpServletResponse) response).setHeader("WWW-Authenticate", "Basic realm=\"realm\"");
                               
            } else {
                throw new ServletException(" something happens ");
            }

        } catch (IOException io) {
            System.out.println("IOException raised in Authentication Filter");
        }

        System.out.print("do Filter : Response dispatched ......");
    }

    private boolean checkUserAutorization(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
     
        HttpSession session = request.getSession();
        boolean result = false;
        
        if (session.getAttribute("RELOGIN") != null) {
             session.removeAttribute("RELOGIN");
             return false;
        }
        // проверяем наличие нашего атрибута в сессии ,  установленного при логине
        // или переданного нам логина/пароля для авторизации
        if (session != null) {
            //System.out.println("cессия новая =" + session.isNew());
            //System.out.println("=id session=" + session.getId());
            
            LoginBean lb = new LoginBean(request);
            
            if (lb.checkSession(request)) {      
                 result = true;
                 
            } else { // метки нет , передан ли  ли логин на форме
                        
               String user = request.getParameter("uname"); 
                if (user != null) {                  
                   // логин передан - проверяем логин/пароль.             
                   String auth_header = request.getHeader("Authorization");
                   if (auth_header!=null) {
                      // базовая авторизация
                       result = lb.checkLoginBaseAuth(auth_header);
                   } else  {
                       // FORM-авторизация
                       result = lb.checkLoginFormAuth();
                   }
                } else { //login не передан
                    result = false;  
                }
                
            }            
        } else {
            System.out.println("=cессия не найдена");
        }
        return result;
    }

    @Override
    public void destroy() {
            
    }

}
