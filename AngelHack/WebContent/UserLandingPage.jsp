<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"
	import="edu.neu.angelhack.*"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Money Manager</title>
<link
	href="//netdna.bootstrapcdn.com/bootstrap/3.0.0/css/bootstrap.min.css"
	rel="stylesheet">
<script type="text/javascript"
	src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
<script>
	function myFunction() {
		var x = document.getElementById("myFile").value;
		//DummyClass dc = new DummyClass();
		x.setAttribute("type", "file");
		alert(x);
		//dc.post1(x);
		
	}
</script>
<style>
body {
	background:
		url(http://download.free-download-web.com/files/2014/09/Cartoon-study-article-vector-material-2.jpg)
		no-repeat center center fixed;
	-webkit-background-size: cover;
	-moz-background-size: cover;
	-o-background-size: cover;
	background-size: cover;
}
</style>
</head>

<body>
	<div class="navbar navbar-default navbar-static-top">
		<div class="container">
			<a href="DonorLogIn.jsp" class="navbar-brand">Chaanda</a>
			<div class="collapse navbar-collapse navHeaderCollapse">
				<ul class="nav navbar-nav navbar-right">
					<li><a href="DonorLogIn.jsp">Home</a></li>
					<li><a href="DonorLogOut.jsp">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>
	<h4 id="element1"></h4>
	<form action="UserLandingPage.jsp">
		<div class="container">
			<table class="table table-striped">
				<tr>
					<td colspan="2">Put in the receipt picture</td>
					<td><input type="file" id="myFile"></td>
				</tr>
			</table>
			<button onclick="myFunction()">Upload</button>
		</div>
	</form>
</body>
</html>