//Learned: 

// -Must add canvas element to doc itself before referencing it! Duh! (No sense of object until it physically exists)
// -Only set the ticker on instantiation. Basically, a lot of it is like Java! (More than you would think at first)
// -If a dumb thing is taking a long time, it's probably something wrong with your assumption.

'use strict';

var curWorld;
var timeline;
var year;
var canvStgMap = new Array();		//Associative Array (Dictionary) for linking a canvas id to its stage.

/*
	Central time keeping function. Essentially the page's Main().
*/
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

/*
	Called during each post to "/time".
*/
function drawWorld(){
	for(var i=0; i<curWorld.length; i++){
		var nation = curWorld[i];
		if(document.getElementById(nation.name)){
			//canvas already exists, draw on it.
			drawNation(curWorld[i]);
		}else{
			//make a new canvas
			var canv = document.createElement('canvas');
			canv.id = curWorld[i].name;

			//Actual height
			canv.width = 500;
			canv.height = 300;

			//Displayed height
			// canv.style.width = "49%";
			// canv.style.height = "49%";

			document.getElementById("canvasWorld").appendChild(canv);
			init(canv.id);		//Instantiate a stage for the new canvas.
		}	
	}
}

/*
	Initializing EaselJS stage for the passed in canvas.
*/
function init(canvas){

	var stage = new createjs.Stage(canvas);
	canvStgMap[canvas] = stage;

	createjs.Ticker.on("tick", handleTick);
  createjs.Ticker.framerate = 30;
    function handleTick(event) {
        stage.update(event);
    }

	var ff6Person = new Image();
	ff6Person.src = "images/FF6Sprite.png";
	ff6Person.onload = addPerson(stage, ff6Person);

}

/*
	Adds a person based on a spritesheet image for the passed in stage.
*/
function addPerson(stage, image){

	var person = {
	  images: [image],
	  frames: {width:20, height:30, regX:0, regY:0, spacing:0, margin:0},
	  animations: {
	  	one: [0],	
	  	two: [1],
	  	three: [2],
	    walk: [0,2,"walk",0.33]
    }
  };

  var spriteSheet = new createjs.SpriteSheet(person);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = 3;
  sprite.scaleY = 3;
  sprite.gotoAndPlay("walk");

  stage.addChild(sprite);
}

/*
	Draws a nation as part of drawWorld().
*/
function drawNation(nation){
	var stage = canvStgMap[nation.name];
	// stage.clear();

  //Other EaselJS Stuff

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

/*
	Scrolls the event log window.
*/
function scrollEvents(){
  var events = document.getElementById("events");

  if(events.scrollTop < (events.scrollHeight - events.offsetHeight)) {
    events.scrollTop = events.scrollTop + 10000
  }
}

/*
	Begins the script like the main method.
*/
passTime();

