<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
    <jsp:body> 	

		<div class="container">
		<div class="row">
	  			<div class="col-md-12">
		  		<h3><c:url value="/createCourt/${facility.facilityId}" var="createCourtUrl" />
		    	<a href="${createCourtUrl }">Create a New Court</a></h3>
	    		</div>

	     	</div>
		            
		  <table class="table table-striped">
		 <thead>
		  <tr>
		     <th>ID</th>
		     <th>Title</th>
		     <th>View</th>
		     <th>Edit</th>
		     <th>Delete</th>
		  </tr>
		 </thead>
		 <tbody>
	
		 	
			<c:forEach var="item" items="${facility.courts}">
			
		
			<tr>
				<td>${item.courtNumber}</td>
				<td>${item.courtName}</td>
				<td><c:url value="/courts/view/${item.courtNumber}" var="viewUrl" />
				<a href="${viewUrl}">View</a></td>
				<td><c:url value="/courts/edit/${facility.facilityId}/${item.courtNumber}" var="editUrl"/>
				<a href="${editUrl }">Edit</a></td>
				<td><c:url value="/courts/delete/${facility.facilityId}/${item.courtNumber}" var="deleteUrl" />
				<a href="${deleteUrl}">Delete</a></td>
			</tr>
			</c:forEach>

		 </tbody>

		</table>
		</div>
    </jsp:body>
</t:mainLayout>
