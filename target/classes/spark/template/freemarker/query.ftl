<#assign content>

<div id="header">
  <canvas id="clock" width = "50px" height = "50px" class = ""> </canvas>
  <h1> ${header}:</h1> <h1 id="year"> X </h1> <h1 style="position:relative; font-size:36px; left:20px"> A.D. </h1>
</div>

<div id="events">
  <p class="mes black">Click the dirt pile above to start/stop time.</p>
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
      <p class = "windowLabel"> Name: </p> <input type = "text" name="name" id="name" class = "windowInput">
      <p class = "windowLabel"> Population: </p> <input type = "number" name="pop" id="pop" class = "windowInput" style="top:65px">
      <p class = "windowLabel"> GDP: </p> <input type = "number" name="gdp" id="gdp" class = "windowInput" style="top:110px">
      <p class = "windowLabel"> Social Stability: </p> <input type = "number" name="social" id="social" class = "windowInput" style="top:160px">
      <p class = "windowLabel"> Standard of Living: </p> <input type = "number" name="living" id="living" class = "windowInput" style="top:205px">

      <button id = "newNationSubmit" class = "windowSubmit"> Submit </button>
    </div>
  </div>

  <!-- New Event -->
  <button class = "userBtn closed" id="addEvent" style = "left: 630px"> Add Event </button>

  <div class = "userWindow" id = "addEventWindow" style = "left:530px; height: 220px; top: 180px;">
    <div id = "newEventForm">
      <p class = "windowLabel">Event: </p>

      <div class = "dropDown closed" id="typeDropDown">
        <p class = "typeRadio">Natural Disaster</p>
        <p class = "typeRadio">Economic Downturn</p>
        <p class = "typeRadio">Economic Boom</p>
      </div>

      <p id="typeLabel">____________________</p>

      <p class = "windowLabel" style = "left: 30px">Year:</p>
      <input type = "number" name="year" id="submitYear" class = "windowInput" style = "right:20px; top:65px; width:195px">

      <p class = "windowLabel" style = "left:45px">To: </p>

      <div class = "dropDown closed" id="nationDropDown">
        <!-- Nation labels are generated here in main.js-->
      </div>

      <p id="nationLabel">____________________</p>

      <button id="newEventSubmit" class = "windowSubmit"> Submit </button>
    </div>
  </div>

  <!-- Stats -->
  <button class = "userBtn closed" id="stats" style = "left: 1020px"> Help </button>
  <div class = "userWindow" id = "statsWindow" style = "left:920px; height:275px; top:125px">
    <div style = "width:350px; height:10px"></div> 
    <img src="images/singleFrames/FF6Person.png" class="helpImg" style="margin-left:25px; width:40px; height:50px"> <p class="helpText"> = Population</p>

    <img src="images/singleFrames/dollar.png" class="helpImg" style="width:75px; height:30px"> <p class="helpText"> = GDP (Millions)</p>

    <img src="images/singleFrames/Social1.png" class="helpImg" style="margin-left:13px; width:60px; height:50px"> <p class="helpText" style = "margin-right:50px"> = Social Stability (X/100)</p>

    <img src="images/singleFrames/LivingTree.png" class="helpImg" style="margin-left:17px;width:60px; height:60px"> <p class="helpText" style = "margin-right:30px"> = Standard of Living (X/100)</p>

    <p class = "helpText" style="display:block;text-align:center; margin: 10px auto;"> Questions? Email: kirisoul@gmail.com </p>
  </div>
</div>

<br>

</#assign>
<#include "main.ftl">