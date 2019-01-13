<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

	<style>
.dropbtn {
	background-color: #4CAF50;
	color: white;
	padding: 16px;
	font-size: 16px;
	border: none;
}

.dropdown {
	position: relative;
	display: inline-block;
}

.dropdown-content {
	display: none;
	position: absolute;
	background-color: #f1f1f1;
	min-width: 160px;
	box-shadow: 0px 8px 16px 0px rgba(0, 0, 0, 0.2);
	z-index: 1;
}

.dropdown-content a {
	color: black;
	padding: 12px 16px;
	text-decoration: none;
	display: block;
}

.dropdown-content a:hover {
	background-color: #ddd;
}

.dropdown:hover .dropdown-content {
	display: block;
}

.dropdown:hover .dropbtn {
	background-color: #3e8e41;
}
</style>
</head>
<body>













	<ul>
		<div class="dropdown">
			<button class="dropbtn">Create</button>
			<div class="dropdown-content">
				<a href="addSaAccount">Create New Saving Account</a> 
				<a href="addCaAccount">Create	New Current Account</a> 
			</div>
		</div>
		  
		<li><a href="updateAccount">Update Account</a></li>
		<li><a href="closeAccount">Close Account</a></li>
		<li><a href="searchForm">Search Account</a></li>
		<li><a href="withdrawAmount">Withdraw</a></li>
		<li><a href="depositeAmount">Deposit</a></li>
		<li><a href="currentBalance">Check Current Balance</a></li>
		<li><a href="fundTransfer">Fund Transfer</a></li>
		<li><a href="getAll"> Get All Saving Accounts</a></li>
		<li><a href="exit">Exit</a></li>

	</ul>

</body>
</html>