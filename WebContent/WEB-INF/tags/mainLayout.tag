<%@tag description="Shared Template" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@attribute name="title" fragment="true" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<meta name="msapplication-TileColor" content="#00aba9">
<meta name="theme-color" content="#ffffff">
<link rel="shortcut icon" href="<c:url value="/images/favicon/favicon.ico" />" />
<!-- <link rel="shortcut icon" href="#" />-->
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
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.6.3/css/font-awesome.min.css">

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
			      	<a class="navbar-brand" href="${homeUrl }" style="color:#008ecc;"><span><img style="margin-top:0 !important;padding-top:0px !important;" height='18' width='20' src="<c:url value="/images/smalllogo.gif" />" alt="Brand"></span>Book2Ball</a>


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
			   	  <c:url value="/aboutUs" var="aboutUsUrl" />
			      <li><a href="${aboutUsUrl}">About Us</a></li>
			      
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
					    	<li class="navbar-text" >   <c:if test="${facility!=null}"> ${facility.facilityName} / </c:if>        ${pageContext.request.userPrincipal.name}</li>
							<li><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out" style="color:orange"></span> Logout</a></li>
						</c:when>    
						<c:otherwise>
							<!-- Have user to be able to login and create account -->
					    	<c:url value="/createAccount" var="createUrl" />
					    	<li><a href="${createUrl}"><span class="gly-color glyphicon glyphicon-user"></span> Sign Up</a></li>
					    	<c:url value="/login" var="loginUrl" />
							<li><a href="${loginUrl}"><span class="gly-color glyphicon glyphicon-log-in"></span> Login</a></li>
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
 		<br/>
    	<div>
	        © 2018 Copyright:
	        <a href="${homeUrl}" style="color:#008ecc;">Book2Ball</a> Created By: Gene Tenorlas, Moghid Saad, Chathu Anthony, Shanu Shanu
        </div>
        <br/>
           <a href="#" class="fa fa-facebook"></a>
           	<a href="#" class="fa fa-twitter"></a>
			<a href="#" class="fa fa-google"></a>
			<a href="#" class="fa fa-linkedin"></a>
			<a href="#" class="fa fa-youtube"></a>
			<a href="#" class="fa fa-instagram"></a>

    </div>
    <!--/.Copyright-->

</footer>

<!--/.Footer-->
</body>
</html>