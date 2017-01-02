<%@include file="header.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body class="deceased">
<h1>Overledene</h1>
<form:form method="post" modelAttribute="deceasedForm">

	<table class="edit">
		<tr>
			<td><label id="lblLastname" class="mandatory"
				for="inputLastname">Achternaam:</label></td>
			<td><form:input size="70" path="searchLastName"
					id="inputLastname" /></td>
		</tr>
		<tfoot>
		<tr>
			<td colspan="2"><input type="submit" name="search" value="Opzoeken" />
			</td>
		</tr>
		</tfoot>
	</table>
	
</form:form>
<%@include file="footer.html" %>