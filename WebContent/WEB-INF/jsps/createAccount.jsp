<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
	<jsp:body>
        <div id="error"></div>
		<c:url value="/register" var="url" />
		
		
		<div class="container">
			  <form name="form" method="post" action="${url}"
				onsubmit="return verify()">
				    <div class="form-group row">
				      <label for="username" class="col-md-12 col-form-label">Username*</label>
				      <div class="col-md-12">
				        <input type="text" class="form-control" name="username"
							placeholder="Username">
				      </div>
				    </div>
				    <div class="form-group row">
				      <label for="password" class="col-md-12 col-form-label">Password*</label>
				      <div class="col-md-12">
				        <input type="password" class="form-control" name="password"
							placeholder="Password">
				      </div>
				    </div>
				    <div class="form-group row">
				      <label for="verifyPassword" class="col-md-12 col-form-label">Password*</label>
				      <div class="col-md-12">
				        <input type="password" class="form-control"
							name="verifyPassword" placeholder="Re-enter Password">
				      </div>
				    </div>
				    	<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
				  	 <div class="form-group row">
				      <div class="offset-md-6 col-md-12">
				        <button type="submit" class="btn btn-primary"
							onclick="verify()">Register</button>
				      </div>
				      </div>
			    </form>
    	</div>
		
		
		
		<script src="<c:url value="/scripts/script.js" />"></script>
    </jsp:body>
</t:mainLayout>
