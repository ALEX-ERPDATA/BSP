package ru.gs.bsp.auth;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginBean {
    private HttpServletRequest request;
    private HttpSession session;
    
    public LoginBean(HttpServletRequest req) {
        this.request=req;
        session = req.getSession();
    }
    
    public boolean checkLoginBaseAuth (String auth_header) {
        boolean result = true;
        sun.misc.BASE64Decoder bs64dec = new sun.misc.BASE64Decoder();
        String NamePassword = null;
        
        // получим схему, с помощью которой передан пароль              
        StringTokenizer st = new StringTokenizer(auth_header, " ");
        String scheme = null;
        try {
          scheme = st.nextToken();
        } catch (NoSuchElementException e) {
              scheme = "";
              result = false;
        }
        try {
            if (scheme.equalsIgnoreCase("BASIC")) {
                    NamePassword = st.nextToken();
                    // раскодируем имя+пароль
                     NamePassword = new String(bs64dec.decodeBuffer(NamePassword));
                } else {
                    NamePassword = st.nextToken();
                }
        } catch (NoSuchElementException e) {
            NamePassword = "";
            result = false;
        } catch (IOException e) {
            NamePassword = "";
            result = false;
        }
        st = null;
        st = new StringTokenizer(NamePassword, ":");
        String authUserName;
        String authUserPsswd;
        try {
         // получим имя пользователя 
            authUserName = st.nextToken();
            } catch (NoSuchElementException e) {
                     authUserName = null;
                   result = false;
            }
            try {
                // получим пароль
                authUserPsswd = st.nextToken();
                } catch (NoSuchElementException e) {
                   authUserPsswd = null;
                   result = false;
            }
                   
            if (authUserName.equals("ALEX") && authUserPsswd.equals("123")) {
                //добавляем метку
                makeSession(authUserName);               
                result = true;
            }

        return result;
    }
    public boolean checkLoginFormAuth () {
        String user = request.getParameter("uname");
        String pw = request.getParameter("pass");
        System.out.println("== uname"+user+" pass="+pw);
        
        boolean result = false;
        if ( user.equals("ALEX") && pw.equals("123") )   {
            makeSession(user);
            result=true;
        }            
        return result;
    }
           
    private void makeSession (String user) {
        //запоминаем имя пользователя в сессию
        request.getSession(true).setAttribute("currentSessionUser",user);     
        request.getSession(true).setAttribute("UserFullName", "Smirnov AV");
    }
    
     public boolean checkSession (HttpServletRequest request) {
        boolean result = false; 
        
        if (session.getAttribute("currentSessionUser") != null) {
            result=true;
        }
        
        //для справки выводим все параметры
            Enumeration en2 = session.getAttributeNames();
            while (en2.hasMoreElements()) {
                System.out.println("==параметр сессии : " + en2.nextElement());
            }
        return result ;
    }
     
    /* public void logout() {
        logger.info("Trying to logout...");
        HttpSession session = FacesUtils.getSession();
        if (session != null) {
            String id = session.getId();
            session.removeAttribute(Constants.webuser);
            session.invalidate();
            WebApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
            logger.info("session " + id + " invalidated");
        }

        try {
            FacesUtils.getResponse().sendRedirect(FacesUtils.getRequest().getContextPath());
            FacesUtils.getResponse().flushBuffer();
        } catch (IOException e) {
            logger.error("", e);
        }
    } */
 
}
