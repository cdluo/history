var curWorld;

function passTime(){
	$.post("/time", function(response) {
		curWorld = JSON.parse(response);
		console.log(curWorld);
		drawWorld();
		setTimeout(passTime,1000);	//Must match timer.schedule in Main
	});
}

function drawWorld(){
	for(i=0; i<curWorld.length; i++){
		var nation = curWorld[i];
		if(document.getElementById(nation.name)){
			drawNation(curWorld[i]);
		}else{
			var canv = document.createElement('canvas');
			canv.id = curWorld[i].name;
			canv.width = 400;
			canv.height = 200;
			document.body.appendChild(canv);
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

	var popLength = Math.floor(nation.pop/100);
	var gdpLength = Math.floor(nation.gdp/100);
	var socialLength = nation.social*3;
	var livingLength = nation.living*3;

	ctx.beginPath();
	ctx.moveTo(0,50);
	ctx.lineTo(popLength,50);
	ctx.strokeStyle = "black";

	ctx.stroke();

	ctx.beginPath();
	ctx.moveTo(0,85);
	ctx.lineTo(gdpLength,85);
	ctx.strokeStyle = "green";

	ctx.stroke();

	ctx.beginPath();
	ctx.moveTo(0,120);
	ctx.lineTo(socialLength,120);
	ctx.strokeStyle = "pink";

	ctx.stroke();

	ctx.beginPath();
	ctx.moveTo(0,155);
	ctx.lineTo(livingLength,155);
	ctx.strokeStyle = "blue";

	ctx.stroke();
}

passTime();

