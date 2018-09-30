<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:mainLayout>
    <jsp:body>
		<div id="error"></div>
		
		<div class="container">
		<h1>Update a Booking</h1>
			
			<c:url value="/saveBooking" var="url" />
				<form:form modelAttribute="booking" method="GET" action="${url}"  name="bookingForm"> 
					<input name="facilityId" type=hidden value="${bookingId}"/>
					  
				  
				    
				    <div class="form-group row">
				    	<form:label path="status" class="col-md-12 col-form-label">Status (select one)*:</form:label>
				       <div class="col-md-12">
				      		<form:select path="status" items="${ booking.status()}" value="${booking.status }" class="form-control"/>	
				       </div>
				    </div>
						

				  	  <div class="form-group row">
					      <div class="offset-md-6 col-md-12">
					        <button type="submit" class="btn btn-primary" >Save Booking</button>
					      </div>
				      </div>
			  </form:form>
    	</div>
		

		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
    
</t:mainLayout>

