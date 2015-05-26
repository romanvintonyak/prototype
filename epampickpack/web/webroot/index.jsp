
<html  ng-app>
  <head>
      <!--<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.15/angular.min.js"></script> -->
      <script src="js/angular.js"></script> 
      <link rel="stylesheet" href="css/epampickpack.css" />
  </head>
  <body>
 
    
    
    <div >
       <div class="column-left">Column left</div>
	   <div class="column-center">
	      <label>Name:</label>
	      <input type="text" ng-model="yourName" placeholder="Enter a name here">
	      <hr>
	      <h1>Hello {{yourName}}!</h1>
	   </div>
	   
	   <div class="column-right">Column right</div>
	</div>
    
    
  </body>
</html>