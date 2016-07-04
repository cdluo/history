///////////////////////
// Window Animations //
///////////////////////

$( "#addNation" ).click(function() {

	var button = document.getElementById("addNation");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(-410px)";
		button.innerHTML = "Cancel";
	}else{
		button.classList.add("closed");
		document.getElementById("addNationWindow").style.transform = "translateY(0px)";
		button.innerHTML = "Add Nation"
	}
});

$( "#addEvent" ).click(function() {

  var button = document.getElementById("addEvent");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(-410px)";
		button.innerHTML = "Cancel";
	}else{
		button.classList.add("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(0px)";
		button.innerHTML = "Add Event";
	}
});

$( "#stats" ).click(function() {

  var button = document.getElementById("stats");

	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		document.getElementById("statsWindow").style.transform = "translateY(-410px)";
	}else{
		button.classList.add("closed");
		document.getElementById("statsWindow").style.transform = "translateY(0px)";
	}
});

//////////////////////////
// Event DropDown Menus //
//////////////////////////

$( "#typeDropBtn" ).click(function() {
	var dropDown = document.getElementById("typeDropDown");

	if(dropDown.classList.contains("closed")){
		dropDown.style.visibility = "visible";
		dropDown.classList.remove("closed");
	}else{
		dropDown.style.visibility = "hidden";
		dropDown.classList.add("closed");
	}
});

$( ".typeRadio" ).click(function() {

	var label = document.getElementById("typeLabel");
	var text = $(this).text();
	label.innerHTML = text;

	var dropDown = document.getElementById("typeDropDown");
	dropDown.style.visibility = "hidden";
	dropDown.classList.add("closed");
});

$( "#nationDropBtn" ).click(function() {
	var dropDown = document.getElementById("nationDropDown");

	if(dropDown.classList.contains("closed")){
		dropDown.style.visibility = "visible";
		dropDown.classList.remove("closed");
	}else{
		dropDown.style.visibility = "hidden";
		dropDown.classList.add("closed");
	}

});

$( "#nationDropDown" ).on("click", ".toRadio", function() {

	console.log("clicked");

	var label = document.getElementById("nationLabel");
	var text = $(this).text();
	label.innerHTML = text;

	var dropDown = document.getElementById("nationDropDown");
	dropDown.style.visibility = "hidden";
	dropDown.classList.add("closed");

});

////////////////////
// Submit Buttons //
////////////////////

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
		document.getElementById("addNation").innerHTML = "Add Nation"
	})
});

$("#newEventSubmit").click(function(){

	console.log("Submitting new Event...");

	var typeJSON = ($('#typeLabel').text());
	var yearJSON = $("#submitYear").val();
	var toJSON = ($('#nationLabel').text());

	var data = {type:typeJSON, year:yearJSON, to:toJSON};

	$.post("/newEvent", data, function(response) {
		//Do stuff after adding new Event

		document.getElementById("addEvent").classList.add("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(0px)";
		document.getElementById("addEvent").innerHTML = "Add Event";
	})
});