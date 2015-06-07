<%@page import="edu.neu.angelhack.entity.User"%>
<%@page import="edu.neu.angelhack.manager.UserDAO"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.angelhack.*,java.util.*,java.math.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Donor Sign Up</title>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<br /> <br />
		<h4>Please fill the below fields to signup</h4>
		<br /> <br />


		<form action="UserSignUp.jsp">


			<table class="table table-striped">
				<tr>
					<td>Name</td>
					<td><input type="text" name="name" class="form-control"
						style="width: 250px;"></td>
				<tr />
				<tr>
				<td> Gender</td>
					<td><select name="select">							
					<Option value="male">Male</Option>
					<Option value="female">Female</Option>
							
					</select></td>
				</tr>
				<tr>
					<td>Username</td>
					<td><input type="text" name="username" class="form-control"
						style="width: 250px;"></td>
				<tr />
				<tr>
					<td>Password</td>
					<td><input type="password" name="password" class="form-control"
						style="width: 250px;"></td>
				</tr>
				<tr>
					<td>Email</td>
					<td><input type="text" name="email" class="form-control"
						style="width: 250px;"></td>
				</tr>
				<tr>
					<td>
						<button type="submit" name="action" value="submit"
							class="btn btn-primary">Signup</button>
					</td>
				</tr>
			</table>
		</form>
		<%
		String action = request.getParameter("action");
		String name = request.getParameter("name");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String email = request.getParameter("email");
		String gender = request.getParameter("select");

		if ("submit".equals(action)) {

			//Insert into the database.
			UserDAO dao = new UserDAO();
			User user = new User();
			
			user.setUserName(username);
			user.setPassWord(password);
			dao.insertUser(user);
			

			session.setAttribute("username", username);

			response.sendRedirect("Upload.jsp");
		}
	%>
	</div>
</body>
</html>