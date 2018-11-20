<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->	
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:mainLayout>
    <jsp:body> 	
		<div class="container">
		<c:choose>
  			<c:when test="${isFiltered eq true}"> 
				<div id="searchBookings">
			</c:when>
 		 	<c:otherwise>
 		 	 	<div id="searchBookings" style="display:none;">
 		 	</c:otherwise>
 		 </c:choose>
 		 
 		 <h1>Filter Bookings</h1>
			
			<c:url value="/searchBooking" var="url" />
				<form:form method="GET" action="${url}" name="bookingForm"> 
				
				    <div class="form-group row">
				    	<label class="col-md-12 col-form-label">Customer Name:</label>
				       <div class="col-md-12">
				      		<input name = "customerName" type="text"   class="form-control"/>    	
				       </div>
				    </div>
					<!-- 
				    <div class="form-group row">
				    	<label  class="col-md-12 col-form-label">Court Name:</label>
				       <div class="col-md-12">
				      		<input name = "courtName" type="text"   class="form-control"/>    	
				       </div>
				    </div>
				     -->
				    <div class="form-group row">
				    	<label class="col-md-12 col-form-label">Status:</label>
				       <div class="col-md-12">
				      		<select name = "status"  class="form-control">	
				      		<option value=""></option>
				      		<option value="active">Active</option>
  							<option value="cancelled">Cancelled</option>
  							<option value="completed">Completed</option>
  							</select>
				       </div>
				    </div>
				    
				    <div class="form-group row">
				    	<label  class="col-md-12 col-form-label">Booking Start Date:</label>
				       <div class="col-md-12">
				      		<input  type="date"  name = "startDT"  class="form-control"/>    	
				       </div>
				    </div>

				  	  <div class="form-group row">
					      <div class="offset-md-6 col-md-12">
					        <button type="submit" class="btn btn-primary" >Filter Booking</button>
					      </div>
				      </div>
			  </form:form>
			  <hr/>
		</div>
		
	
		  
		 
			<h1 >All Bookings</h1>
			 <div class="dropdown">
			  <button class="btn btn-primary dropdown-toggle" type="button" id="dropdownMenu1" data-toggle="dropdown" aria-haspopup="true" aria-expanded="true">
			   Action
			    <span class="caret"></span>
			  </button>
			  <ul class="dropdown-menu" aria-labelledby="dropdownMenu1">
			    <li><a href="#" onclick="showFilter()">Filter Bookings</a></li>
			    <c:url value="/pdfs/report" var="urlPdf" />
			    <li><a href="${urlPdf}" >Download Report</a></li>
			
			  </ul>
			</div>
		
		  <table class="table table-striped">
		 <thead>
		  <tr>
		     <th>Court Number</th>
		     <th>Court Name</th>
		     <th>Customer Name</th>
		     <th>Status</th>
		     <th>Booking Start Date/Time</th>
		     <th>Booking End Date/Time</th>
		     <th>Cost</th>
		     <th>Edit</th>
		     <!-- <th>Delete</th> -->
		  </tr>
		 </thead>
		 <tbody>
	

			
				<c:forEach var="booking" items="${bookings}">
					<tr>
						<td>${booking.court.courtNumber}</td>
						<td>${booking.court.courtName}</td>
						<td>${booking.customerName}</td>
						<td>${booking.status}</td>
						<fmt:parseDate value="${booking.startDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
						<fmt:formatDate pattern="E yyyy-MM-dd h:mm a" value="${ parsedDateTime }" var="formattedDate" />
					
	  					<td> ${ formattedDate }</td>
	  				
	  					<fmt:parseDate value="${booking.endDateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="both" />
						<fmt:formatDate pattern="E yyyy-MM-dd h:mm a" value="${ parsedDateTime }" var="formattedDate" />
					
	  					<td> ${ formattedDate }</td>
	  					
	  					<td> ${ booking.getCost(booking.court)}</td>
	  					<td><c:url value="/bookings/edit/${booking.bookingId}/${booking.court.courtNumber}" var="editUrl"/>
				<a href="${editUrl }">Edit</a></td>
				<!-- 
				<td><c:url value="/bookings/cancel/${booking.bookingId}}" var="cancelUrl" />
				<a href="${cancelUrl}" onclick="return cancelFunction('/bookings/cancel/${facility.facilityId}/${item.courtNumber}')" id="cancelBooking">Cancel</a></td>
				 -->
					</tr>
				</c:forEach>
	
		 </tbody>

		</table>
		</div>
		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
</t:mainLayout>
