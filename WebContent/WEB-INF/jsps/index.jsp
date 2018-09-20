<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
<jsp:body>

 <div class="container">

      <!-- Jumbotron Header -->
      <header class="jumbotron">
        <h1 class="display-3">Register Your Facility Now!</h1>
        <p class="lead">Get more customers by registering your facility and be a part of the Book2Ball family.</p>
        <!-- Have user to be able to login and create account -->
		<c:url value="/createAccount" var="createUrl" />
        <a href="${createUrl}" class="btn btn-primary btn-lg">Register Now!</a>
      </header>

      <!-- Page Features -->
      <div class="row text-center">  <!-- /.row -->
        <div class="col-sm-6 col-md-4">
	       <div class="thumbnail">
		      <img src="<c:url value="/images/ball2.jpg" />" alt="Basketball 2">
		      <div class="caption">
		        <h3>Affiliated</h3>
		        <p>Proven way to grow your business.</p>
		        <c:url value="/facilities" var="facilitiesUrl" />
		        <p><a href="${facilitiesUrl}" class="btn btn-primary" role="button">Our Facilities</a></p>
	     	 </div>
    		</div>
     	 </div>
     	 
     	 <div class="col-sm-6 col-md-4">
	       <div class="thumbnail">
		      <img src="<c:url value="/images/ball3.jpg" />" alt="Basketball 3">
		      <div class="caption">
		        <h3>More Information</h3>
		        <p>Keep in touch to get more details.</p>
		        <c:url value="/contactUs" var="contactUsUrl" />
		        <p><a href="${contactUsUrl }" class="btn btn-primary" role="button">Contact Us</a></p>
	     	 </div>
    		</div>
     	 </div>
     	 
     	 <div class="col-sm-6 col-md-4">
	       <div class="thumbnail">
		      <img src="<c:url value="/images/ball4.jpg" />" alt="Basketball 4">
		      <div class="caption">
		        <h3>Our Social Media</h3>
		        <p>See us in social media for up to date information.</p>
		        <p><a href="#socialMedia" class="btn btn-primary" role="button">Follow Us</a></p>
	     	 </div>
    		</div>
     	 </div>
     	 
     

  
      </div>
      <!-- /.row -->

 </div>
 <!-- /.container -->
   
</jsp:body>
</t:mainLayout>