
package ru.gs.bsp.servlets;
/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
public class TimeSessionServlet extends HttpServlet {

boolean flag = true;

protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException {
    performTask(req, resp);
}

private void performTask(HttpServletRequest req, HttpServletResponse resp) throws ServletException {
    HttpSession session = null;
    if (flag) {
        //создание сессии и установка времени инвалидации
        session = req.getSession();
        int timeLive = 10; //десять секунд!
        session.setMaxInactiveInterval(timeLive);
        flag = false;
        } else {
            //если сессия не существует, то ссылка на нее не будет получена
            session = req.getSession(false);
        }
    TimeSession.go(resp, req, session);

}

}



import java.io.IOException;

import java.io.PrintWriter;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;

public class TimeSession {

public static void go(HttpServletResponse resp, HttpServletRequest req, HttpSession session ) {

PrintWriter out = null;

try {

out = resp.getWriter();

out.write("<br> Creation Time : "

+ new Date(session.getCreationTime()));

out.write("<br> Session alive! ");

out.flush();

out.close();

} catch (NullPointerException e) {

//если сессия не существует, то генерируется исключение

if (out != null)

out.print("Session disabled!");

} catch (IOException e) {

e.printStackTrace();

throw new RuntimeException("i/o failed: " + e);

}
    
  
}
*/