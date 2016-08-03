<!DOCTYPE html>
  <head>
    <meta charset="utf-8">
    <title>${title}</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="shortcut icon" href="images/icon.png"/>

    <link rel="stylesheet" href="stylesheets/normalize.css">
    <link rel="stylesheet" href="stylesheets/html5bp.css">
  </head>
  <body>
    <p id="title"> To the New World </p>

    <img src="images/flower.png" id="flower">
    <button id = "enterButton"> Enter </button>

    <p id="credits"> A Game by <a href="https://github.com/cdluo">Chris Luo </a> </p>
  </body>
</html>

<!-- CSS -->
<style>
  
@font-face {
  font-family:Jacques;
  src:url("stylesheets/Jacques.ttf");
}

#title{
  position: relative;
  display:block;
  margin: 0 auto;
  top:5px;
  font-family: Jacques;
  font-size:64px;
  text-align:center;
  text-shadow: 1px 1px 1px black;
}

#flower{
  position:relative;
  display:block;
  margin-top: 20px;
  margin-bottom: 35px;
  margin-left:auto;
  margin-right:auto;
}

#enterButton{
  position:relative;
  display:block;
  height:50px;
  width:175px;
  font-family:"Garamond";
  font-size: 20px;
  margin: 0 auto;
  border: 1px solid black;
  border-radius:10px;
  box-shadow: 2px 2px 2px black;
  background-color:#AF82B8;
  transition-duration: 0.4s;
}

#enterButton:hover{
  background-color:#79D6F2;
}

#credits{
  position:absolute;
  bottom:10px;
  right:10px;
  font-family:"Garamond";
  font-size:24px;
}

</style>

<!-- JS -->
<script src="javascripts/jquery-2.1.1.js"></script>
<script>

$("#enterButton").on('click', function(){
  window.location = "/home";
});

</script>