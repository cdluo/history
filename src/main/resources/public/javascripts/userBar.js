/*
 * This Javascript file controls everything to do with the userbar,
 * including its window animations, dropdown menus, and post requests
 * to the server.
 * 
 * @author Chris Luo
 */

///////////////////////
// Window Animations //
///////////////////////

$( "#addNation" ).click(function() {
	var button = document.getElementById("addNation");
	var buttonWindow = document.getElementById("addNationWindow");

	controlWindow(button, buttonWindow, "Add Nation");
});

$( "#addEvent" ).click(function() {
  var button = document.getElementById("addEvent");
  var buttonWindow = document.getElementById("addEventWindow");

	controlWindow(button, buttonWindow, "Add Event");
});

$( "#stats" ).click(function() {
  var button = document.getElementById("stats");
  var buttonWindow = document.getElementById("statsWindow")

  controlWindow(button, buttonWindow, "Stats");
});

function controlWindow(button, buttonWindow, label){
	if(button.classList.contains("closed")){
		button.classList.remove("closed");
		buttonWindow.style.transform = "translateY(-410px)";
		button.innerHTML = "Cancel";
	}else{
		button.classList.add("closed");
		buttonWindow.style.transform = "translateY(0px)";
		button.innerHTML = label;
	}
}

//////////////////////////
// Event DropDown Menus //
//////////////////////////

$( "#typeDropBtn" ).click(function() {
	var dropWindow = document.getElementById("typeDropDown");
	dropDown(dropWindow);
});

$( "#nationDropBtn" ).click(function() {
	var dropWindow = document.getElementById("nationDropDown");
	dropDown(dropWindow);
});

$( ".typeRadio" ).click(function() {

	var label = document.getElementById("typeLabel");
	var text = $(this).text();
	var dropDown = document.getElementById("typeDropDown");

	confirmRadio(label, text, dropDown);

});

$( "#nationDropDown" ).on("click", ".toRadio", function() {

	var label = document.getElementById("nationLabel");
	var text = $(this).text();
	var dropDown = document.getElementById("nationDropDown");

	confirmRadio(label, text, dropDown);
});

function dropDown(dropWindow){
	if(dropWindow.classList.contains("closed")){
		dropWindow.style.visibility = "visible";
		dropWindow.classList.remove("closed");
	}else{
		dropWindow.style.visibility = "hidden";
		dropWindow.classList.add("closed");
	}
}

function confirmRadio(label, text, dropDown){
	label.innerHTML = text;

	dropDown.style.visibility = "hidden";
	dropDown.classList.add("closed");
}

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

		document.getElementById("addEvent").classList.add("closed");
		document.getElementById("addEventWindow").style.transform = "translateY(0px)";
		document.getElementById("addEvent").innerHTML = "Add Event";
	})
});