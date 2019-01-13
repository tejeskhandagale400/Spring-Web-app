<%@ taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%> 
<!DOCTYPE html>
<html>
<head>
 <meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<%-- ${account.bankAccount.accountNumber}
 --%>	<table>
		<tr>
			<th><a href="sortByAccNo">Account Number </a></th>
			<th><a href="sortByName">Holder Name</a></th>
			<th><a href="sortByAccBalance">Account Balance</a></th>
			<th><a href="sortBySalary ">Salary</a></th>
			<th>Over Draft Limit</th>
			<th><a href="sortByType">Type Of Account</a></th>
		</tr>
		<jstl:if test="${account!=null}">
			<tr>
				<td>${account.bankAccount.accountNumber}</td>
				<td>${account.bankAccount.accountHolderName }</td>
				<td>${account.bankAccount.accountBalance}</td>
		 	 <jstl:if test="${account.bankAccount.type.equals('CA')}">
			 	<td>${"N/A"}</td>
 				<td>${account.odLimit}</td>					
				<td>${"Current"}</td>
				</jstl:if> 
				<jstl:if test="${account.bankAccount.type.equals('SA')}">
				<td>${account.salary==true?"Yes":"No"}</td>	
				<td>${"N/A"}</td>
				<td>${"Savings"}</td>
				</jstl:if>
				
				<td>${account.salary==true?"Yes":"No"}</td>	
				<td>${"N/A"}</td>
				<td>${account.bankAccount.type}</td>
			</tr>
		</jstl:if>
	  <jstl:if test="${accounts!=null}">
			<jstl:forEach var="account" items="${accounts}">
				<tr>
					<td>${account.bankAccount.accountNumber}</td>
					<td>${account.bankAccount.accountHolderName }</td>
					<td>${account.bankAccount.accountBalance}</td>
					<%-- <td>${account.salary==true?"Yes":"No"}</td>
					<td>${"N/A"}</td>
					<td>${account.bankAccount.type}</td>
					 --%>
			
				<%-- <jstl:if test="${account.bankAccount.type.equals('CA')}">
			 	<td>${"N/A"}</td>
 				<td>${account.odLimit}</td>					
				<td>${"Current"}</td>
				</jstl:if> --%>
				<jstl:if test="${account.bankAccount.type.equals('SA')}">
				<td>${account.salary==true?"Yes":"No"}</td>	
				<td>${"N/A"}</td>
				<td>${"Savings"}</td>
				</jstl:if>
					
					
				</tr>
			</jstl:forEach>
		</jstl:if> 
	</table>

 <div>
		<jsp:include page="homeLink.html"></jsp:include>
	</div>
</body>
</html>