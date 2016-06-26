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
			init(canv.id, curWorld[i]);		//Instantiate a stage for the new canvas.
		}	
	}
}

/*
	Initializing EaselJS stage for the passed in canvas.
*/
function init(canvas, nation){

	var stage = new createjs.Stage(canvas);
	canvStgMap[canvas] = stage;

	createjs.Ticker.on("tick", handleTick);
  createjs.Ticker.framerate = 30;
    function handleTick(event) {
        stage.update(event);
    }

  // Pop
	var ff6Person = new Image();
	ff6Person.src = "images/FF6Sprite.png";
	ff6Person.onload = addPerson(stage, ff6Person);

	var popLabel = new createjs.Text(nation.pop, "24px Arial", "#000000");
	popLabel.x = 125;
	popLabel.y = 30;

	popLabel.name = "popLabel";
	stage.addChild(popLabel);

	// Gdp
	var dollar = new Image();
	dollar.src = "images/dollarSpriteSheet.png";
	dollar.onload = addDollar(stage, dollar);

	var gdpLabel = new createjs.Text(nation.gdp, "24px Arial", "#000000");
	gdpLabel.x = 125;
	gdpLabel.y = 90;

	gdpLabel.name = "gdpLabel";
	stage.addChild(gdpLabel);

	//Social
	var social = new Image();
	social.src = "images/Social.png";
	social.onload =  addSocial(stage,social);

	var socialLabel = new createjs.Text(nation.social, "24px Arial", "#000000");
	socialLabel.x = 125;
	socialLabel.y = 150;

	socialLabel.name = "socialLabel";
	stage.addChild(socialLabel);

	//Living
	var living = new Image();
	living.src = "images/Living.png";
	living.onload =  addLiving(stage,living);

	var livingLabel = new createjs.Text(nation.living, "24px Arial", "#000000");
	livingLabel.x = 125;
	livingLabel.y = 230;

	livingLabel.name = "livingLabel";
	stage.addChild(livingLabel);
}

/*
	Adds a person based on a spritesheet image for the passed in stage.
*/
function addPerson(stage, image){

	var person = {
	  images: [image],
	  frames: {width:20, height:30, regX:-10, regY:0, spacing:0, margin:0},
	  animations: {
	  	one: [0],	
	  	two: [1],
	  	three: [2],
	    walk: [0,2,"walk",0.33]
    }
  };

  var spriteSheet = new createjs.SpriteSheet(person);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = 2.5;
  sprite.scaleY = 2.5;
  sprite.gotoAndPlay("walk");

  sprite.name = "person";
  stage.addChild(sprite);
}

/*
	Adds a dollar sprite
*/
function addDollar(stage, image){

	var data = {
	  images: [image],
	  frames: {width:50, height:20, regX:-3, regY:-50, spacing:5, margin:0},
	  animations: {
	    shine: [0,2,"shine",0.2]
    }
  };

  var spriteSheet = new createjs.SpriteSheet(data);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = 1.75;
  sprite.scaleY = 1.75;
  sprite.gotoAndPlay("shine");

  sprite.name = "dollar";
  stage.addChild(sprite);
}

/*
	Adds a speech bubble
*/
function addSocial(stage, image){

	var data = {
	  images: [image],
	  frames: {width:71, height:61, regX:0, regY:-100, spacing:0, margin:0},
	  animations: {
	  	heart: [8,9,"heart",0.1],	
	  	dot: {
            frames: [0,10,3,7],
            next: "dot",
            speed: 0.05
      },
      anger: {
            frames: [6,4,2,5,1],
            next: "anger",
            speed: 0.4
      }
    }
  };

  var spriteSheet = new createjs.SpriteSheet(data);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = 1.25;
  sprite.scaleY = 1.25;
  sprite.gotoAndPlay("heart");

  sprite.name = "social";
  stage.addChild(sprite);
}

/*
	Adds a Tree.
*/
function addLiving(stage, image){

	var data = {
	  images: [image],
	  frames: {width:96, height:96, regX:-3, regY:-200, spacing:0, margin:0},
	  animations: {
	  	spring: [0,2,"spring",0.05],	
	  	fall: [3,4,"fall",0.05],	
	  	winter:[5]
    }
  };

  var spriteSheet = new createjs.SpriteSheet(data);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = 1;
  sprite.scaleY = 1;
  sprite.gotoAndPlay("spring");

  sprite.name = "living";
  stage.addChild(sprite);
}


/*
	Draws a nation as part of drawWorld().
*/
function drawNation(nation){
	var stage = canvStgMap[nation.name];

	//Use for updating the stuff you instantiated in init.
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

