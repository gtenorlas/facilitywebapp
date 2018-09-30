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



function verifyWikiForm()
{
	//alert("im checking");
	//try to parse Date
	//try {
		var publishedDate = document.forms['wikiForm']['publishedDate'].value.toString(); //convert to string for checking
		//alert("Date " + publishedDate);
	//}catch(err) {
		//document.getElementById("error").innerHTML = "Invalid date format must be yyyy-mm-dd";
	    //alert("incorrect date");
	    //return false;
	//}
	
	//alert("validating now");
	
  // regular expression for date format yyyy-mm-dd
	var regEx = /^\d{4}-\d{2}-\d{2}$/;

  if(!publishedDate.match(regEx)) {
	document.getElementById("error").innerHTML = "Invalid date format must be yyyy-mm-dd";
    //alert("incorrect date");
    return false;
  }
  
  return true;
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
