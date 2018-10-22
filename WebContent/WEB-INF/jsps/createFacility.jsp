<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %> <!-- formatting purposes -->
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<t:mainLayout>
    <jsp:body>
    
        <c:if test="${accountCreated}"><h3>Facility Administrator Account Created. Please Fill in the Facility Information.</h3></c:if>
    	
    
    
		<div id="error"></div>
		
		<div class="container">
		<h1>Create Facility</h1>
			
			<c:url value="/saveFacility" var="url" />
				<form:form modelAttribute="facility" method="GET" action="${url}" name="facilityForm"> 
					<form:input path="facilityId" type="hidden" value="${facility.facilityId}"/>
					<form:input path="username" type="hidden" value="${facility.username}"/>
					 				    	  
				    <div class="form-group row">
				    	<form:label path="facilityName" class="col-md-12 col-form-label">Facility Name*:</form:label>
				       <div class="col-md-12">
				      		<form:input path="facilityName" type="text" required="true" value="${facility.facilityName}" class="form-control"/>   
				      		<form:errors path="facilityName" cssClass="error" /> 	
				       </div>
				    </div>
				    
				    <div class="form-group">
				  		<form:label path="facilityDescription">Facility Description*:</form:label>
				  		<form:textarea path="facilityDescription" class="form-control" rows="5" maxlength="3000" required="true" value="${facility.facilityDescription}"/>
				  		<form:errors path="facilityDescription" cssClass="error" /> 	
					</div>
					  
					 <div class="form-group">
				  		<form:label path="line_1">Address Line 1*:</form:label>
				  		<form:input path="line_1" class="form-control" type="text" required="true" value="${facility.line_1}"/>
				  		<form:errors path="line_1" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="line_2">Address Line 2:</form:label>
				  		<form:input path="line_2" class="form-control" type="text" value="${facility.line_2}"/>
				  		<form:errors path="line_2" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="line_3">Address Line 3:</form:label>
				  		<form:input path="line_3" class="form-control" type="text" value="${facility.line_3}"/>
				  		<form:errors path="line_3" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="city">City*:</form:label>
				  		<form:input path="city" class="form-control" type="text" required="true" value="${facility.city}"/>
				  		<form:errors path="city" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="province">Province:</form:label>
				  		<form:input path="province" class="form-control" type="text" required="false" value="${facility.province}"/>
				  		<form:errors path="province" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="postalCode">Postal Code*:</form:label>
				  		<form:input path="postalCode" class="form-control" type="text" required="true" value="${facility.postalCode}"/>
				  		<form:errors path="postalCode" cssClass="error" /> 	
					</div>
					
					<div class="form-group">
				  		<form:label path="country">Country*:</form:label>
				  		<form:input path="country" class="form-control" type="text" required="true" value="${facility.country}"/>
				  		<form:errors path="country" cssClass="error" /> 	
					</div>
					
		
					<div class="form-group">
				  		<form:label path="contactNumber">Contact Number*:</form:label>
				  		<form:input path="contactNumber" class="form-control" type="tel" required="true" value="${facility.contactNumber}"/>
				  		<form:errors path="contactNumber" cssClass="error" /> 	
					</div>
					     
						

				  	  <div class="form-group row">
					      <div class="offset-md-6 col-md-12">
					        <button type="submit" class="btn btn-primary" >Save Facility</button>
					      </div>
				      </div>
			  </form:form>
    	</div>
		

		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
    
</t:mainLayout>

