<?php
$last_modified_time = getlastmod();
header("Cache-Control: public");
header("Expires: " . date("r", time()+10800));
 
if (isset($_SERVER['HTTP_IF_MODIFIED_SINCE']) && strtotime($_SERVER['HTTP_IF_MODIFIED_SINCE']) &gt; $last_modified_time){
    header('HTTP/1.1 304 Not Modified');
    die; /* убили всё, что ниже */
}
header('Last-Modified: '.gmdate('D, d M Y H:i:s', $last_modified_time).' GMT');
?>

<!DOCTYPE html>     
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Start Page</title>
    </head>
    <body>
        
        <ol>
	        <li> <a href="img/DSC.jpg"> Get foto</a> </li>
                <li> Чтобы увидеть знанчения headers введите /getInfo?header=название_хидера </li>
        </ol>
   	<ol>
            <li> <a href="LoginPost"> Войти ( используя Post() )</a> </li>
            <li> <a href="app/welcome"> Войти (Basic Auth) </a> </li>
        </ol>
        <!--%
           System.out.println("=relogin"+session.getAttribute("relogin"));
        %-->
    </body>
</html>
