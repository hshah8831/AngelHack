<%@page import="edu.neu.angelhack.entity.User"%>
<%@page import="edu.neu.angelhack.manager.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.angelhack.*,java.util.*,java.math.*" %>
<html> 
<head> 
<title>File Upload with Servlet 3.0</title> 
</head> 
<body> 
<% 
//UserDAO dao = new UserDAO();
String username = request.getParameter("username");
//Integer t = dao.getLatestTransaction(username);
session.setAttribute("username", username);
//session.setAttribute("tran", t);%>
<form action="fileUpload" enctype="multipart/form-data" method="post"> 
<input type="file" name="uploadFile" /> <input type="submit" /></form> 
</body> 
</html>