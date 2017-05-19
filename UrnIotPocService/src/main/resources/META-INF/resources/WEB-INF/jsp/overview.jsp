<%@include file="header.html" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<spring:url value="${requestScope['javax.servlet.forward.request_uri']}?urnTag=${param.urnTag}&" var="pageurl" />
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
						<td>${event.dateTime}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
			<span> 
			<c:forEach begin="0" end="${eventsForm.eventsPagedList.pageCount-1}" varStatus="loop">&nbsp;&nbsp;
    			<c:choose>
					<c:when test="${loop.index == eventsForm.eventsPagedList.page}">${loop.index+1}</c:when>
					<c:otherwise>
						<a href="${pageurl}currentPage=${loop.index}">${loop.index+1}</a>
					</c:otherwise>
				</c:choose>
    			&nbsp;&nbsp;
    		</c:forEach>
			</span>
			<span>
				<c:choose>
					<c:when test="${eventsForm.eventsPagedList.lastPage}">Next</c:when>
					<c:otherwise>
						<a href="${pageurl}currentPage=${eventsForm.eventsPagedList.page}&gotoPage=next">Next</a>
					</c:otherwise>
				</c:choose>
			</span>
		</c:if>
</form:form>
<%@include file="footer.html" %>