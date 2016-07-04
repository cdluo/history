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
		  <button id="typeDropBtn" class="dropBtn">Type:</button>

		  <div class = "closed" id="typeDropDown">
		  	<p class = "typeRadio">Natural Disaster</p>
		  	<p class = "typeRadio">Election</p>
		  	<p class = "typeRadio">Economic Downturn</p>
		  	<p class = "typeRadio">Economic Boom</p>
		  </div>

		  <p id="typeLabel"> --- </p>

		  <p style = "position:relative; left:75px; margin:5px 0; display:inline-block">Year:</p>
		  <input type = "number" name="year" id="submitYear" class = "windowInput">

	  	<button id = "nationDropBtn" class = "dropBtn">To: </button>

	  	<div class = "closed" id="nationDropDown">
	  		<!-- Nation labels are generated here in main.js-->
	  	</div>

			<p id="nationLabel"> --- </p>

		  <button id="newEventSubmit" class = "windowSubmit" style = "top:0px"> Submit </button>
		</div>
	</div>

	<button class = "userBtn closed" id="stats"> Stats/Graphs </button>
	<div class = "userWindow" id = "statsWindow">
		<p>Stats/Graphs</p>
	</div>
</div>

<br>

</#assign>
<#include "main.ftl">
