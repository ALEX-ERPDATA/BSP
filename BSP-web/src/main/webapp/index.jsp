<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        Добро пожаловать !
	<ol>
	    <li> <a href="img/DSC.jpg"> Get foto</a> </li>
            <!--li> Чтобы увидеть знанчения headers введите /getInfo?header=название_хидера </li-->
        </ol>
        
        <form action="formAuth" method="post">

        	Please enter your username 		
		<input type="text" name="uname"/><br>		
		
		Please enter your password
		<input type="text" name="pass"/>
			
		<input type="submit" value="submit">	        	
	</form>
    </body>
        <!--%
           System.out.println("=relogin"+session.getAttribute("relogin"));
        %-->
    </body>
</html>
