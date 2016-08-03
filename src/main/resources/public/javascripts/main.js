// Reflections: 

// -Must add canvas element to doc itself before referencing it! Duh! (No sense of object until it literally exists)
// -Only set the ticker on instantiation. Basically, a lot of createJS is like Java! (More than you would think at first)
// -If a dumb thing is taking a long time, it's probably something wrong with your assumptions.

'use strict';

var curWorld;
var timeline;
var year;
var canvStgMap = {};		//Associative Array for linking a canvas id to its stage. ([K:canvas]||[V:stage])
var timeout;
var clockStage;		//For controlling the top clock animation, will be instantiated at the bottom.
var timeRunning;	//based on whether the clockstage is running or not (so it's possible to be not running on page load)

/*
	Central time keeping function. Essentially the page's Main().
*/
function passTime(){

	var clock = document.getElementById("clock");

	if(clock.classList.contains("running")){
		timeRunning = true;
	}else{
		timeRunning = false;
	}

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

	if(timeRunning){
		timeout = setTimeout(passTime, 2000);	//Should match TIMESPEED constant in Main on the backend.
	}
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
			canv.style.border = "1px solid #000000";
			canv.style.margin = "8px 8px";

			//Actual height
			canv.width = 450;
			canv.height = 300;

			document.getElementById("canvasWorld").appendChild(canv);
			init(canv.id, curWorld[i]);		//Instantiate a stage for the new canvas.

			//Add new nation to list in the events form
			var nationsDrop = document.getElementById("nationDropDown");

			var newInput = document.createElement('p');
			newInput.classList.add("toRadio");
			newInput.innerHTML = curWorld[i].name;
			nationsDrop.appendChild(newInput);
		}	
	}
}

