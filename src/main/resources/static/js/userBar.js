//Window Animations

$( "#addNation" ).click(function() {
	console.log("Adding Nation");

	var button = document.getElementById("addNation");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(-410px)";
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
		document.getElementById("addEventWindow").style.transform = "translateY(-410px)";
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
		document.getElementById("statsWindow").style.transform = "translateY(-410px)";
	}else{
		button.classList.add("closed");
		document.getElementById("statsWindow").style.transform = "translateY(0px)";
	}
});

//Submit Buttons

$("#newNationSubmit").click(function(){

	console.log("Submitting new Nation...");

	var nameJSON = $("#name").val();
	var popJSON = $("#pop").val();
	var gdpJSON = $("#gdp").val();
	var socialJSON = $("#social").val();
	var livingJSON = $("#living").val();

	var data = {name:nameJSON, pop:popJSON, gdp:gdpJSON, social:socialJSON, living:livingJSON};

	$.post("/newNation", data, function(response) {
		//Do stuff after adding new nation

		document.getElementById("addNation").classList.add("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(0px)";
	})
});

$("#newEventSubmit").click(function(){

	console.log("Submitting new Nation...");

	var typeJSON = ($('input[name=type]:checked').val());
	var yearJSON = $("#submitYear").val();
	var toJSON = ($('input[name=nations]:checked').val());

	console.log(yearJSON);

	var data = {type:typeJSON, year:yearJSON, to:toJSON};

	$.post("/newEvent", data, function(response) {
		//Do stuff after adding new Event

		document.getElementById("addEvent").classList.add("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(0px)";
	})
});