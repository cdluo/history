<#assign content>

<div id="header">
	<h1> ${header}: </h1> <h1 id="year"> X </h1>
</div>

<div id="events">
	<p class="mes black">Welcome to the New World</p>
</div>

<div id="canvasWorld">
</div>

<div id = "userBar">

	<button class = "userBtn closed" id="addNation"> Add Nation </button>
	<div class = "userWindow" id = "addNationWindow">
		<div id = "newNationForm">
		  Name:
		  <input type = "text" name="name" id="name" class = "windowInput"> <br>
		  Population:
		  <input type = "number" name="pop" id="pop" class = "windowInput"> <br>
		  GDP:
		  <input type = "number" name="gdp" id="gdp" class = "windowInput"> <br>
		  Social Stability:
		  <input type = "number" name="social" id="social" class = "windowInput"> <br>
		  Standard of Living:
		  <input type = "number" name="living" id="living" class = "windowInput"> <br>

		  <button id = "newNationSubmit" class = "windowSubmit"> Submit </button>
		</div>
	</div>

	<button class = "userBtn closed" id="addEvent"> Add Event </button>
	<div class = "userWindow" id = "addEventWindow">
		<div id = "newEventForm">
		  <p style="margin:0 0; font:24px Arial">Type:</p>

		  Natural Disaster<input type = "radio" name="type" class = "windowInput" value = "Natural Disaster" checked><br> 
		  Election<input type = "radio" name="type" class = "windowInput" value = "Election"><br>
		  Economic Downturn<input type = "radio" name="type" class = "windowInput" value = "Economic Downturn"><br>
		  Economic Boom<input type = "radio" name="type" class = "windowInput" value = "Economic Boom"><br>

		  <br>

		  Year:
		  <input type = "number" name="year" id="year" class = "windowInput"> <br> 

		  <br>

		  <div id="nationsList">
		  	To: <br>
			</div>

		  <button id="newEventSubmit" class = "windowSubmit"> Submit </button>
		</div>
	</div>

	<button class = "userBtn closed" id="stats"> Stats/Graphs </button>
	<div class = "userWindow" id = "statsWindow">
		Stats
	</div>
</div>

<br>

</#assign>
<#include "main.ftl">
