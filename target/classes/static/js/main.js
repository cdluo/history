//Learned: 
//-Must add canvas element to doc itself before referencing it! Duh! (No sense of object until it physically exists)

'use strict';

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
		document.getElementById("year").innerHTML = year;
	});

	setTimeout(passTime,1000);	//Must match timer.schedule in Main
}

function drawWorld(){
	for(var i=0; i<curWorld.length; i++){
		var nation = curWorld[i];
		if(document.getElementById(nation.name)){
			drawNation(curWorld[i]);
		}else{
			var canv = document.createElement('canvas');
			canv.id = curWorld[i].name;

			//Actual height
			canv.width = 500;
			canv.height = 300;

			//Displayed height
			// canv.style.width = "49%";
			// canv.style.height = "49%";

			document.getElementById("canvasWorld").appendChild(canv);
			init(canv.id);
		}	
	}
}

//For EaselJS
function init(canvas){

	var stage = new createjs.Stage(canvas);
	var circle = new createjs.Shape();
	circle.graphics.beginFill("black").drawCircle(0, 0, 50);
	circle.x = 100;
	circle.y = 100;
	stage.addChild(circle);
	
	createjs.Tween.get(circle, { loop: true })
	  .to({ x: 400 }, 1000, createjs.Ease.getPowInOut(4))
	  .to({ alpha: 0, y: 175 }, 500, createjs.Ease.getPowInOut(2))
	  .to({ alpha: 0, y: 225 }, 100)
	  .to({ alpha: 1, y: 200 }, 500, createjs.Ease.getPowInOut(2))
	  .to({ x: 100 }, 800, createjs.Ease.getPowInOut(2));

	createjs.Ticker.setFPS(60);
	createjs.Ticker.addEventListener("tick", stage);
}

function drawNation(nation){
	var canvas = document.getElementById(nation.name);

	//TODO: Use EaselJS Here
	//
	//
}

/**
 * Adds a message to the chatbox, with the specified color
 * color must be "white", "green", "red", or "blue"
 */
function drawTimeline(){
	
	var color;

	for(var i=0; i<timeline.length; i++){
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

