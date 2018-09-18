<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<t:mainLayout>
    <jsp:body>
        <h1>Book2Ball Contact Us Form</h1>
         <form id="contactForm" class="form-horizontal" method="post" action="mailto:tenorlas@sheridancollege.ca?Subject=Book2BallInquiry" name="contactUsForm">
         
         		<div class="form-group">
                    <label for="Name" class="col-md-12 col-form-label">Name</label>
                    <div class="col-md-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                            <input type="text" id="Name" name="Name" class="form-control" placeholder="* Enter Name" required/>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="Name" class="col-md-12 col-form-label">Email</label>
                    <div class="col-md-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-envelope"></i></span>
                            <input type="text" id="Email" name="Email" class="form-control" placeholder="* Enter Email" required/>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="Phone" class="col-md-12 col-form-label">Phone Number</label>
                    <div class="col-md-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-earphone"></i></span>
                            <input type="tel" id="Phone" name="Phone" class="form-control" placeholder="* Enter Phone Number" required/>
                        </div>
                    </div>
                </div>
                
                <div class="form-group">
                    <label for="Message" class="col-md-12 col-form-label">Message</label>
                    <div class="col-md-12">
                        <div class="input-group">
                            <span class="input-group-addon"><i class="glyphicon glyphicon-comment"></i></span>
                            <textarea id="Message" name="Message" class="form-control" rows="5"></textarea>
                        </div>
                    </div>
                </div>
                
                <div class="form-group row">
					      <div class="offset-md-6 col-md-12">
					        <button type="submit" class="btn btn-primary" >Send Inquiry</button>
					      </div>
				</div>
         </form>
    </jsp:body>
</t:mainLayout>