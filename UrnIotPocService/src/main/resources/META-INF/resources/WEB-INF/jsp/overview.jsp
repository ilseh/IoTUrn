<%@include file="header.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body class="overview">
<form:form method="post" modelAttribute="eventsForm">
	<h1>Activiteiten urn</h1>
	<c:if test="${not empty eventsForm.eventsPagedList.pageList}">

		<table class="show">
			<thead>
				<tr>
					<c:forEach var="columnHeader" items="${eventsForm.columnHeaders}"
						varStatus="status">
						<th>${columnHeader}
						</th>
					</c:forEach>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="event"
					items="${eventsForm.eventsPagedList.pageList}" varStatus="status">
					<tr>
						<td>${event.id}</td>
						<td>${event.devEUI}</td>
						<td>${event.payload_hex}</td>
						<td>${event.eventType}</td>
						<td>${event.time}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</form:form>
<%@include file="footer.html" %>