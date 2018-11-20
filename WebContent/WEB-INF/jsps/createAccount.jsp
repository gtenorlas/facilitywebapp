<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
	<jsp:body>
		<c:if test="${duplicate}"><div><h4 id="error">Username already taken.</h4></div></c:if>
        <div><h4 id="error"></h4></div><br/>
		<c:url value="/register" var="url" />
		
		
		<div class="container">
			  <h1>Sign Up</h1>
			  <form name="form" method="post" action="${url}"
				onsubmit="return verify()">
				    <div class="form-group row">
				      <label for="username" class="col-md-12 col-form-label">Username*:</label>
				      <div class="col-md-12">
				      <div class="input-group">
                          <span class="input-group-addon navbar-inverse"><i class="gly-color glyphicon glyphicon-user"></i></span>
				        	<input type="text" class="form-control" name="username" required="true"
							placeholder="Username" maxlength="45">
				      </div>
				      </div>
				    </div>
				    <div class="form-group row">
				      <label for="password" class="col-md-12 col-form-label">Password*:</label>
				      <div class="col-md-12">
				      <div class="input-group">
                             <span class="input-group-addon navbar-inverse"><i class="gly-color glyphicon glyphicon-lock"></i></span>
				        <input type="password" class="form-control" name="password" required="true"
							placeholder="Password" maxlength="60">
							</div>
				      </div>
				    </div>
				    <div class="form-group row">
				      <label for="verifyPassword" class="col-md-12 col-form-label">Re-enter Password*:</label>
				      <div class="col-md-12">
				      <div class="input-group">
                             <span class="input-group-addon navbar-inverse"><i class="gly-color glyphicon glyphicon-lock"></i></span>
				        <input type="password" class="form-control"
							name="verifyPassword" placeholder="Re-enter Password" required="true"> 
				      </div>
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
