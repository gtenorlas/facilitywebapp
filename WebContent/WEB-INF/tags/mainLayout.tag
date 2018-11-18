<%@tag description="Shared Template" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="title" fragment="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="#" />
<!-- <link rel="shortcut icon" href="data:image/x-icon;," type="image/x-icon"> -->
<link rel="stylesheet" href="<c:url value="/css/style.css" />">
<title><jsp:invoke fragment="title"/></title>

 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

<!-- Add icon library -->
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body>
	<div class="wrapper">
		<nav class="navbar navbar-inverse">
			  <div class="container-fluid">
			  
			   <!-- Brand and toggle get grouped for better mobile display -->
			    <div class="navbar-header">
			      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
			        <span class="sr-only">Toggle navigation</span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			        <span class="icon-bar"></span>
			      </button>
	
			     	<c:url value="/" var="homeUrl" />
			      	<a class="navbar-brand" href="${homeUrl }" style="color:#f5f5f0;" style="margin-top:0 !important;padding-top:0px !important;"><span><img height='16' width='16' src="<c:url value="/images/logomininew.png" />" alt="Brand"></span>Book2Ball</a>


			    </div>
			  
			  
			<!-- Collect the nav links, forms, and other content for toggling -->
    		<div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
		
			    <ul class="nav navbar-nav">
				   
			      <li><a href="${homeUrl }">Home</a></li>
			       <!-- check if user is not logged in -->
					<c:if test="${empty pageContext.request.userPrincipal}">
				      <c:url value="/facilities" var="facilitiesUrl" />
				      <li><a href="${facilitiesUrl}">Our Facilities</a></li>
			      </c:if>    
			      <c:url value="/contactUs" var="contactUsUrl" />
			      <li><a href="${contactUsUrl}">Contact Us</a></li>
			      
			       <!-- check if user is logged in -->
					<c:if test="${not empty pageContext.request.userPrincipal}">
					    <c:url value="/courts" var="courtsUrl" />
			      		<li><a href="${courtsUrl}">Courts</a></li>
			      		<c:url value="/bookings" var="bookingsUrl" />
			      		<li><a href="${bookingsUrl}">Bookings</a></li>
					</c:if>    
			
			      
			      
			    </ul>
			    <c:url value="/search" var="searchUrl" />
			    <form class="navbar-form navbar-left" action="${searchUrl}" method="get" >
				  <div class="input-group">
				    <input type="text" class="form-control" placeholder="Search" name="keyword">
				    <div class="input-group-btn">
				      <button class="btn btn-primary btn-md" type="submit">
				        <i class="glyphicon glyphicon-search" style="font-size:21px;"></i>
				      </button>
				    </div>
				  </div>
				</form>
				
				
			    <ul class="nav navbar-nav navbar-right">
			    
			      <!-- check if user is logged in -->
					<c:choose>
						 <c:when test="${not empty pageContext.request.userPrincipal}">
					    	<!-- Have user to be able to logout -->
					    	<c:url value="/logout" var="logoutUrl" />
					    	<li class="navbar-text" >       ${facility.facilityName}     /      ${pageContext.request.userPrincipal.name}</li>
							<li><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
						</c:when>    
						<c:otherwise>
							<!-- Have user to be able to login and create account -->
					    	<c:url value="/createAccount" var="createUrl" />
					    	<li><a href="${createUrl}"><span class="glyphicon glyphicon-user"></span> Sign Up</a></li>
					    	<c:url value="/login" var="loginUrl" />
							<li><a href="${loginUrl}"><span class="glyphicon glyphicon-log-in"></span> Login</a></li>
						</c:otherwise>
					</c:choose>
			    </ul>
			  </div>
			  </div>
			</nav>
		
		
		
		<div id="body" style="padding: 20px;">
			<jsp:doBody />
		</div>
		
		
		
		
		<div class="footerSpace"></div>
		</div>
	
	
<!--Footer-->
<footer class="bg-inverse footer">


   
    <!--Call to action-->
    	
    <div class="text-center py-3 myFooter" >
        <ul class="list-unstyled list-inline mb-0">
            <li class="list-inline-item">
                <h5 class="mb-1">Register for free</h5>
            </li>
            <li class="list-inline-item">
                <a href="${createUrl}" class="btn btn-primary btn-rounded">Sign up!</a>
            </li>
        </ul>
    </div>
    <!--/.Call to action-->




    <!--Copyright-->
    <c:url value="/" var="homeUrl" />
    <div class="footer-copyright py-3 text-center">
    	<div>
	        © 2018 Copyright:
	        <a href="${homeUrl}">Book2Ball</a> Created By: Gene Tenorlas, Moghid Saad, Chathu Anthony, Shanu Shanu
        </div>
        <br/>
        <div id="socialMedia" style="text-align:center;">
	        <!-- Add font awesome icons -->
			<a href="#" class="fa fa-facebook"></a>
			<a href="#" class="fa fa-twitter"></a>
			<a href="#" class="fa fa-google"></a>
			<a href="#" class="fa fa-linkedin"></a>
			<a href="#" class="fa fa-youtube"></a>
			<a href="#" class="fa fa-instagram"></a>
        </div>
    </div>
    <!--/.Copyright-->

</footer>

<!--/.Footer-->
</body>
</html>