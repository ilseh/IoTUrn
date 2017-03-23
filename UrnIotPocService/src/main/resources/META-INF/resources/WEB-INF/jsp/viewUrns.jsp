<%@include file="header.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<body class="viewUrns">
<h1>Overzicht Urnen</h1>
<form:form method="post" modelAttribute="viewUrnsForm">
	<c:if test="${not empty viewUrnsForm.eventsPagedList.pageList}">

		<table class="show">
			<thead>
				<tr>
					<c:forEach var="columnHeader" items="${viewUrnsForm.columnHeaders}"
						varStatus="status">
						<th>${columnHeader}
						</th>
					</c:forEach>
				</tr>
			</thead>

			<tbody>
				<c:forEach var="urn"
					items="${viewUrnsForm.eventsPagedList.pageList}" varStatus="status">
					<tr>
						<td>${urn.id}</td>
						<td>${urn.deceasedFirstName} ${urn.deceasedLastName}</td>
						<td>${urn.referenceId}</td>
						<td><a href="<c:url value="viewEvents"><c:param name="urnTag" value="${urn.devEUI}"/></c:url>">${urn.devEUI}</a></td>
						<td>${urn.dateLch}</td>
						<td><a href="<c:url value="lightUrn"><c:param name="deviceId" value="${urn.devEUI}"/><c:param name="on" value="true"/><c:param name="deceasedLastName" value="${param.deceasedLastName}"/></c:url>"><input type="button" value="Aan"/></a>
						<a href="<c:url value="lightUrn"><c:param name="deviceId" value="${urn.devEUI}"/><c:param name="on" value="false"/><c:param name="deceasedLastName" value="${param.deceasedLastName}"/></c:url>"><input type="button" value="Uit"/></a>
						<a href="<c:url value="delete/urn"><c:param name="deviceId" value="${urn.devEUI}"/><c:param name="deceasedLastName" value="${param.deceasedLastName}"/></c:url>"><input type="button" value="Del"/></a></td>
						<td>
						<c:choose>
							<c:when test="${urn.currentStatus == 'search'}">
								<abr title="Urn lamp aan"><img src="LightOn.jpg" height="40" width="40"/></abr>
							</c:when>	
							<c:when test="${urn.currentStatus == ''}">
								<img src="LightOff.jpg" height="40" width="40"/>
							</c:when>
							<c:when test="${urn.currentStatus == 'unauthorized_movement'}">
								<abr title="Ongeoorloofde beweging"><img src="Alert.jpg" height="35" width="35"/></abr>
							</c:when>
							<c:otherwise>
								${urn.currentStatus}
							</c:otherwise>
						</c:choose>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</c:if>
</form:form>
<%@include file="footer.html" %>