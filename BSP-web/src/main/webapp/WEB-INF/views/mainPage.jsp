
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
        
        <p><a href="/order/make"> 2. Сделать заказ </a></p>
        
        <!--тест--> 
        <p><a href="getPrint"> 3. Увидеть печатную форму ( потом пернести в конец шага 2.Заказ) </a></p>
        <!-- p><a href="/order/print"> 3. Увидеть печатную форму ( поток пернести в конец шага 2.Заказ </a></p-->
        
        <p><a href="getOrderHistory"> 4. История операций </a></p>
        
        <p><a href="logout"> Выход </a></p>
     </body>
</html>

