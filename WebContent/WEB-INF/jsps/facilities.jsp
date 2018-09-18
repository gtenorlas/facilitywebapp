<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
    <jsp:body>
		<div class="container">   
		
		  <c:if test="${empty facilities}"><h3>No facility found.</h3></c:if>
		         
		  <table class="table table-striped table-bordered">
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
