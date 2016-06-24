<script src="https://code.createjs.com/easeljs-0.8.2.min.js"></script>

var curWorld;
var timeline;
var year;

function passTime(){
	$.post("/time", function(response) {
		curWorld = JSON.parse(response);
		drawWorld();
	});

	$.post("/timeline", function(response) {
		timeline = JSON.parse(response);
		if(timeline != null){
			drawTimeline();
		}
	});

	$.post("/year", function(response) {
		year = JSON.parse(response);
		console.log(year);
		document.getElementById("year").innerHTML = year;
	});

	setTimeout(passTime,1000);	//Must match timer.schedule in Main
}

function drawWorld(){
	for(i=0; i<curWorld.length; i++){
		var nation = curWorld[i];
		if(document.getElementById(nation.name)){
			drawNation(curWorld[i]);
		}else{
			var canv = document.createElement('canvas');
			canv.id = curWorld[i].name;

			//Actual height
			canv.width = 500;
			canv.height = 200;

			//Displayed height
			// canv.style.width = "49%";
			// canv.style.height = "49%";

			document.getElementById("canvasWorld").appendChild(canv);
		}	
	}
}

function drawNation(nation){
	var canvas = document.getElementById(nation.name);

	// Get size of canvas (not needed?)
	// var rect = canvas.getBoundingClientRect();
	// console.log(rect.top, rect.right, rect.bottom, rect.left);

	var ctx = canvas.getContext("2d");
	ctx.clearRect(0, 0, canvas.width, canvas.height);

	ctx.font = "32px Arial";
    ctx.fillStyle = "#000000";
	ctx.fillText(nation.name, 0, 32);

	// drawPerson(ctx);		FOR EASEL JS (DRAWING CANVAS NATION)
}

/**
 * Adds a message to the chatbox, with the specified color
 * color must be "white", "green", "red", or "blue"
 */
function drawTimeline(){

	var color;

	for(i=0; i<timeline.length; i++){
		var newPar = document.createElement("p");

		if(timeline[i].name == "Economic Downturn"){
			color = "red";
		}else if(timeline[i].name == "Economic Boom"){
			color = "green";
		}

		var text = timeline[i].year + ": " + timeline[i].name + " occured in " + timeline[i].to + ".";

		var textNode = document.createTextNode(text);
		newPar.appendChild(textNode);
		newPar.className += "mes " + color;
		document.getElementById("events").appendChild(newPar);
		scrollEvents();
	}

}

function scrollEvents(){
  var events = document.getElementById("events");

  if(events.scrollTop < (events.scrollHeight - events.offsetHeight)) {
    events.scrollTop = events.scrollTop + 10000
  }
}



passTime();

