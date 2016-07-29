<#assign content>

<div id="header">
  <h1> ${header}: </h1> <h1 id="year"> X </h1> <canvas id="clock" width = "50px" height = "50px" class = ""> </canvas>
</div>

<div id="events">
  <p class="mes black">Welcome to the New World</p>
</div>

<div id="canvasWorld">
  <!-- Each nation will get a canvas inserted here -->
  <!-- Drawing done by CreateJS in main.js -->
</div>

<div id = "userBar">

  <!-- New Nation -->
  <button class = "userBtn closed" id="addNation" style = "left:240px"> Add Nation </button>

  <div class = "userWindow" id = "addNationWindow" style = "left:140px">
    <div id = "newNationForm">
      <p class = "windowTopfiller"></p>
      <p class = "windowLabel"> Name: </p> <input type = "text" name="name" id="name" class = "windowInput"> <br>
      <p class = "windowLabel"> Population: </p> <input type = "number" name="pop" id="pop" class = "windowInput"> <br>
      <p class = "windowLabel"> GDP: </p> <input type = "number" name="gdp" id="gdp" class = "windowInput"> <br>
      <p class = "windowLabel"> Social Stability: </p> <input type = "number" name="social" id="social" class = "windowInput"> <br>
      <p class = "windowLabel"> Standard of Living: </p> <input type = "number" name="living" id="living" class = "windowInput"> <br>

      <button id = "newNationSubmit" class = "windowSubmit"> Submit </button>
    </div>
  </div>

  <!-- New Event -->
  <button class = "userBtn closed" id="addEvent" style = "left: 630px"> Add Event </button>

  <div class = "userWindow" id = "addEventWindow" style = "left:530px">
    <div id = "newEventForm">
      <button id="typeDropBtn" class="dropBtn">Type:</button>

      <div class = "dropDown closed" id="typeDropDown">
        <p class = "typeRadio">Natural Disaster</p>
        <p class = "typeRadio">Election</p>
        <p class = "typeRadio">Economic Downturn</p>
        <p class = "typeRadio">Economic Boom</p>
      </div>

      <p id="typeLabel">-----------------</p>

      <p class = "windowLabel" style = "left: 50px">Year:</p>
      <input type = "number" name="year" id="submitYear" class = "windowInput" style = "right:45px">

      <button id = "nationDropBtn" class = "dropBtn">To: </button>

      <div class = "dropDown closed" id="nationDropDown">
        <!-- Nation labels are generated here in main.js-->
      </div>

      <p id="nationLabel">-----------------</p>

      <button id="newEventSubmit" class = "windowSubmit"> Submit </button>
    </div>
  </div>

  <!-- Stats -->
  <button class = "userBtn closed" id="stats" style = "left: 1020px"> Stats/Graphs </button>
  <div class = "userWindow" id = "statsWindow" style = "left:920px">
  </div>
</div>

<br>

</#assign>
<#include "main.ftl">