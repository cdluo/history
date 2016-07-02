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
		<p>New Nation</p>
	</div>

	<button class = "userBtn closed" id="addEvent"> Add Event </button>
	<div class = "userWindow" id = "addEventWindow">
		<p>New Event</p>
	</div>

	<button class = "userBtn closed" id="stats"> Stats/Graphs </button>
	<div class = "userWindow" id = "statsWindow">
		<p>New Stats</p>
	</div>
</div>

<br>

</#assign>
<#include "main.ftl">
