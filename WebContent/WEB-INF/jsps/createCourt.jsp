<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:mainLayout>
    <jsp:body>
		<div id="error"></div>
		
		<div class="container">
		<h1>Create/Update a Court</h1>
			
			<c:url value="/saveCourt" var="url" />
				<form:form modelAttribute="court" method="GET" action="${url}"  name="courtForm"> 
					<input name="facilityId" type=hidden value="${facilityId}"/>
					<form:input path="courtNumber" type="hidden" value="${court.courtNumber}"/>
					  
				    	
				   <div class="form-group row">
				    	<form:label path="courtName" class="col-md-12 col-form-label">Court Name*:</form:label>
				       <div class="col-md-12">
				      		<form:input path="courtName" type="text" required="true" value="${court.courtName}" class="form-control"/>    	
				       </div>
				    </div>
				    
				    <div class="form-group row">
				    	<form:label path="availability" class="col-md-12 col-form-label">Status (select one)*:</form:label>
				       <div class="col-md-12">
				      		<form:select path="availability" items="${ court.availabilityTypes()}" value="${court.availability }" class="form-control"/>	
				       </div>
				    </div>
				    
				    <div class="form-group row">
				    	<form:label path="maxPlayer" class="col-md-12 col-form-label">Maximum Players*:</form:label>
				       <div class="col-md-12">
				      		<form:input path="maxPlayer" type="number" required="true" value="${court.maxPlayer}" class="form-control" max="12" increment="1"/>    	
				       </div>
				    </div>
				    
				    <div class="form-group row">
				    	<form:label path="courtType" class="col-md-12 col-form-label">Court Type (select one)*:</form:label>
				       <div class="col-md-12">
				      		<form:select path="courtType" items="${ court.courtTypes()}" value="${court.courtType }" class="form-control"/>	
				       </div>
				    </div>
				      
				    <div class="form-group row">
				    	<form:label path="price" class="col-md-12 col-form-label">Rental Cost per Hour*:</form:label>
				       <div class="col-md-12">
				      		<form:input path="price" type="number" required="true" value="${court.price}" class="form-control" increment="0.5" step="any"/>    	
				       </div>
				    </div>
					     
						

				  	  <div class="form-group row">
					      <div class="offset-md-6 col-md-12">
					        <button type="submit" class="btn btn-primary" >Save Court</button>
					      </div>
				      </div>
			  </form:form>
    	</div>
		

		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
    
</t:mainLayout>

