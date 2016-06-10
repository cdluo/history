$.post("/time", function(response) {
	var nations = JSON.parse(response);
	console.log(nations);
});