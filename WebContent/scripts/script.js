/*
 * To validate username and passwords during the account registration
 */
function verify() {
	var username = document.forms['form']['username'].value;
	var password1 = document.forms['form']['password'].value;
	var password2 = document.forms['form']['verifyPassword'].value;
	if (username == null) {
		document.getElementById("error").innerHTML = "Please check your username.";
		return false;
	}else if (password1 == "" || password1 != password2) {
		document.getElementById("error").innerHTML = "Please check your passwords.";
		return false;
	} 
}

/*
 * Show filter section in the Bookings.jsp page 
 */
function showFilter(){
    var x = document.getElementById("searchBookings");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

/*
 * Confirm delete court
 */
function deleteFunction(url) {
	var r = confirm("Continue to delete the court?");
	if (r == true) {
	    txt = "You pressed OK!";
	    //window.location.href = url;
	    return true;
	} else {
	    txt = "You pressed Cancel!";
	   // window.location.href = '/courts';
	    return false;
	}
}

/*
 * Confirm cancel booking
 */
function cancelFunction(url) {
	var r = propmt("Please provide the reason of cancalation");
	if (r != null){
		document.getElementById("reason").innerHTML = r;
		return r;
	}
//	if (r == true) {
//	    txt = "You pressed OK!";
//	    //window.location.href = url;
//	    return true;
//	} else {
//	    txt = "You pressed Cancel!";
//	   // window.location.href = '/courts';
//	    return false;
//	}
}
