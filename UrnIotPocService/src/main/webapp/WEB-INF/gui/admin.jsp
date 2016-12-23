<%@include file="header.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body class="admin">
<h1>Beheren SmartUrnen</h1>
<form:form method="post" modelAttribute="urnForm">
	<c:if test="${not empty urnForm.error}">
		<div class="error">${urnForm.error}</div>
	</c:if>
	<table class="collectivityDiscount">
		<tr>
			<td><label id="lblUrnTag" class="mandatory"
				for="inputUrnTag">Device ID:</label></td>
			<td><form:input size="70" path="urn.DevEUI"
					id="inputUrnTag" /></td>
		</tr>
		<tr>
			<td />
			<td><form:errors path="urn.DevEUI" cssClass="error" /></td>
		</tr>
		<tr>
			<td><label id="lblUrnReference" class="mandatory"
				for="inputUrnReference">Intern referenceid:</label></td>
			<td><form:input size="70" path="urn.referenceId"
					id="inputUrnReference" /></td>
		</tr>
		<tr>
			<td />
			<td><form:errors path="urn.referenceId" cssClass="error" /></td>
		</tr>
		<tr>
			<td><label id="lblUrnDeceasedFirstName" class="mandatory"
				for="inputUrnReference">Voornaam van overledene:</label></td>
			<td><form:input size="70" path="urn.deceasedFirstName"
					id="inputUrnFirstName" /></td>
		</tr>
		<tr>
			<td />
			<td><form:errors path="urn.deceasedFirstName" cssClass="error" /></td>
		</tr>
		<tr>
			<td><label id="lblUrnDeceasedLastName" class="mandatory"
				for="inputUrnReference">Achternaam van overledene:</label></td>
			<td><form:input size="70" path="urn.deceasedLastName"
					id="inputUrnLastName" /></td>
		</tr>
		<tr>
			<td />
			<td><form:errors path="urn.deceasedLastName" cssClass="error" /></td>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit" name="save" value="Opslaan" />
			</td>
		</tr>
	</table>
</form:form>
<%@include file="footer.html" %>