/*
	Initializing EaselJS stage for the passed in canvas.
*/
function init(canvas, nation){

	var stage = new createjs.Stage(canvas);
	canvStgMap[String(canvas)] = stage;		// Map the stage to its canvas in the global map. 

	createjs.Ticker.on("tick", handleTick);
  createjs.Ticker.framerate = 30;
  function handleTick(event) {
      stage.update(event);
  }

  //////////
  // Name //
  //////////

	var graphics = new createjs.Graphics();
  graphics.setStrokeStyle("0");
  graphics.beginStroke("#000000");
	graphics.beginFill("#9FAEB3");
	graphics.drawRect(0,0,500,30);

	var header = new createjs.Shape(graphics);
  header.x = 0;
  header.y = 0;
  stage.addChild(header);

  var name = new createjs.Text(nation.name, "24px Garamond", "#000000");
	name.x = 10;
	name.y = 3;

	name.name = "name";
	stage.addChild(name);

	///////////////////
	// Progress Bars //
	///////////////////

	var socialBar = getProgressBar(stage, nation.social);
	var socialBar1 = new createjs.Shape(socialBar);
  socialBar1.x = 125;
  socialBar1.y = 160;

	socialBar1.name = "socialBar";
	stage.addChild(socialBar1);

	var livingBar = getProgressBar(stage, nation.living);
	var livingBar1 = new createjs.Shape(livingBar);
  livingBar1.x = 125;
  livingBar1.y = 230;

	livingBar1.name = "livingBar";
	stage.addChild(livingBar1);

	/////////
  // Pop //
  /////////

	var ff6Person = new Image();
	ff6Person.src = "images/FF6Sprite.png";

	var data;

  ff6Person.onload = function(){

  	data = {
	  	images: [ff6Person],
	  	frames: {width:20, height:30, regX:-17, regY:-17, spacing:0, margin:0},
	  	animations: {
	  		one: [0],	
	  		two: [1],
	  		three: [2],
	    	walk: [0,2,"walk",0.33]
    	}
  	};
  	addSprite(stage, data, 2.25, 2.25, "walk", "person");
  };

	var popLabel = new createjs.Text(nation.pop, "24px Arial", "#000000");
	popLabel.x = 125;
	popLabel.y = 55;

	popLabel.name = "popLabel";
	stage.addChild(popLabel);

	/////////
	// Gdp //
	/////////

	var dollar = new Image();
	dollar.src = "images/dollarSpriteSheet.png";

	dollar.onload = function(){
		data = {
		  images: [dollar],
		  frames: {width:50, height:20, regX:-10, regY:-60, spacing:5, margin:0},
		  animations: {
		    shine: [0,2,"shine",0.2]
	    }
	  };

	  addSprite(stage, data, 1.75, 1.75, "shine", "dollar");
	};


	var gdpLabel = new createjs.Text(nation.gdp, "24px Arial", "#000000");
	gdpLabel.x = 125;
	gdpLabel.y = 105;

	gdpLabel.name = "gdpLabel";
	stage.addChild(gdpLabel);

	////////////
	// Social //
	////////////

	var social = new Image();
	social.src = "images/Social.png";


	social.onload = function(){

		data = {
		  images: [social],
		  frames: {width:71, height:61, regX:-20, regY:-145, spacing:0, margin:0},
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

	  addSprite(stage, data, 1, 1, "heart", "social");
	};

	////////////
	// Living //
	////////////

	var living = new Image();
	living.src = "images/Living.png";

	living.onload = function(){
		data = {
		  images: [living],
		  frames: {width:96, height:96, regX:-28, regY:-265, spacing:0, margin:0},
		  animations: {
		  	spring: [0,2,"spring",0.05],	
		  	fall: [3,4,"fall",0.05],	
		  	winter:[5]
	    }
	  };

	  addSprite(stage, data, 0.8, 0.8, "spring", "living");
	};
}

/*
	Creates a progressBar graphics instance that can be added through
	the Shape class.

	@param percent: how full this progress bar should appear.
*/
function getProgressBar(stage, percent){
	var graphics = new createjs.Graphics();
	graphics.setStrokeStyle(1);

	graphics.beginStroke("#000000");
	graphics.beginFill("white");
	graphics.drawRoundRect(0,0,300,30,10);
	graphics.endStroke();
	graphics.endFill;

	var greenLength = 3 * percent;
	graphics.beginStroke("#000000");
	graphics.beginFill("green");
	graphics.drawRoundRect(0,0,greenLength,30,10);

	return graphics;
}

/*
	Adds a sprite to the given stage

	@param stage: the stage to add the sprite to
	@param data: the spritesheet data 
	@param scaleX: initial x scale size
	@param scaleY: intial y scale size
	@param name: name of the sprite
*/
function addSprite(stage, data, scaleX, scaleY, startAni, name){
	var spriteSheet = new createjs.SpriteSheet(data);

  var sprite = new createjs.Sprite(spriteSheet);
  sprite.scaleX = scaleX;
  sprite.scaleY = scaleY;
  sprite.gotoAndPlay(startAni);
  sprite.stop();

  sprite.name = name;
  stage.addChild(sprite);
}

/*
	Draws a nation as part of drawWorld().
*/
function drawNation(nation){
	var stage = canvStgMap[nation.name];

	// Progress Bars
	stage.removeChild(stage.getChildByName("socialBar"));

	var socialBar = getProgressBar(stage, nation.social);
	var socialBar1 = new createjs.Shape(socialBar);
  socialBar1.x = 125;
  socialBar1.y = 160;

	socialBar1.name = "socialBar";
	stage.addChild(socialBar1);

	stage.removeChild(stage.getChildByName("livingBar"));

	var livingBar = getProgressBar(stage, nation.living);
	var livingBar1 = new createjs.Shape(livingBar);
  livingBar1.x = 125;
  livingBar1.y = 230;

	livingBar1.name = "livingBar";
	stage.addChild(livingBar1);

	//Labels
	var popLabel = stage.getChildByName("popLabel");
	popLabel.text = nation.pop;

	var gdpLabel = stage.getChildByName("gdpLabel");
	gdpLabel.text = nation.gdp;

	//Animations
	var bubble = stage.getChildByName("social");
	if(nation.social > 66){
		if(bubble.currentAnimation != "heart"){
			bubble.gotoAndPlay("heart");
		}
	}else if(nation.social > 33){
		if(bubble.currentAnimation != "dot"){
			bubble.gotoAndPlay("dot");
		}
	}else{
		if(bubble.currentAnimation != "anger"){
			bubble.gotoAndPlay("anger");
		}
	}

	var tree = stage.getChildByName("living");
	if(nation.living > 66){
		if(tree.currentAnimation != "spring"){
			tree.gotoAndPlay("spring");
		}
	}else if(nation.living > 33){
		if(tree.currentAnimation != "fall"){
			tree.gotoAndPlay("fall");
		}
	}else{
		if(tree.currentAnimation != "winter"){
			tree.gotoAndPlay("winter");
		}
	}
}

/**
 * Updates the message box. The color variable options
 * are defined in main.css.
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
	Pauses the animations in each nation window.
*/
function pauseNationAnimations(){
	for (var key in canvStgMap) {
		
		var stage = canvStgMap[key];

		stage.getChildByName("person").stop();
		stage.getChildByName("dollar").stop();
		stage.getChildByName("social").stop();
		stage.getChildByName("living").stop();
	}
}

/*
	Plays the animations in each nation window.
*/
function playNationAnimations(){
	for (var key in canvStgMap) {
		
		var stage = canvStgMap[key];

		stage.getChildByName("person").play();
		stage.getChildByName("dollar").play();
		stage.getChildByName("social").play();
		stage.getChildByName("living").play();
	}
}

/*
	Executed on page load.
*/
function initFlowerClock(){
	clockStage = new createjs.Stage(document.getElementById("clock"));
	createjs.Ticker.on("tick", handleTick);
  createjs.Ticker.framerate = 30;
    function handleTick(event) {
        clockStage.update(event);
    }

	var flower = new Image();
	flower.src = "images/FlowerSheet.png";

	flower.onload = function(){
			var data = {
			  images: [flower],
			  frames: {width:50, height:50, regX:0, regY:0, spacing:0, margin:0},
			  animations: {
			  	life: [0,30,"life",0.2],	
		    }
		};

		addSprite(clockStage, data, 1, 1, "life", "flower");
		clockStage.getChildByName("flower").stop();
	};
}

/*
	Jquery Handler for the flowerclock.
*/
$( "#clock" ).click(function() {

	if(this.classList.contains("running")){
		clearTimeout(timeout);
		this.classList.remove("running");
		timeRunning = false;

		$.post("/stopTime", function(response) {
			var year = JSON.parse(response);
			console.log("Time stopped on " + year + ".");
			clockStage.getChildByName("flower").stop();
		});

		$(document).ajaxStop(function() {
			pauseNationAnimations();
		});
	}else{
		timeout = setTimeout(passTime,1000);
		this.classList.add("running");
		timeRunning = true;

		$.post("/resumeTime", function(response) {
			var year = JSON.parse(response);
			console.log("Time resumed on " + year + ".");
			clockStage.getChildByName("flower").play("life");
		});

		$(document).ajaxStop(function() {
			playNationAnimations();
		});
	}
});

//////////////////////////////////
//////////// Start! //////////////
//////////////////////////////////

initFlowerClock();
window.onload = function(){
	passTime();
};
