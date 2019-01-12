<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%> 
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


	<form action="updateInDB">
  	<table>
		<tr>
			<th>Account Number</th>
			<th>Holder Name</th>
			<th>Account Balance</th>
			<th>Salary</th>
			<th>Over Draft Limit</th>
			<th>Type Of Account</th>
		</tr>
		<jstl:if test="${account!=null}">

			<tr>
				<td><input name="accountNumber" value=${account.bankAccount.accountNumber} readonly="readonly"></td>
				<td><input type="text" name="accountHolderName"
					value="${account.bankAccount.accountHolderName}"></td>
				<td><input name="accountBalance" value=${account.bankAccount.accountBalance} readonly="readonly"></td>
				<jstl:if test="${account.salary==true}">
					<td><select name="salary">
							<option value="salaryTrue">Yes</option>
							<option value="salaryFalse">No</option></td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</jstl:if>
				<jstl:if test="${account.salary==false}">
					<td><select name="salary">
							<option value="salaryFalse">No</option>
							<option value="salaryTrue">Yes</option></td>
					<td>${"N/A"}</td>
					<td>${"Savings"}</td>
				</jstl:if>
			</tr>
		</jstl:if>
			
	</table>
	<input type = "submit" value="submit"> 	&nbsp &nbsp&nbsp<input type = "reset" value="reset"> <br>
		
			</form>
		
	
	<%-- <div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div> --%>
</body>
</html>

