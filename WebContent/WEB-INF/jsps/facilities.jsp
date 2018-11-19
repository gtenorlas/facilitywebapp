<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
    <jsp:body>
		<div class="container">
		<div class="row">
	  			<div class="col-md-12">
		  		<h1>Facilities</h1>
	    		</div>

	     	</div>
		            
		  <table class="table table-striped">
		 <thead>
		  <tr>
		     <th>Facility Name</th>
		     <th>Facility Description</th>
		  </tr>
		 </thead>
		 <tbody>
		 
	
			<c:forEach var="item" items="${facilities}">
			
		
			<tr>
				<td>${item.facilityName}</td>
				<td>${item.facilityDescription}</td>
		
			</tr>
			</c:forEach>

		 </tbody>

		</table>
		</div>

    </jsp:body>
</t:mainLayout>
