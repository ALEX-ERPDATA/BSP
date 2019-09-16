
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">        
    </head>
    <body>
        <p>Welcome, <%=session.getAttribute("currentSessionUser")%></p>
        
        <p> Choose one item from the List </p>
        
        <p><a href="rates"> 1. Узнать курс валют </a></p>
        
        <p><a href="rates"> 2. Сделать заказ </a></p>
        
        <p><a href="logout"> Выход </a></p>
     </body>
</html>

