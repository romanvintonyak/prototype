var epamcscockpit = angular.module("epamcscockpit", ["ngRoute","epamCustomersResource","epamcscockpitFilters"]);

epamcscockpit.config(["$routeProvider","$httpProvider",function($routeProvider,$httpProvider){
	$routeProvider
		.when("/",{
			 templateUrl: 'template/ticket_pool.html',
		     controller: 'TicketPoolCtrl',
		})
		.when("/advanced_search",{
			templateUrl: "template/advanced_search.html",
		})
		.when("/ticket/:ticketId",{
			templateUrl: "template/ticket_details.html",
		    controller: 'TicketDetailsCtrl'
		})
		.when("/customer/:customerId",{
			templateUrl: "template/customer_details.html",
		    controller: 'CustomerDetailsCtrl'
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
	
	$http.get("data/ticketStore.json").
		success(function(data,status,headers,config){
			$scope.ticketPool=data
		}).
		error(function(){
			// Handle error here
		});
	
});

epamcscockpit.controller("TicketDetailsCtrl", function($scope, $http, $routeParams) {
	 $scope.ticketId = $routeParams.ticketId;
});

epamcscockpit.controller("CustomerDetailsCtrl", function(CustomersResource,$http, $scope, $routeParams) {
	$scope.customer = undefined;
	$scope.customerAddress = undefined;
	
	CustomersResource.get({
		 id: $routeParams.customerId,
	 }, function(data) { //successful response here
		 $scope.customer=data
		 console.log($scope.customer)
	 }, function(err) {
		 // Handle error here
	 });
});



