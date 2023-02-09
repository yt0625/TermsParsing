<%@page import="main.parse.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC"-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<body>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MCP</title>
<link rel="stylesheet" type="text/css" media="screen" href="style.css" />
</head>

<center>
	<h1>약관 분석 화면</h1>
	<script>Main.root.print();</script>

	<form action="fileupload.jsp" method="post"
		enctype="Multipart/form-data">
		<b>제목 : </b><input type="text" size="10" maxlength="15" name="subject">
		<b>올린 사람 : </b><input type="text" size="10" maxlength="15" name="name"> 
		<br><b>파일명 : </b><input type="file" size="25" style="padding-top: 10px;" name="fileName" /><br /> <input
			type="submit" style="margin-top: 10px;" value="전송" /> <input type="reset" value="취소" />
	</form>


</center>
</body>
</html>