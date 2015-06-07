<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">
</head>
<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<a href="ChaandaHomePage.jsp" class="navbar-brand">Money Manager</a>
			<div class="collapse navbar-collapse navHeaderCollapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="UserLogIn.jsp">Home</a></li>
					<li><a href="UserSignUp.jsp">Sign Up</a></li>
				</ul>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row">
			<div class="col-md-4 col-md-offset-9 ">
				<div class="panel panel-default">
					<div class="panel-heading">
						<strong class="">Login</strong>
					</div>
					<div class="panel-body">
						<form action="DonorLogIn.jsp" class="form-horizontal">
							<%
								String action = request.getParameter("action");
								String uName = request.getParameter("nUserName");
								String pwd = request.getParameter("nPassword");

								/*if ("login".equals(action)) {

									StudentDAO dao = new StudentDAO();
									Person p = dao.universityLogin(uName, pwd);

									if (p != null) {
										session.setAttribute("userName", p.getUserName());
										response.sendRedirect("UserLandingPage.jsp");
									}

									else {*/
							%>
							<h6 class="text-danger">
								<%
									out.println("Username or Password Incorrect . Please try again");
								%>
							</h6>
							<%
								/*}
								}

								if ("cancel".equals(action)) {
									response.sendRedirect("UserLogIn.jsp");*/
								//}
							%>
							<div class="form-group">
								<label for="nUserName" class="col-sm-3 control-label">Username</label>
								<div class="col-sm-9">
									<input class="form-control" id="txtUserName" name="nUserName"
										type="text">
								</div>
							</div>
							<div class="form-group">
								<label for="nPassword" class="col-sm-3 control-label">Password</label>
								<div class="col-sm-9">
									<input class="form-control" id="txtPassword" name="nPassword"
										type="password">
								</div>
							</div>
							<div class="form-group">
								<div class="col-sm-offset-3 col-sm-9">
									<div class="checkbox">
										<label class=""> <input class="" type="checkbox">Remember
											me
										</label>
									</div>
								</div>
							</div>
							<div class="form-group last">
								<div class="col-sm-offset-3 col-sm-9">
									<button type="submit" name="action"
										class="btn btn-success btn-sm" value="login">Sign in</button>
									<button type="reset" class="btn btn-default btn-sm">Reset</button>
								</div>
							</div>
						</form>
					</div>
					<div class="panel-footer">
						First time User? <a href="UserSignUp.jsp" class="">Register
							here</a>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>