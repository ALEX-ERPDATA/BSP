<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<html>
	<head>
		<meta http-equip="Content-Type" content="text/html; charset=UTF-8"/>
		<title>Отправка сообщения в очередь</title>
	</head>
	<body>
		<form action="sendMessage" method="POST">
			<table>
				<tr>
					<td>Введите сообщение:</td>
					<td><input type="text" name="message" size="70"/></td>
				</tr>
				<tr>
					<td colspan="2"><input type="submit" name="submitMessage" value="Отправить"/></td>
				</tr>
			</table>
		</form>
	</body>
</html>