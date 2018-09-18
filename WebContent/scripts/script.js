function verify() {
	var password1 = document.forms['form']['password'].value;
	var password2 = document.forms['form']['verifyPassword'].value;
	if (password1 == null || password1 == "" || password1 != password2) {
		document.getElementById("error").innerHTML = "Please check your passwords";
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
