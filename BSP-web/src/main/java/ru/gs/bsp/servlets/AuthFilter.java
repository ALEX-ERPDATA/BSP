package ru.gs.bsp.servlets;

import java.io.IOException;
import static java.util.Collections.list;
import java.util.Enumeration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AuthFilter implements Filter {

    private static FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
        System.out.println(" == init " + filterConfig.getFilterName());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        System.out.print("=do Filter: Request received .....");

        try {

            boolean authorized = this.checkUserAutorization((HttpServletRequest) request);

            // проверяем авторизирован ли пользователь, если да - пропускаем запрос дальше
            if (authorized) {
                System.out.print("= Authorizired! Good Luck ");
                chain.doFilter(request, response);
            } // не авторизован / требуем авторизацию
            else if (response instanceof HttpServletResponse) {
                ServletContext context = filterConfig.getServletContext();
                //String login_page = context.getInitParameter("login_page");
                System.out.print("=NO Authentication, forward to Login page = ");
                // вернуть 401
                ((HttpServletResponse) response).setStatus(((HttpServletResponse) response).SC_UNAUTHORIZED);
                ((HttpServletResponse) response).setHeader("WWW-Authenticate", "Basic realm=\"realm\"");
                //context.getRequestDispatcher(login_page).forward(request, response);
            } else {
                throw new ServletException(" something happens ");
            }

        } catch (IOException io) {
            System.out.println("IOException raised in AuthenticationFilter");
        }

        System.out.print("do Filter : Response dispatched ......");
    }

    private boolean checkUserAutorization(HttpServletRequest request) {

        HttpSession session = request.getSession();
        boolean result = false;
        //проверяем наличие нашего атрибута в сессии ,  установленного при логине
        // проверяем наличие передаваемого логина/пароля      
        if (session != null) {
            System.out.println("cессия новая =" + session.isNew());
            System.out.println("=id session=" + session.getId());
            /*Enumeration en = session.getAttributeNames();
            while (en.hasMoreElements()) {
                System.out.println("==параметр сессии на входе: " + en.nextElement());
            }*/

            if (session.getAttribute("RELOGIN") != null) {
                session.removeAttribute("RELOGIN");
                return false;
            }

            if (session.getAttribute("currentSessionUser") != null) {
                result = true;
            } else { // метки нет , проверяем логин/пароль.  Если совпадает - выдаем метку

                sun.misc.BASE64Decoder bs64dec = new sun.misc.BASE64Decoder();
                String NamePassword = null;
                // получить заголовок Authorization
                String auth_header = request.getHeader("Authorization");

                if (auth_header != null) {

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
                        session.setAttribute("currentSessionUser", authUserName);
                        session.setMaxInactiveInterval(15);

                        result = true;
                    }

                    // можем сгенерировать еще одну куку
                    // Создаем куку
                    //    Cookie temp_key = new Cookie ("BOOK_TEMP_KEY","HASH1111");
                    //    temp_key.setMaxAge(60*1);
                    //    response.addCookie(temp_key);                      
                }
            }
            //для справки выводим все параметры
            /*Enumeration en2 = session.getAttributeNames();
            while (en2.hasMoreElements()) {
                System.out.println("==параметр сессии перед отправкой: " + en2.nextElement());
            }*/
        } else {
            System.out.println("=cессия не найдена");
        }
        return result;
    }

    @Override
    public void destroy() {
            
    }

}
