var curWorld;

function passTime(){
	$.post("/time", function(response) {
		curWorld = JSON.parse(response);
		drawWorld();
		setTimeout(passTime,1000);
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
			document.body.appendChild(canv);
		}	
	}
}

function drawNation(nation){
	console.log('Drawing ' + nation.name);
	var canvas = document.getElementById(nation.name);
	canvas.style.width = '300px';
	canvas.style.height = '200px';
	
	// Get size of canvas (not needed?)
	// var rect = canvas.getBoundingClientRect();
	// console.log(rect.top, rect.right, rect.bottom, rect.left);

	var ctx = canvas.getContext("2d");
	ctx.clearRect(0, 0, canvas.width, canvas.height);

	ctx.font = "16px Arial";
    ctx.fillStyle = "#000000";
	ctx.fillText(nation.name, 0, 20);

	ctx.stroke();
}

passTime();

