<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        START JSP PAGE  !!! 1
	<ol>
	        <li> <a href="img/DSC.jpg"> Get foto</a> </li>
                <li> Чтобы увидеть знанчения headers введите /getInfo?header=название_хидера </li>
        </ol>
   	<ol>
            <li> <a href="LoginPost"> Start login ( using Post() )</a> </li>
            <li> <a href="app/welcome"> Basic Authentification </a> </li>
        </ol>
        <!--%
           System.out.println("=relogin"+session.getAttribute("relogin"));
        %-->
    </body>
</html>
