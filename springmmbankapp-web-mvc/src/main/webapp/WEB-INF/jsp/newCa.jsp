<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
 <body>
 <form action ="addCa">
  
	Enter Name :&nbsp &nbsp<input type = "text" name="accountHolderName" > <br><br>
	Enter Balance :&nbsp<input type = "number" name="accountBalance"> <br><br>
	Overdraft Limit:<input type = "number" name="odLmit"><br><br>
	<input type = "submit" value="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" value="reset"> <br>
		
	</form>
	 
	 <div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>