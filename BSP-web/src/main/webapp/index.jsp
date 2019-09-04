<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        Страница для тестирования разных вариантов
	<ol>
	    <li> <a href="img/DSC.jpg"> Get foto</a> </li>
            <li> Чтобы увидеть знанчения headers введите /getInfo?header=название_хидера </li>
            <li>  </li>
            <li> <a href="app/Main"> Вход в приложение</a> </li> 
            
        </ol>
        
        <!--form action="app/Main" method="post">

        	Please enter your username 		
		<input type="text" name="uname"/><br>		
		
		Please enter your password
		<input type="text" name="pass"/>
			
		<input type="submit" value="submit">	        	
	</form-->
    </body>
        <!--%
           System.out.println("=relogin"+session.getAttribute("relogin"));
        %-->
</html>
