var curNations;

function passTime(){
	$.post("/time", function(response) {
		curNations = JSON.parse(response);
		console.log(curNations);
		setTimeout(passTime,1000);
	});
}

passTime();

