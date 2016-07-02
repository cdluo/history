$( "#addNation" ).click(function() {
	console.log("Adding Nation");

	var button = document.getElementById("addNation");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(-310px)";
	}else{
		button.classList.add("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(0px)";
	}
});

$( "#addEvent" ).click(function() {
  console.log("Adding Event...");

  var button = document.getElementById("addEvent");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(-310px)";
	}else{
		button.classList.add("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(0px)";
	}
});

$( "#stats" ).click(function() {
  console.log("Showing Stats...");

  var button = document.getElementById("stats");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("statsWindow").style.transform = "translateY(-310px)";
	}else{
		button.classList.add("closed");
		document.getElementById("statsWindow").style.transform = "translateY(0px)";
	}
});