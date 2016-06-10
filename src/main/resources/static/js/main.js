function passTime(){
	$.post("/time", function(response) {
		var nations = JSON.parse(response);
		console.log(nations);
		setTimeout(passTime,1000);
	});
}

passTime();

