<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->	
<t:mainLayout>
    <jsp:body>
        <div class="row">
	  		<div class="col-md-12">
	  			<h1>${court.courtName}</h1>
	  			<p><strong>Court Number:</strong> ${court.courtNumber}</p>
	  			<p><strong>Availability:</strong> ${court.availability}</p>
	  			<p><strong>Max Player:</strong> ${court.maxPlayer}</p>
	  			<p><strong>Court Type:</strong> ${court.courtType}</p>
	  			<p><strong>Rental Cost per Hour:</strong> ${court.getCost()}</p>
	  			<fmt:parseDate value="${court.creationDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
				<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
	  			<p><strong>Creation Date:</strong> ${formattedDate}</p>

	  			<hr/>
	  			<h2>Bookings:</h2>
	  			<c:forEach var="item" items="${court.bookings}">
		  			<p><strong>Booking Id:</strong> ${item.bookingId }</p>
		  			<p><strong>Customer Name:</strong> ${item.customerName }</p>
		  			<fmt:parseDate value="${item.bookingDate }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
					<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
					
		  			<p><strong>Booking Date:</strong> ${ formattedDate }</p>
	  				<p><strong>Booking Type:</strong> ${item.bookingType }</p>
	  				<p><strong>Booking Status:</strong> ${item.status }</p>
	  				
	  				<fmt:parseDate value="${item.startDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
					<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
					
	  				<p><strong>Start Date and Time:</strong> ${ formattedDate }</p>
	  				
	  				<fmt:parseDate value="${item.endDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
					<fmt:formatDate pattern="E yyyy-MM-dd hh:mm" value="${ parsedDateTime }" var="formattedDate" />
					
	  				<p><strong>End Date and Time:</strong> ${ formattedDate }</p>
	  				<hr size="5" style="border-top: 1px solid #ccc;"/>
	  			</c:forEach>
	  					
	  		</div>
		</div>
    </jsp:body>
</t:mainLayout>