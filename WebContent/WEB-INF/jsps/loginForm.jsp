<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<t:mainLayout>
    <jsp:body>
        <c:if test="${param.error != null}">
			<div id="error">No such account exists.
			Please check your username and password!</div>
		</c:if>
		<c:url value="/login" var="loginUrl" />
		
		<c:if test="${accountCreated}"><div><h3>Facility created successfully! Please login to continue.</h3></div></c:if>
		
		<div class="container">
			  <form method="post" action="${loginUrl}">
				    <div class="form-group row">
				      <label for="username" class="col-md-12 col-form-label">Username</label>
				      <div class="col-md-12">
				        <input type="text" class="form-control" name="username" placeholder="Username">
				      </div>
				    </div>
				    <div class="form-group row">
				      <label for="password" class="col-md-12 col-form-label">Password</label>
				      <div class="col-md-12">
				        <input type="password" class="form-control" name="password" placeholder="Password">
				      </div>
				    </div>
				    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				  	 <div class="form-group row">
				      <div class="offset-md-6 col-md-12">
				        <button type="submit" class="btn btn-primary">Sign in</button>
				      </div>
				      </div>
			    </form>
    	</div>
		

		
		<c:url value="/createAccount" var="createUrl" />
		
		<h3><a href="${createUrl}">Create an Account</a></h3>
		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
    
</t:mainLayout>
