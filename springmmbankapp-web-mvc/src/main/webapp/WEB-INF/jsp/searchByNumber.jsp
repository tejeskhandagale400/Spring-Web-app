<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="searchAccount">
		Enter Account Number: <input type="number" name="txtAccountNumber" />
		<br /> <input type="submit" value="Submit">

	</form>
	  <div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>  
</body>
</html>