var epamcscockpit = angular.module("epamcscockpit", ["ngRoute"]);


epamcscockpit.config(["$routeProvider",function($routeProvider){
	$routeProvider
		.when("/",{
			 templateUrl: 'template/ticket_pool.html',
		     controller: 'TicketPoolCtrl'
		})
		.when("/advanced_search",{
			templateUrl: "template/advanced_search.html"
		})
		.when("/ticket/:ticketId",{
			templateUrl: "template/ticket_details.html",
		    controller: 'TicketCtrl'
		})
		.when("/ticket_create",{
			templateUrl: "template/ticket_create.html",
		    
		})
		.when("/order_create",{
			templateUrl: "template/order_create.html",
		    
		})
		.when("/customer_create",{
			templateUrl: "template/customer_create.html",
		    
		})
		.otherwise({
	        redirectTo: "/"
	    });
}]);

epamcscockpit.controller("TicketPoolCtrl", function($scope, $http) {
	$scope.ticketPool = [];
	$scope.ticketFilter ={
			
	}
	
	$http.get("data/ticketStore.json").
		success(function(data,status,headers,config){
			$scope.ticketPool=data
		}).
		error(function(){
			
		});
	
});

epamcscockpit.controller("TicketCtrl", function($scope, $http, $routeParams) {
	 $scope.ticketId = $routeParams.ticketId;
});