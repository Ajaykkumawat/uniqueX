<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>
</head>
<body>
<h3>Browse And Upload the Student File</h3>
      
      <form method="POST" action="uploadFile" enctype="multipart/form-data">
		File to upload: 
		<input type="file" name="file"><br /> 
		<input type="submit" value="Upload"> 
	 </form>	
	
	<br>
    <br>
    <br>
    <table>
     <tr>
    	<th>Sorting Algorithm</th>
    	<th>Number of Student Records</th>
    	<th>Sorting Time In Nenosecond</th>
 	 </tr>
 	 
 	 <c:forEach items="${data}" var="entry">
 	 <tr>
    	<td>${entry.key}</td> 
    	<c:forEach items="${entry.value}" var="entry1">
			<td>${entry1.key}</td> 
    		<td>${entry1.value}</td>
     	</c:forEach>
     	</tr>
	</c:forEach>
	 
</table>
	
</body>
</html>