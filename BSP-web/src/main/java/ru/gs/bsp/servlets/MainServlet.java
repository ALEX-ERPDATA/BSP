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
 
    // Показать страницу Login.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        // Forward (перенаправить) к странице /WEB-INF/views/loginView.jsp
        // (Пользователь не может прямо получить доступ // к страницам JSP расположенные в папке WEB-INF).
        response.setContentType("text/html;charset=UTF-8");
        response.addHeader("CURRENT_NAME", "SRW_USER");
        RequestDispatcher dispatcher //
                = this.getServletContext().getRequestDispatcher("/WEB-INF/views/login.jsp");
 
        dispatcher.forward(request, response);
 
    }
 
    // Когда пользователь вводит userName & password, и нажимает Submit.
     @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
      // get Attribute - getAttribute() is for server-side usage only 
        //- you fill the request with attributes that you can use within the same request. 
        // For example - you set an attribute in a servlet, and read it from a JSP. 
        // Can be used for any object, not just string.
        // get Parameter - getParameter() возвращает параметры http-запроса. Они передаются от клиента к серверу. 
        //Например http://example.com/servlet?parameter=1. Может возвращать только String
        String user = request.getParameter("uname");
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
        
    }                
        /*
        String userName = request.getParameter("userName");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember = "Y".equals(rememberMeStr);
 
        UserAccount user = null;
        boolean hasError = false;
        String errorString = null;
 
        if (userName == null || password == null || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = MyUtils.getStoredConnection(request);
            try {
                // Найти user в DB.
                user = DBUtils.findUser(conn, userName, password);
 
                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        // В случае, если есть ошибка,
        // forward (перенаправить) к /WEB-INF/views/login.jsp
        if (hasError) {
            user = new UserAccount();
            user.setUserName(userName);
            user.setPassword(password);
 
            // Сохранить информацию в request attribute перед forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user);
 
            // Forward (перенаправить) к странице /WEB-INF/views/login.jsp
            RequestDispatcher dispatcher //
                    = this.getServletContext().getRequestDispatcher("/WEB-INF/views/loginView.jsp");
 
            dispatcher.forward(request, response);
        }
        // В случае, если нет ошибки.
        // Сохранить информацию пользователя в Session.
        // И перенаправить к странице userInfo.
        else {
            HttpSession session = request.getSession();
            MyUtils.storeLoginedUser(session, user);
 
            // Если пользователь выбирает функцию "Remember me".
            if (remember) {
                MyUtils.storeUserCookie(response, user);
            }
            // Наоборот, удалить Cookie
            else {
                MyUtils.deleteUserCookie(response);
            }
 
            // Redirect (Перенаправить) на страницу /userInfo.
            response.sendRedirect(request.getContextPath() + "/userInfo");
        }
    */
    
 
}