<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->	
<t:mainLayout>
    <jsp:body> 	
		<div class="container"> 
		<h1>All Bookings</h1>           
		  <table class="table table-striped">
		 <thead>
		  <tr>
		     <th>Court Number</th>
		     <th>Court Name</th>
		     <th>Customer Name</th>
		     <th>Booking Start Date/Time</th>
		     <th>Booking End Date/Time</th>
		  </tr>
		 </thead>
		 <tbody>
	
			<c:forEach var="item" items="${facility.courts}">
			
				<c:forEach var="booking" items="${item.bookings}">
					<tr>
						<td>${item.courtNumber}</td>
						<td>${item.courtName}</td>
						<td>${booking.customerName}</td>
						<fmt:parseDate value="${booking.startDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
						<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
					
	  					<td> ${ formattedDate }</td>
	  				
	  					<fmt:parseDate value="${booking.endDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
						<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
					
	  					<td> ${ formattedDate }</td>
					</tr>
				</c:forEach>
			</c:forEach>
		 </tbody>

		</table>
		</div>

    </jsp:body>
</t:mainLayout>
